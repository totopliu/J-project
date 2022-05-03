package com.crm.platform.service.pub.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.param.TradeQuery;
import com.crm.platform.entity.pub.CurrencyPriceBO;
import com.crm.platform.entity.pub.GroupRebateVO;
import com.crm.platform.entity.pub.ManagerEntity;
import com.crm.platform.entity.pub.ManagerRebateBO;
import com.crm.platform.entity.pub.GoldSokectDTO;
import com.crm.platform.entity.pub.ManagerRebateConfigBO;
import com.crm.platform.entity.pub.TardeTotalVO;
import com.crm.platform.entity.pub.TradesToRebateEntity;
import com.crm.platform.enums.ManagerAutoRebateEnum;
import com.crm.platform.mapper.pub.GroupRebateMapper;
import com.crm.platform.mapper.pub.ManagerMapper;
import com.crm.platform.mapper.pub.RebateMapper;
import com.crm.platform.mapper.pub.TradesMapper;
import com.crm.platform.service.pub.TradesService;
import com.crm.util.CurrencyPointUtil;
import com.crm.util.MathUtil;
import com.crm.util.Mt4PropertiesUtil;
import com.crm.util.RebateUtil;
import com.crm.util.SessionUtil;
import com.crm.util.SocketUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
@Transactional
public class TradesServiceImpl implements TradesService {

    @Autowired
    TradesMapper tradesMapper;

    @Autowired
    ManagerMapper managerMapper;

    @Autowired
    RebateMapper rebateMapper;

    @Autowired
    GroupRebateMapper groupRebateMapper;

    @Override
    public PageInfo<JSONObject> listHoldTrades(DataGrid grid, TradeQuery param) {
        handleTradeQuery(param);
        if (StringUtils.isNotEmpty(grid.getSort())) {
            PageHelper.orderBy(grid.getSort() + "   " + grid.getOrder());
        }
        PageHelper.startPage(grid.getPageNum(), grid.getPageSize());
        return new PageInfo<JSONObject>(this.tradesMapper.queryHoldTradesList(param));
    }

    @Override
    public PageInfo<JSONObject> listFinishTrades(DataGrid grid, TradeQuery param) {
        handleTradeQuery(param);
        if (StringUtils.isNotEmpty(grid.getSort())) {
            PageHelper.orderBy(grid.getSort() + "   " + grid.getOrder());
        }
        PageHelper.startPage(grid.getPageNum(), grid.getPageSize());
        return new PageInfo<JSONObject>(this.tradesMapper.queryFinishTradesList(param));
    }

    @Override
    public AjaxJson countFinishProfitTotal(TradeQuery param) {
        AjaxJson json = new AjaxJson();
        handleTradeQuery(param);
        TardeTotalVO tardeTotalVO = tradesMapper.countFinishTotal(param);
        tardeTotalVO.setCommissionSum(
                MathUtil.roundBigDecimalSetHalfupToDouble(2, new BigDecimal(tardeTotalVO.getCommissionSum())));
        tardeTotalVO.setStorageSum(
                MathUtil.roundBigDecimalSetHalfupToDouble(2, new BigDecimal(tardeTotalVO.getStorageSum())));
        tardeTotalVO.setTradeProfitSum(
                MathUtil.roundBigDecimalSetHalfupToDouble(2, new BigDecimal(tardeTotalVO.getTradeProfitSum())));
        Double tradeAllProfitSum = MathUtil.add(
                MathUtil.add(tardeTotalVO.getCommissionSum(), tardeTotalVO.getStorageSum()),
                tardeTotalVO.getTradeProfitSum());
        tardeTotalVO
                .setTradeAllProfitSum(MathUtil.roundBigDecimalSetHalfupToDouble(2, new BigDecimal(tradeAllProfitSum)));
        Integer inCount = tradesMapper.getInCount(param);
        tardeTotalVO.setInCount(inCount);
        Double inGoldSum = tradesMapper.getInGoldSum(param);
        tardeTotalVO.setInGoldSum(MathUtil.roundBigDecimalSetHalfupToDouble(2, new BigDecimal(inGoldSum)));
        Double outGoldSum = tradesMapper.getOutGoldSum(param);
        tardeTotalVO.setOutGoldSum(MathUtil.roundBigDecimalSetHalfupToDouble(2, new BigDecimal(outGoldSum)));
        tardeTotalVO.setInOutGold(MathUtil.roundBigDecimalSetHalfupToDouble(2,
                new BigDecimal(MathUtil.add(tardeTotalVO.getInGoldSum(), tardeTotalVO.getOutGoldSum()))));
        Double balanceSum = tradesMapper.getBalanceSum(param);
        tardeTotalVO.setBalanceSum(MathUtil.roundBigDecimalSetHalfupToDouble(2, new BigDecimal(balanceSum)));
        Double equitySum = tradesMapper.getEquitySum(param);
        tardeTotalVO.setEquitySum(MathUtil.roundBigDecimalSetHalfupToDouble(2, new BigDecimal(equitySum)));
        json.setSuccess(true);
        json.setData(tardeTotalVO);
        return json;
    }

