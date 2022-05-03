package com.crm.platform.controller.pub;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.crm.annotation.SystemLog;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.pub.DinpayDTO;
import com.crm.platform.entity.pub.InGoldBO;
import com.crm.platform.entity.pub.ManagerEntity;
import com.crm.platform.entity.pub.PayCommonDTO;
import com.crm.platform.entity.pub.PayGozDTO;
import com.crm.platform.entity.pub.PayHxt;
import com.crm.platform.entity.pub.PayResult;
import com.crm.platform.entity.pub.PayShenfuDTO;
import com.crm.platform.entity.pub.PayYsDTO;
import com.crm.platform.service.pub.InGoldService;
import com.crm.util.DateFormatUtil;
import com.crm.util.MD5Utils;
import com.crm.util.MathUtil;
import com.crm.util.PayUtilDinpay;
import com.crm.util.PayUtilGaozhi;
import com.crm.util.PayUtilShenfu;
import com.crm.util.PayUtilYs;
import com.crm.util.SessionUtil;
import com.github.pagehelper.PageInfo;
import com.itrus.util.sign.RSAWithSoftware;

/**
 * 入金控制器
 * 
 * 
 */
@Controller
@RequestMapping("/pub/inGold")
public class InGoldController {

    private final static Logger LOG = LoggerFactory.getLogger(InGoldController.class);

    @Autowired
    private InGoldService inGoldService;

    /**
     * 入金页
     * 
     * @return
     */
    @RequestMapping(value = "list")
    public String list() {
        return "pub/in_gold/list";
    }

    /**
     * 入金分页列表
     * 
     * @param grid
     * @param login
     * @param ticket
     * @param inDate
     * @return
     */
    @RequestMapping(value = "query")
    @ResponseBody
    public PageInfo<JSONObject> query(DataGrid grid, Integer login, Integer ticket, String inDate) {
        try {
            if (login == null) {
                ManagerEntity user = SessionUtil.getSession();
                login = user.getLogin();
            }
            return inGoldService.listInGold(grid, login, ticket, inDate);
        } catch (Exception e) {
            LOG.error("", e);
        }
        return null;
    }

    /**
     * 查询入金合计
     * 
     * @param login
     * @param ticket
     * @param inDate
     * @return
     */
    @RequestMapping(value = "findInGoldTotal")
    @ResponseBody
    public AjaxJson findInGoldTotal(Integer login, Integer ticket, String inDate) {
        if (login == null) {
            ManagerEntity user = SessionUtil.getSession();
            login = user.getLogin();
        }
        return inGoldService.countInGoldTotal(login, ticket, inDate);
    }

    /**
     * 查看入金详情
     * 
     * @param id
     * @return
     */
    @RequestMapping("getInGoldInfo")
    public String getInGoldInfo(Model model, Integer id) {
        model.addAttribute("dto", inGoldService.getInGoldById(id));
        return "pub/in_gold/info";
    }

    /**
     * 打开申请入金页
     * 
     * @return
     */
    @RequestMapping(value = "inPage")
    public String inPage(Model model) {
        // 查询入金汇率
        model.addAttribute("rate", inGoldService.getRate().getData());
        return "pub/in_gold/in";
    }

    /**
     * 提交入金申请
     * 
     * @param bo
     * @return
     */
    @RequestMapping(value = "save")
    @ResponseBody
    @SystemLog(description = "提交入金申请", module = "入金模块", methods = "save")
    public AjaxJson save(InGoldBO bo) {
        AjaxJson json = new AjaxJson();
        try {
            ManagerEntity user = SessionUtil.getSession();
            bo.setManagerid(user.getManagerid());
            json = inGoldService.save(bo);
            return json;
        } catch (Exception e) {
            LOG.error("保存入金异常", e);
        }
        return json;
    }
    
