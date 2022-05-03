package com.crm.platform.controller.pub;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import springfox.documentation.annotations.ApiIgnore;

import com.crm.platform.constant.Constant;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.pub.CountryDO;
import com.crm.platform.entity.pub.ManagerEntity;
import com.crm.platform.enums.ManagerReviewStateEnum;
import com.crm.platform.service.pub.ManagerService;
import com.crm.platform.service.pub.MenuService;
import com.crm.util.MathUtil;
import com.crm.util.MsgUtil;
import com.crm.util.SessionUtil;

/**
 * 用户登录控制器
 *
 * 
 */
@Controller
@ApiIgnore
public class LoginController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private ManagerService managerService;

    /**
     * 打开登录页
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
    public String login(HttpServletRequest request) {
        return SessionUtil.getSession() != null ? "redirect:/index" : "login";
    }

    /**
     * 打开首页
     * 
     * @param model
     * @return
     */
    @RequestMapping("main")
    public String main(Model model) {
        ManagerEntity user = SessionUtil.getSession();
        model.addAttribute("dto", managerService.selectByPrimaryKey(user.getManagerid()));
        if (user.getLogin() != null) {
            Double balance = managerService.getBalanceByLogin(user.getLogin());
            Double rebate = managerService.getRebateByManagerId(user.getManagerid());
            Double rebateBalance = managerService.getBalanceByLogin(user.getRebateLogin());
            model.addAttribute("balance",
                    (balance != null) ? MathUtil.roundDoubleSetHalfUpToStr(2, balance) : BigDecimal.ZERO);
            model.addAttribute("rebate", MathUtil.roundDoubleSetHalfUpToStr(2, rebate));
            model.addAttribute("rebateBalance",
                    (rebateBalance != null) ? MathUtil.roundDoubleSetHalfUpToStr(2, rebateBalance) : BigDecimal.ZERO);
        } else {
            model.addAttribute("balance", BigDecimal.ZERO);
            model.addAttribute("rebate", BigDecimal.ZERO);
            model.addAttribute("rebateBalance", BigDecimal.ZERO);
        }
        return "main";
    }

    /**
     * 打开主页面，查询菜单
     * 
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("index")
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("list", menuService.listTree(SessionUtil.getSession()));
        return "index";
    }

    /**
     * 用户登录
     * 
     * @param request
     * @param response
     * @param dto
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse response, ManagerEntity dto) {
        request.setAttribute("ACCOUNT", dto.getAccount());
        String lg = request.getParameter("lg");
        if (StringUtils.isBlank(lg)) {
            lg = "zh";
        }
        request.setAttribute("lg", lg);
        // 验证用户审核状态
        ManagerEntity user = managerService.getByAccount(dto.getAccount());
        if (user != null) {
            int reviewState = user.getReviewState();
            if (reviewState != ManagerReviewStateEnum.AUDIT_APPROVAL.getValue()) {
                if (reviewState == ManagerReviewStateEnum.UNAUDITED.getValue()) {
                    request.setAttribute("LOGIN_ERROR_CODE", Constant.LOGIN_ERROR_CODE_100005);
                    request.setAttribute("LOGIN_ERROR_MESSAGE",
                            MsgUtil.getLgMsg(Constant.LOGIN_ERROR_MESSAGE_UNREVIEW, lg));
                    return "login";
                } else if (reviewState == ManagerReviewStateEnum.AUDIT_FAILED.getValue()) {
                    request.setAttribute("LOGIN_ERROR_CODE", Constant.LOGIN_ERROR_CODE_100006);
                    request.setAttribute("LOGIN_ERROR_MESSAGE", MsgUtil
                            .getLgMsg(Constant.LOGIN_ERROR_MESSAGE_REVIEW_FAIL, lg).concat(user.getReviewReason()));
                    return "login";
                }
            }
        }
        // 想要得到 SecurityUtils.getSubject() 的对象．．访问地址必须跟shiro的拦截地址内．不然后会报空指针
        Subject sub = SecurityUtils.getSubject();
        // 用户输入的账号和密码,,存到UsernamePasswordToken对象中..然后由shiro内部认证对比,
        // 认证执行者交由ShiroDbRealm中doGetAuthenticationInfo处理
        // 当以上认证成功后会向下执行,认证失败会抛出异常
        UsernamePasswordToken token = new UsernamePasswordToken(dto.getAccount(), dto.getPassword());
        try {
            sub.login(token);
        } catch (LockedAccountException lae) {
            token.clear();
            request.setAttribute("LOGIN_ERROR_CODE", Constant.LOGIN_ERROR_CODE_100002);
            request.setAttribute("LOGIN_ERROR_MESSAGE", MsgUtil.getLgMsg(Constant.LOGIN_ERROR_MESSAGE_SYSTEMERROR, lg));
            return "login";
        } catch (ExcessiveAttemptsException e) {
            token.clear();
            request.setAttribute("LOGIN_ERROR_CODE", Constant.LOGIN_ERROR_CODE_100003);
            request.setAttribute("LOGIN_ERROR_MESSAGE", MsgUtil.getLgMsg(Constant.LOGIN_ERROR_MESSAGE_MAXERROR, lg));
            return "login";
        } catch (AuthenticationException e) {
            token.clear();
            request.setAttribute("LOGIN_ERROR_CODE", Constant.LOGIN_ERROR_CODE_100001);
            request.setAttribute("LOGIN_ERROR_MESSAGE", MsgUtil.getLgMsg(Constant.LOGIN_ERROR_MESSAGE_USERERROR, lg));
            return "login";
        }
        return "redirect:/index?lg=" + lg;
    }

    /**
     * 显示没有权限页
     * 
     * @return
     */
    @RequestMapping("denied")
    public String denied() {
        return "denied";
    }

    /**
     * 打开新用户注册页
     * 
     * @param account
     * @param belongid
     * @param model
     * @return
     */
    @RequestMapping(value = "registered/belong_{belongid}", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
    public String registered(@PathVariable String belongid, Model model, HttpServletRequest request) {
        model.addAttribute("belongid", belongid);
        String lg = request.getParameter("lg");
        if (StringUtils.isBlank(lg)) {
            lg = "zh";
        }
        request.setAttribute("lg", lg);
        List<CountryDO> countryDOs = managerService.listCountry();
        model.addAttribute("country", countryDOs);
        return "registered";
    }

    /**
     * 新用户提交注册信息
     * 
     * @param request
     * @param dto
     * @return
     */
    @RequestMapping(value = "registered", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson registered(HttpServletRequest request, ManagerEntity dto, String lg) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            dto.setReviewState(0);
            dto.setEmail(dto.getAccount());
            ajaxJson = managerService.save(dto, lg);
            if (ajaxJson.isSuccess()) {
                ajaxJson.setMsg(MsgUtil.getLgMsg(Constant.REGISTERED_SUCCESS_MESSAGE, lg));
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(MsgUtil.getLgMsg(Constant.REGISTERED_ERROR_MESSAGE, lg));
        }
        return ajaxJson;
    }

    /**
     * 跳转忘记密码填写账户邮箱页
     * 
     * @param request
     * @return
     */
    @RequestMapping("forgetPass")
    public String forgetPass(HttpServletRequest request) {
        String lg = request.getParameter("lg");
        if (StringUtils.isBlank(lg)) {
            lg = "zh";
        }
        request.setAttribute("lg", lg);
        return "forget_pass";
    }

    /**
     * 发送重置密码邮件
     * 
     * @param account
     * @param lg
     * @return
     */
    @RequestMapping("sendVerification")
    @ResponseBody
    public AjaxJson sendVerification(String account, String lg) {
        return managerService.sendVerification(account, lg);
    }

    /**
     * 打开重置密码页
     * 
     * @param belongid
     * @param managerid
     * @param tamp
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "reset/page_{hash}_{managerid}_{tamp}", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
    public String reset(@PathVariable String hash, @PathVariable String managerid, @PathVariable String tamp,
            Model model, HttpServletRequest request) {
        String lg = request.getParameter("lg");
        if (StringUtils.isBlank(lg)) {
            lg = "zh";
        }
        request.setAttribute("lg", lg);
        model.addAttribute("hash", hash);
        model.addAttribute("managerid", managerid);
        model.addAttribute("tamp", tamp);
        return "reset";
    }

    /**
     * 重置新密码
     * 
     * @param hash
     * @param managerid
     * @param tamp
     * @param password
     * @return
     */
    @RequestMapping("resetPass")
    @ResponseBody
    public AjaxJson resetPass(String hash, String managerid, String tamp, String password, String lg) {
        return managerService.resetPass(hash, managerid, tamp, password, lg);
    }

    /**
     * 打开手机登录页
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "mobileLogin", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
    public String mobileLogin(HttpServletRequest request) {
        return SessionUtil.getSession() != null ? "redirect:/index" : "mobile/login";
    }

    /**
     * 手机端登录
     * 
     * @param account
     * @param password
     * @return
     */
    @RequestMapping(value = "mobileLogin", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson mobileLogin(String account, String password) {
        AjaxJson json = new AjaxJson();
        // 验证用户审核状态
        ManagerEntity user = managerService.getByAccount(account);
        if (user != null) {
            int reviewState = user.getReviewState();
            if (reviewState != ManagerReviewStateEnum.AUDIT_APPROVAL.getValue()) {
                if (reviewState == ManagerReviewStateEnum.UNAUDITED.getValue()) {
                    json.setMsg(MsgUtil.getLgMsg(Constant.LOGIN_ERROR_MESSAGE_UNREVIEW, "zh"));
                    return json;
                } else if (reviewState == ManagerReviewStateEnum.AUDIT_FAILED.getValue()) {
                    json.setMsg(MsgUtil.getLgMsg(Constant.LOGIN_ERROR_MESSAGE_REVIEW_FAIL, "zh")
                            .concat(user.getReviewReason()));
                    return json;
                }
            }
        }
        // 想要得到 SecurityUtils.getSubject() 的对象．．访问地址必须跟shiro的拦截地址内．不然后会报空指针
        Subject sub = SecurityUtils.getSubject();
        // 用户输入的账号和密码,,存到UsernamePasswordToken对象中..然后由shiro内部认证对比,
        // 认证执行者交由ShiroDbRealm中doGetAuthenticationInfo处理
        // 当以上认证成功后会向下执行,认证失败会抛出异常
        UsernamePasswordToken token = new UsernamePasswordToken(account, password);
        try {
            sub.login(token);
            json.setSuccess(true);
            return json;
        } catch (LockedAccountException lae) {
            token.clear();
            json.setMsg(MsgUtil.getLgMsg(Constant.LOGIN_ERROR_MESSAGE_SYSTEMERROR, "zh"));
            return json;
        } catch (ExcessiveAttemptsException e) {
            token.clear();
            json.setMsg(MsgUtil.getLgMsg(Constant.LOGIN_ERROR_MESSAGE_MAXERROR, "zh"));
            return json;
        } catch (AuthenticationException e) {
            token.clear();
            json.setMsg(MsgUtil.getLgMsg(Constant.LOGIN_ERROR_MESSAGE_USERERROR, "zh"));
            return json;
        }

    }
}