    @Override
    public void refreshTradesToRebate() {
        List<TradesToRebateEntity> tradesToRebateEntities = tradesMapper
                .findTradesByCloseTime(tradesMapper.findTempTime());
        if (tradesToRebateEntities != null && tradesToRebateEntities.size() > 0) {
            for (int i = 0; i < tradesToRebateEntities.size(); i++) {
                TradesToRebateEntity ttrEntity = tradesToRebateEntities.get(i);
                if (i == (tradesToRebateEntities.size() - 1)) {
                    tradesMapper.updTempTime(ttrEntity.getTime());
                }
                String symbol = ttrEntity.getSymbol(); // 订单货币
                CurrencyPriceBO currencyPriceBO = rebateMapper.getCurrencyPriceBOBySymbol(symbol);
                if (currencyPriceBO == null) {
                    continue;
                }
                ManagerRebateConfigBO managerRebateConfigBO = managerMapper
                        .getManagerRebateConfigBOByLogin(ttrEntity.getLogin());
                if (managerRebateConfigBO == null) {
                    continue;
                }
                int ticket = ttrEntity.getDeal(); // 订单号
                String groupName = managerRebateConfigBO.getGroupName();
                Integer currencyId = currencyPriceBO.getCurrency_id();
                Integer belongId = managerRebateConfigBO.getBelongid();
                Double rebateFixed = 0d;
                Double rebatePoint = 0d;
                BigDecimal volumeBigDecimal = MathUtil.translateToBigDecimal(ttrEntity.getVolume()); // 交易手数
                GroupRebateVO groupRebateVO = groupRebateMapper.getGroupRebateByAttr(groupName, currencyId,
                        managerRebateConfigBO.getLevel());
                if (groupRebateVO != null) {
                    rebateFixed = groupRebateVO.getFixed();
                    rebatePoint = groupRebateVO.getPoint();
                    ManagerRebateBO selfManagerRebateBO = new ManagerRebateBO();
                    selfManagerRebateBO.setManagerid(managerRebateConfigBO.getManagerid());
                    selfManagerRebateBO.setTicket(ticket);
                    selfManagerRebateBO.setOver(0);

                    double rebate = 0;
                    if (rebateFixed != null) {
                        selfManagerRebateBO.setFixed(rebateFixed);
                        rebate = RebateUtil.getFixedRebate(volumeBigDecimal, rebateFixed);
                        selfManagerRebateBO.setRebate(rebate);
                    } else if (rebatePoint != null) {
                        selfManagerRebateBO.setPoint(rebatePoint);
                        double currencypoint = CurrencyPointUtil.mathCurrencyPoint(symbol, currencyPriceBO.getDigits(),
                                currencyPriceBO.getBid(), currencyPriceBO.getFixed(), currencyPriceBO.getRelbid());
                        rebate = RebateUtil.getPointRebate(currencypoint, volumeBigDecimal, rebatePoint);
                        selfManagerRebateBO.setRebate(rebate);
                    }
                    if (rebate > 0) {
                        if (managerRebateConfigBO.getAutoRebate() == ManagerAutoRebateEnum.AUTO.getValue()) {
                            if (managerRebateConfigBO.getRebateLogin() != null) {
                                if (intoGoldBySocket(managerRebateConfigBO.getRebateLogin(), rebate, ticket)) {
                                    selfManagerRebateBO.setOver(1);
                                    selfManagerRebateBO.setRebateTime(new Date());
                                }
                            }
                        }
                        rebateMapper.insertManagerRebate(selfManagerRebateBO);
                    }
                }

                int count = 100;
                while (count > 0) {
                    count--;
                    ManagerRebateConfigBO parentManagerRebateConfigBO = managerMapper
                            .getManagerRebateConfigBOByManagerId(belongId);
                    if (parentManagerRebateConfigBO == null) {
                        break;
                    }
                    GroupRebateVO parentGroupRebateVO = groupRebateMapper.getGroupRebateByAttr(groupName, currencyId,
                            parentManagerRebateConfigBO.getLevel());
                    if (parentGroupRebateVO != null) {
                        ManagerRebateBO managerRebateBO = new ManagerRebateBO();
                        managerRebateBO.setManagerid(parentManagerRebateConfigBO.getManagerid());
                        managerRebateBO.setTicket(ticket);
                        managerRebateBO.setOver(0);

                        double parentRebate = 0;
                        if (parentGroupRebateVO.getFixed() != null) {
                            managerRebateBO.setFixed(parentGroupRebateVO.getFixed());
                            double parentRebateFixed = MathUtil.subtract(parentGroupRebateVO.getFixed(), rebateFixed);
                            if (parentRebateFixed > 0) {
                                rebateFixed = parentGroupRebateVO.getFixed();
                            }
                            parentRebate = RebateUtil.getFixedRebate(volumeBigDecimal, parentRebateFixed);
                        } else if (parentGroupRebateVO.getPoint() != null) {
                            managerRebateBO.setPoint(parentGroupRebateVO.getPoint());
                            double parentRebatePoint = MathUtil.subtract(parentGroupRebateVO.getPoint(), rebatePoint);
                            if (parentRebatePoint > 0) {
                                rebatePoint = parentGroupRebateVO.getPoint();
                            }
                            double currencypoint = CurrencyPointUtil.mathCurrencyPoint(symbol,
                                    currencyPriceBO.getDigits(), currencyPriceBO.getBid(), currencyPriceBO.getFixed(),
                                    currencyPriceBO.getRelbid());
                            parentRebate = RebateUtil.getPointRebate(currencypoint, volumeBigDecimal,
                                    parentRebatePoint);
                        }
                        managerRebateBO.setRebate(parentRebate);
                        if (parentRebate > 0) {
                            if (parentManagerRebateConfigBO.getAutoRebate() == ManagerAutoRebateEnum.AUTO.getValue()) {
                                if (parentManagerRebateConfigBO.getRebateLogin() != null) {
                                    if (intoGoldBySocket(parentManagerRebateConfigBO.getRebateLogin(), parentRebate,
                                            ticket)) {
                                        managerRebateBO.setOver(1);
                                        managerRebateBO.setRebateTime(new Date());
                                    }
                                }
                            }
                            rebateMapper.insertManagerRebate(managerRebateBO);
                        }
                    }
                    belongId = parentManagerRebateConfigBO.getBelongid();
                }
            }
        }
    }