    @RequestMapping("pay_{id}_{umid}")
    public String pay(Model model, @PathVariable Integer id, @PathVariable Integer umid) {
        try {
            InGoldBO inGoldEntity = inGoldService.getInGoldById(id);
            model.addAttribute("umid", umid);
            model.addAttribute("orderid", inGoldEntity.getOrderNumber());
            model.addAttribute("money", MathUtil.roundDoubleSetHalfUpToStr(0, inGoldEntity.getMoney() * 100));
            model.addAttribute("otime", DateFormatUtil.getNowTimeStamp());
            String sign = MD5Utils
                    .md5(umid + MathUtil.roundDoubleSetHalfUpToStr(0, inGoldEntity.getMoney() * 100) + "mtf")
                    .toUpperCase();
            model.addAttribute("sign", sign);
        } catch (Exception e) {
            LOG.error("跳转支付页面异常", e);
        }
        return "pub/in_gold/pay";
    }
    
    @RequestMapping("payNotify")
    @ResponseBody
    public String payNotify(HttpServletRequest request) {
        showParams(request);
        try {
            String umid = request.getParameter("umid");
            String money = request.getParameter("money");
            String key = "mtf";
            String sign = request.getParameter("sign");
            String orderid = request.getParameter("orderid");
            String mySign = MD5Utils.md5(umid + money + key).toUpperCase();
            if (Objects.equals(sign, mySign)) {
                inGoldService.payNotify(orderid);
                return "ok";
            } else {
                return "fail";
            }
        } catch (Exception e) {
            LOG.error("支付异步通知异常", e);
            return "fail";
        }
    }

    /**
     * 打开高志跳转页
     * 
     * @return
     */
    @RequestMapping(value = "submit_{id}")
    public String submit(Model model, @PathVariable Integer id) {
        try {
            InGoldBO inGoldEntity = inGoldService.getInGoldById(id);
            String orderdate = DateFormatUtil.format(inGoldEntity.getCreateTime(), DateFormatUtil.SCHEME_NO_SYMBOL_DATE);
            // 配置支付参数
            PayGozDTO payGozDTO = PayUtilGaozhi.createPayGozDTO(inGoldEntity);
            model.addAttribute("dto", inGoldEntity);
            model.addAttribute("date", orderdate);
            model.addAttribute("sign", payGozDTO.toParam());
            model.addAttribute("payParam", payGozDTO);
        } catch (Exception e) {
            LOG.error("", e);
        }
        return "pub/in_gold/submit";
    }

    /**
     * 高志支付接口回调页
     */
    @RequestMapping(value = "result")
    public String result(PayResult payResult) {
        try {
            inGoldService.updateInGoldByResult(payResult);
        } catch (Exception e) {
            LOG.error("", e);
        }
        return "redirect:/index";
    }