    @Override
    public PageInfo<JSONObject> queryStatistics(DataGrid grid, Integer login, String group, String startTime,
            String endTime) {
        if (StringUtils.isNotEmpty(grid.getSort())) {
            PageHelper.orderBy(grid.getSort() + "   " + grid.getOrder());
        }
        PageHelper.startPage(grid.getPageNum(), grid.getPageSize());
        return new PageInfo<JSONObject>(tradesMapper.queryStatistics(login, group, startTime, endTime));
    }

    @Override
    public AjaxJson summary(Integer login, String group, String startTime, String endTime) {
        AjaxJson json = new AjaxJson();
        JSONObject vo = tradesMapper.summary(login, group, startTime, endTime);
        json.setData(vo);
        json.setSuccess(true);
        return json;
    }

    /**************************
     * 调用MT4接口入金
     * 
     * @param login
     * @param rebate
     * @param ticket
     * @return
     **************************/
    private boolean intoGoldBySocket(int login, double rebate, int ticket) {
        GoldSokectDTO goldSokectDTO = Mt4PropertiesUtil.getConfigGoldSoketDTO();
        goldSokectDTO.setLogin(login);
        goldSokectDTO.setMoney(MathUtil.roundBigDecimalSetHalfupToInt(0,
                MathUtil.translateToBigDecimal(rebate).multiply(new BigDecimal(100))));
        goldSokectDTO.setComment(String.valueOf(ticket));
        String response = SocketUtil.connectMt4(JSONObject.toJSONString(goldSokectDTO));
        if ("0".equals(response)) {
            return true;
        } else {
            return false;
        }
    }

    /**************************
     * 递归获取所有下级ID
     * 
     * @param login
     * @return
     **************************/
    private StringBuffer getSonLogins(String login) {
        StringBuffer logins = new StringBuffer();
        List<Integer> sonLogins = managerMapper.getSonLoginsByLogin(login);
        if (sonLogins != null && sonLogins.size() > 0) {
            for (int i = 0; i < sonLogins.size(); i++) {
                logins.append(",").append(sonLogins.get(i));
                logins.append(getSonLogins(String.valueOf(sonLogins.get(i))));
            }
        }
        return logins;
    }

    /************************
     * 处理订单参数
     * 
     * @param query
     ************************/
    private void handleTradeQuery(TradeQuery query) {
        StringBuffer logins = new StringBuffer();
        Integer getLimit = query.getGetLimit();
        ManagerEntity manager = SessionUtil.getSession();
        Integer sessionLogin = manager.getLogin();
        Integer sessionId = manager.getManagerid();
        if (Objects.equals(0, getLimit)) {
            // 本人
            if (query.getLogin() != null) {
                logins.append(query.getLogin());
            } else if (sessionLogin != null) {
                logins.append(sessionLogin);
            } else {
                logins.append(0);
            }
        } else if (Objects.equals(1, getLimit)) {
            // 直接下级
            List<Integer> sonLogins = null;
            if (query.getTreeManagerId() != null) {
                sonLogins = managerMapper.listSonLoginsById(query.getTreeManagerId());
            } else {
                sonLogins = managerMapper.listSonLoginsById(sessionId);
            }
            if (sonLogins != null && sonLogins.size() > 0) {
                for (int i = 0; i < sonLogins.size(); i++) {
                    if (i == 0) {
                        logins.append(sonLogins.get(i));
                    } else {
                        logins.append(",").append(sonLogins.get(i));
                    }
                }
            } else {
                logins.append(0);
            }
        } else if (Objects.equals(2, getLimit)) {
            // 所有下级
            List<Integer> sonLogins = null;
            if (query.getTreeManagerId() != null) {
                sonLogins = managerMapper.listSonLoginsById(query.getTreeManagerId());
            } else {
                sonLogins = managerMapper.listSonLoginsById(sessionId);
            }
            if (sonLogins != null && sonLogins.size() > 0) {
                for (int i = 0; i < sonLogins.size(); i++) {
                    if (i == 0) {
                        logins.append(sonLogins.get(i));
                    } else {
                        logins.append(",").append(sonLogins.get(i));
                    }
                    logins.append(getSonLogins(String.valueOf(sonLogins.get(i))));
                }
            } else {
                logins.append(0);
            }
        } else if (Objects.equals(3, getLimit)) {
            // 本人+下级
            List<Integer> sonLogins = null;
            if (query.getTreeManagerId() != null) {
                sonLogins = managerMapper.listSonLoginsById(query.getTreeManagerId());
            } else {
                sonLogins = managerMapper.listSonLoginsById(sessionId);
            }
            if (sonLogins != null && sonLogins.size() > 0) {
                for (int i = 0; i < sonLogins.size(); i++) {
                    if (i == 0) {
                        logins.append(sonLogins.get(i));
                    } else {
                        logins.append(",").append(sonLogins.get(i));
                    }
                    logins.append(getSonLogins(String.valueOf(sonLogins.get(i))));
                }
                if (query.getLogin() != null) {
                    logins.append(",").append(query.getLogin());
                } else if (sessionLogin != null) {
                    logins.append(",").append(sessionLogin);
                }
            } else {
                if (query.getLogin() != null) {
                    logins.append(query.getLogin());
                } else if (sessionLogin != null) {
                    logins.append(sessionLogin);
                } else {
                    logins.append(0);
                }
            }
        }
        query.setLogins(logins.toString());
    }

}