    /**
     * 打开申付跳转页
     * 
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("submitShenfu_{id}")
    public String submitShenfu(Model model, @PathVariable Integer id) {
        try {
            InGoldBO inGoldEntity = inGoldService.getInGoldById(id);
            PayShenfuDTO payShenfuDTO = PayUtilShenfu.createPayShenDTO();
            payShenfuDTO.setOrderNo(String.valueOf(id));
            payShenfuDTO.setTxnAmt(MathUtil.roundDoubleSetHalfUpToStr(2, inGoldEntity.getMoney()));
            model.addAttribute("dto", payShenfuDTO);
        } catch (Exception e) {
            LOG.error("", e);
        }
        return "pub/in_gold/submitShenfu";
    }

    /**
     * 申付支付接口回调页
     * 
     * @param request
     * @return
     */
    @RequestMapping("resultShenfu")
    public void resultShenfu(HttpServletRequest request) {
        try {
            LOG.info("payresult come in");
            Map<String, String> map = new HashMap<String, String>();
            Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = (String) paramNames.nextElement();
                String[] paramValues = request.getParameterValues(paramName);
                if (paramValues.length == 1) {
                    String paramValue = paramValues[0];
                    if (paramValue.length() != 0) {
                        map.put(paramName, paramValue);
                    }
                }
            }
            String str = PayUtilShenfu.coverMap2String(map);
            LOG.info("payresult  ========== " + str);
            String publickey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCGIrcOD7ZqmkL+Ew1apwKWlwD1mLf0Yw/GgvHZh3rAFkzU5+9PFHsvJ0any15NrqNbx9nY3mgzab8tbg6ui+YRRYD9qyL1FxAohuCXVldDMr1mg11rK2ZiqtIeJbmiyiW2QgBuZVId6lqnD+bz6uiMrDhD1MEWERsaGP7Qz9zIBwIDAQAB";
            String signature = request.getParameter("signature");
            if (PayUtilShenfu.verify(str.getBytes("UTF-8"), publickey, signature)) {
                LOG.info("payresult  ========== check ok");
                inGoldService.checkResultDinpay(request.getParameter("orderNo"), "");
            }
            // inGoldService.checkResultShenfu(result);
        } catch (Exception e) {
            LOG.error("", e);
        }
    }
    
    @RequestMapping("resultAfsl")
    @ResponseBody
    public String resultAfsl(HttpServletRequest request) {
        synchronized(this) {
            try {
                LOG.info("Afsl支付回调被调用");
                showParams(request);
                String v_oid = request.getParameter("v_oid");
                String v_pmode = request.getParameter("v_pmode");
                String v_pstatus = request.getParameter("v_pstatus");
                String v_url = request.getParameter("v_url");
                String v_amount = request.getParameter("v_amount");
                String v_moneytype = request.getParameter("v_moneytype");
                String v_mac = request.getParameter("v_mac");
                Map<String, String> map = new HashMap<String, String>();
                map.put("v_oid", v_oid);
                map.put("v_pmode", v_pmode);
                map.put("v_pstatus", v_pstatus);
                map.put("v_url", v_url);
                map.put("v_amount", v_amount);
                map.put("v_moneytype", v_moneytype);
                map.put("appid", "180112053217556012");
                map.put("appsecret", "RKW19qVWclbFIJfGq0iVXdLco91XNpFT");
                
                map = sortMapByKey(map);
                String sign = MD5Utils.md5(org.kopitubruk.util.json.JSONUtil.toJSON(map));
                if (sign.equals(v_mac)) {
                    LOG.info("Afsl回调验证通过");
                    inGoldService.checkResultDinpay(v_oid, "");
                    return "ok";
                } else {
                    return "false";
                }
            } catch (Exception e) {
                LOG.error("Afsl支付回调异常", e);
                return "false";
            }
        }
    }

    /**
     * 打开银盛跳转页
     * 
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("submitYs_{id}")
    public String submitYs(Model model, @PathVariable Integer id, String bank_type, String support_card_type) {
        try {
            InGoldBO inGoldEntity = inGoldService.getInGoldById(id);
            PayYsDTO dto = PayUtilYs.createPayYsDTO();
            dto.setTotal_amount(MathUtil.roundDoubleSetHalfUpToStr(2, inGoldEntity.getMoney()));
            dto.setOut_trade_no(dto.getOut_trade_no() + "R" + id);
            dto.setBank_type(bank_type);
            dto.setSupport_card_type(support_card_type);
            model.addAttribute("dto", dto);
        } catch (Exception e) {
            LOG.error("", e);
        }
        return "pub/in_gold/submitYspay";
    }

    /**
     * 跳转智付页
     * 
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("payDinpay_{id}")
    public String payDinpay(Model model, @PathVariable Integer id) {
        try {
            InGoldBO inGoldEntity = inGoldService.getInGoldById(id);
            DinpayDTO dto = PayUtilDinpay.createDinpayDTO();
            dto.setOrder_no(String.valueOf(id));
            dto.setOrder_amount(inGoldEntity.getMoney());
            model.addAttribute("dto", dto);
        } catch (Exception e) {
            LOG.error("", e);
        }
        return "pub/in_gold/submitDinpay";
    }

    /**
     * 跳转AFSL支付
     * 
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("payAfsl_{id}")
    public String payAfsl(Model model, @PathVariable Integer id) {
        try {
            InGoldBO inGoldEntity = inGoldService.getInGoldById(id);
            Map<String, String> map = new HashMap<String, String>();
            map.put("appid", "180112053217556012");
            map.put("appsecret", "RKW19qVWclbFIJfGq0iVXdLco91XNpFT");
            
            map.put("v_oid", String.valueOf(id));
            map.put("v_amount", MathUtil.roundDoubleSetHalfUpToStr(2, inGoldEntity.getMoney()));
            map.put("v_ymd", DateFormatUtil.format(new Date(), DateFormatUtil.SCHEME_NO_SYMBOL_DATE));
            map.put("v_url", "http://mt5.mmigasia.com/crmf");
            map.put("v_notifyurl", "http://mt5.mmigasia.com/crmf/pub/inGold/resultAfsl");

            map = sortMapByKey(map);
            String sign = MD5Utils.md5(org.kopitubruk.util.json.JSONUtil.toJSON(map));
            map.put("sign", sign);
            model.addAttribute("dto", map);
        } catch (Exception e) {
            LOG.error("跳转afsl支付异常", e);
        }
        return "pub/in_gold/submitAfsl";
    }
    
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }
    
    
    
    /**
     * 智付支付成功回调接口
     * 
     * @param request
     * @return
     */
    @RequestMapping("resultDinpay")
    @ResponseBody
    public String resultDinpay(HttpServletRequest request) {
        try {
            LOG.info("第三方回调通知接口");
            request.setCharacterEncoding("UTF-8");
            String interface_version = (String) request.getParameter("interface_version");
            String merchant_code = (String) request.getParameter("merchant_code");
            String notify_type = (String) request.getParameter("notify_type");
            String notify_id = (String) request.getParameter("notify_id");
            String sign_type = (String) request.getParameter("sign_type");
            String dinpaySign = (String) request.getParameter("sign");
            String order_no = (String) request.getParameter("order_no");
            String order_time = (String) request.getParameter("order_time");
            String order_amount = (String) request.getParameter("order_amount");
            String extra_return_param = (String) request.getParameter("extra_return_param");
            String trade_no = (String) request.getParameter("trade_no");
            String trade_time = (String) request.getParameter("trade_time");
            String trade_status = (String) request.getParameter("trade_status");
            String bank_seq_no = (String) request.getParameter("bank_seq_no");

            StringBuilder signStr = new StringBuilder();
            if (null != bank_seq_no && !bank_seq_no.equals("")) {
                signStr.append("bank_seq_no=").append(bank_seq_no).append("&");
            }
            if (null != extra_return_param && !extra_return_param.equals("")) {
                signStr.append("extra_return_param=").append(extra_return_param).append("&");
            }
            signStr.append("interface_version=").append(interface_version).append("&");
            signStr.append("merchant_code=").append(merchant_code).append("&");
            signStr.append("notify_id=").append(notify_id).append("&");
            signStr.append("notify_type=").append(notify_type).append("&");
            signStr.append("order_amount=").append(order_amount).append("&");
            signStr.append("order_no=").append(order_no).append("&");
            signStr.append("order_time=").append(order_time).append("&");
            signStr.append("trade_no=").append(trade_no).append("&");
            signStr.append("trade_status=").append(trade_status).append("&");
            signStr.append("trade_time=").append(trade_time);

            String signInfo = signStr.toString();

            boolean result = false;

            if ("RSA-S".equals(sign_type)) {
                // sign_type = "RSA-S"
                String dinpay_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCDTUGtcjHrz0mGC8Kfbdu2c8YGSokdbRmw1u84j0lnkQevN5Ebn7+vESL2VGWkGN7guj/+JVvwBopxm7nUY9sXi+EAMptekhRC6WQeiydcp1k/Vblpgx0NfaNRG6D9oQIgEFEw2Gw4SmvKw1pLd9PW6UoeCr/UMKL4Qc56Yq4edwIDAQAB";
                result = RSAWithSoftware.validateSignByPublicKey(signInfo, dinpay_public_key, dinpaySign);
            }
            LOG.info("订单号" + trade_status + "状态" + trade_status + "验证" + result + "金额" + order_amount);
            if ("SUCCESS".equals(trade_status) && result) {
                inGoldService.checkResultDinpay(order_no, "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "SUCCESS";
    }

    /**
     * 支付回调
     * 
     * @param request
     * @return
     */
    @RequestMapping("payResult")
    @ResponseBody
    public String payResult(HttpServletRequest request) {
        try {
            LOG.info("第三方回调通知接口");
            String amount = request.getParameter("amount");
            String merchant_ref = request.getParameter("merchant_ref");
            String successcode = request.getParameter("successcode");
            String token = request.getParameter("token");

            LOG.info("订单号" + merchant_ref + "状态" + successcode + "验证" + token + "金额" + amount);
            String sign = MD5Utils.md5(amount + merchant_ref + successcode + "9103c8c82514f39d8360c7430c4ee557");
            if (sign.equals(token) && "0".equals(successcode)) {
                LOG.info("回调验证成功");
                inGoldService.checkResultDinpay(merchant_ref, "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "SUCCESS";
    }

    /**
     * 
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("payCommon_{id}")
    public String payCommon(Model model, @PathVariable Integer id) {
        try {
            InGoldBO inGoldEntity = inGoldService.getInGoldById(id);
            PayCommonDTO dto = new PayCommonDTO();
            dto.setMerchant_ref(String.valueOf(id));
            dto.setAmount(MathUtil.roundDoubleSetHalfUpToStr(2, inGoldEntity.getMoney()));
            model.addAttribute("dto", dto);
        } catch (Exception e) {
            LOG.error("", e);
        }
        return "pub/in_gold/submitCommon";
    }

    @RequestMapping("payJuhe_{id}")
    public String payJuhe(Model model, @PathVariable Integer id) {
        try {
            InGoldBO inGoldEntity = inGoldService.getInGoldById(id);
            Map<String, String> dto = new HashMap<String, String>();
            dto.put("Mer_code", "10109101");
            dto.put("Mer_key", "afHU8xPXpRsP5gnSjD14LHbTXsXsWPpp3E3R9C4BeLkZtK4bDDz56jFteNXq06azBcwQCvs7jPzZ7b9nNVFt0KIuYkNMFnl7KLyryDysuSvHJGLcnJ35RHYRtbS276RW");
            dto.put("Billno", String.valueOf(id));
            DecimalFormat currentNumberFormat = new DecimalFormat("#0.00");
            dto.put("Amount", currentNumberFormat.format(inGoldEntity.getMoney()));
            dto.put("OrderDate", new SimpleDateFormat("yyyyMMdd").format(new Date()));
            dto.put("Currency", "RMB");
            dto.put("GatewayType", "01");
            dto.put("Language", "GB");
            dto.put("ReturnUrl", "http://mt5.mmigasia.com");
            dto.put("GoodsInfo", "");
            dto.put("OrderEncodeType", "2");
            dto.put("RetEncodeType", "12");
            dto.put("Rettype", "1");
            dto.put("ServerUrl", "http://mt5.mmigasia.com/crmf/pub/inGold/resultJuhe");
            cryptix.jce.provider.MD5 b = new cryptix.jce.provider.MD5();
            String SignMD5 = b.toMD5(dto.get("Billno") + dto.get("Amount") + dto.get("OrderDate") + dto.get("Currency") + dto.get("Mer_key")).toLowerCase();
            dto.put("SignMD5", SignMD5);
            model.addAttribute("dto", dto);
        } catch (Exception e) {
            LOG.error("", e);
        }
        return "pub/in_gold/juhe";
    }

    /**
     * 移动端入金
     * 
     * @return
     */
    @RequestMapping("mobileDepositPage")
    public String mobileDepositPage(Model model) {
        model.addAttribute("rate", inGoldService.getRate().getData());
        return "mobile/deposit";
    }

    /**
     * 移动端入金
     * 
     * @return
     */
    @RequestMapping("mobileDeposit")
    @ResponseBody
    public AjaxJson mobileDeposit(InGoldBO bo) {
        AjaxJson json = new AjaxJson();
        try {
            boolean tag = new BigDecimal(bo.getMoney()).compareTo(BigDecimal.ZERO) > 0;
            if (!tag) {
                json.setSuccess(false);
                return json;
            }
            ManagerEntity user = SessionUtil.getSession();
            bo.setManagerid(user.getManagerid());
            bo.setPayType(3);
            json = inGoldService.save(bo);
            json.setData(bo);
            return json;
        } catch (Exception e) {
            LOG.error("", e);
        }
        return json;
    }

    @RequestMapping("payHxt_{id}")
    public String payHxt(Model model, @PathVariable Integer id) {
        try {
            InGoldBO inGoldEntity = inGoldService.getInGoldById(id);
            PayHxt dto = new PayHxt();
            dto.setMerOrderId(String.valueOf(id));
            dto.setBankCode(inGoldEntity.getChannelid());
            dto.setOrderAmt(MathUtil.roundDoubleSetHalfUpToStr(0, inGoldEntity.getMoney() * 100));
            dto.depositSign();
            model.addAttribute("dto", dto);
        } catch (Exception e) {
            LOG.error("", e);
        }
        return "mobile/submit";
    }

    @RequestMapping("resultHxt")
    @ResponseBody
    public String resultHxt(HttpServletRequest request) {
        try {
            LOG.info("慧鑫通第三方回调通知接口");
            showParams(request);
            request.setCharacterEncoding("UTF-8");
            String merId = (String) request.getParameter("merId");
            String merOrderId = (String) request.getParameter("merOrderId");
            String orderDate = (String) request.getParameter("orderDate");
            String tranAmt = (String) request.getParameter("tranAmt");
            String resCdoe = (String) request.getParameter("resCdoe");
            String resMsg = (String) request.getParameter("resMsg");
            String sign = (String) request.getParameter("sign");
            if ("0000".equals(resCdoe)) {
                String plaintext = new StringBuffer("merId=").append(merId).append("&merOrderId=").append(merOrderId).append("&orderDate=").append(orderDate).append("&resCdoe=")
                        .append(resCdoe).append("&resMsg=").append(resMsg).append("&tranAmt=").append(tranAmt).append("&key=").append("ee2940c027433244").toString();
                if (MD5Utils.md5(plaintext).equals(sign)) {
                    LOG.info("慧鑫通第三方回调验证成功->" + merOrderId);
                    inGoldService.checkResultDinpay(merOrderId, "");
                }
            }
        } catch (Exception e) {
            LOG.error("", e.getMessage());
        }
        return "success";
    }

    @RequestMapping("resultJuhe")
    @ResponseBody
    public String resultJuhe(HttpServletRequest request) {
        showParams(request);
        String billno = request.getParameter("MerOrderNo");
        String currency_type = request.getParameter("Currency");
        String amount = request.getParameter("Amount");
        String mydate = request.getParameter("OrderDate");
        String succ = request.getParameter("Succ");
        String msg = request.getParameter("Msg");
        String attach = request.getParameter("GoodsInfo");
        String ipsbillno = request.getParameter("SysOrderNo");
        String retEncodeType = request.getParameter("RetencodeType");
        String signature = request.getParameter("Signature");
        String content = billno + amount + mydate + succ + ipsbillno + currency_type; // 明文：订单编号+订单金额+订单日期+成功标志+IPS订单编号+币种
        boolean verify = false;

        // 验证方式：12-md5
        if (retEncodeType.equals("12")) {
            // 商户后台下载的商户证书
            String md5Key = "afHU8xPXpRsP5gnSjD14LHbTXsXsWPpp3E3R9C4BeLkZtK4bDDz56jFteNXq06azBcwQCvs7jPzZ7b9nNVFt0KIuYkNMFnl7KLyryDysuSvHJGLcnJ35RHYRtbS276RW";
            cryptix.jce.provider.MD5 b = new cryptix.jce.provider.MD5();
            String SignMD5 = b.toMD5(content + md5Key).toLowerCase();
            if (SignMD5.equals(signature)) {
                verify = true;
            }
        }
        if (verify) {
            if (succ != null) {
                if (succ.equalsIgnoreCase("Y")) {
                    LOG.info("聚合支付回调验证成功订单号-->{}", billno);
                    inGoldService.checkResultDinpay(billno, ipsbillno);
                    return "success";
                }
            }
        }
        return "";
    }
    
    @RequestMapping("payYsb_{id}")
    public String payYsb(Model model, @PathVariable Integer id) {
        try {
            InGoldBO inGoldEntity = inGoldService.getInGoldById(id);
            Map<String, String> dto = new HashMap<String, String>();
            dto.put("version", "3.0.0");
            dto.put("merchantId", "2120180205160933001");
            dto.put("merchantUrl", "http://mt5.mmigasia.com/crmf/pub/inGold/resultYsb");
            dto.put("responseMode", "2");
            dto.put("orderId", String.valueOf(id));
            dto.put("currencyType", "CNY");
            dto.put("amount", new BigDecimal(inGoldEntity.getMoney()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            dto.put("assuredPay", "");
            dto.put("time", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            dto.put("remark", "deposit");
            dto.put("bankCode", "");
            dto.put("commodity", "");
            dto.put("merchantKey", "mm999888");
            StringBuffer plain = new StringBuffer();
            plain.append("merchantId=").append(dto.get("merchantId")).append("&").append("merchantUrl=")
                    .append(dto.get("merchantUrl")).append("&").append("responseMode=").append(dto.get("responseMode"))
                    .append("&").append("orderId=").append(dto.get("orderId")).append("&").append("currencyType=")
                    .append(dto.get("currencyType")).append("&").append("amount=").append(dto.get("amount"))
                    .append("&assuredPay=").append(dto.get("assuredPay")).append("&time=").append(dto.get("time"))
                    .append("&remark=").append(dto.get("remark")).append("&merchantKey=")
                    .append(dto.get("merchantKey"));
            LOG.info("银生宝签名明文-->" + plain.toString());
            String mac = MD5Utils.md5(plain.toString());
            dto.put("mac", mac);
            LOG.info("银生宝签名-->" + mac);
            model.addAttribute("dto", dto);
        } catch (Exception e) {
            LOG.error("银生宝跳转异常-->", e);
        }
        return "pub/in_gold/submitYsb";
    }
    
    @RequestMapping("resultYsb")
    @ResponseBody
    public String resultYsb(HttpServletRequest request) {
        try {
            synchronized (this) {
                showParams(request);
                String merchantId = request.getParameter("merchantId");
                String responseMode = request.getParameter("responseMode");
                String orderId = request.getParameter("orderId");
                String currencyType = request.getParameter("currencyType");
                String amount = request.getParameter("amount");
                String returnCode = request.getParameter("returnCode");
                String returnMessage = request.getParameter("returnMessage");
                String mac = request.getParameter("mac");
                String bankCode = request.getParameter("bankCode");
                String merchantKey = "mm999888";
                if ("0000".equals(returnCode)) {
                    String plain = new StringBuffer("merchantId=").append(merchantId).append("&responseMode=")
                            .append(responseMode).append("&orderId=").append(orderId).append("&currencyType=")
                            .append(currencyType).append("&amount=").append(amount).append("&returnCode=")
                            .append(returnCode).append("&returnMessage=").append(returnMessage).append("&merchantKey=")
                            .append(merchantKey).toString();
                    LOG.info("银生回调明文-->" + plain);
                    String myMac = MD5Utils.md5(plain).toUpperCase();
                    LOG.info("银生回调我的密文-->" + myMac);
                    LOG.info("银生回调给的密文-->" + mac);
                    if (myMac.equals(mac)) {
                        LOG.info("银生回调验证通过订单号-->" + orderId);
                        inGoldService.checkResultDinpay(orderId, "");
                        return "SUCCESS";
                    }
                }
            }
            return "FAIL";
        } catch (Exception e) {
            LOG.error("银生宝回调异常-->", e);
            return "FAIL";
        }
    }

    private void showParams(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }
        Set<Map.Entry<String, String>> set = map.entrySet();
        LOG.info("------------------------------");
        for (Map.Entry<String, String> entry : set) {
            LOG.info(entry.getKey() + ":" + entry.getValue());
        }
        LOG.info("------------------------------");
    }
}

class MapKeyComparator implements Comparator<String>{
    public int compare(String str1, String str2) {
        return str1.compareTo(str2);
    }
}
