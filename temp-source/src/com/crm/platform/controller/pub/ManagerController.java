package com.crm.platform.controller.pub;

import java.io.File;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.constant.Constant;
import com.crm.platform.controller.BaseController;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.DataGrid;
import com.crm.platform.entity.pub.ManagerEntity;
import com.crm.platform.service.pub.ManagerService;
import com.crm.util.MsgUtil;
import com.crm.util.SessionUtil;
import com.github.pagehelper.PageInfo;

import springfox.documentation.annotations.ApiIgnore;

/**
 * 账户管理
 * 
 * 
 */
@Controller
@RequestMapping("/pub/manager")
@ApiIgnore
public class ManagerController extends BaseController {

    private final static Logger LOG = LoggerFactory.getLogger(ManagerController.class);

    @Autowired
    private ManagerService managerService;

    /**
     * 打开账号管理列表页
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {
        return "pub/manager/list";
    }

    /**
     * 打开账号编辑页
     * 
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit")
    public String edit(Integer id, Model model) {
        if (id != null) {
            model.addAttribute("dto", this.managerService.selectByPrimaryKey(id));
        }
        return "pub/manager/edit";
    }

    /**
     * 查询账号分页列表
     * 
     * @param grid
     * @param name
     * @return
     */
    @RequestMapping(value = "/query")
    @ResponseBody
    public PageInfo<JSONObject> query(DataGrid grid, String name, String login) {
        return this.managerService.listManager(grid, name, login);
    }

    /**
     * 保存账户
     * 
     * @param dto
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public AjaxJson save(@ModelAttribute(value = "dto") ManagerEntity dto) {
        try {
            String lg = SessionUtil.getLg();
            return managerService.save(dto, lg);
        } catch (Exception e) {
            LOG.error("", e);
        }
        return null;
    }

    /**
     * 删除账户
     * 
     * @param ids
     * @return
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public AjaxJson del(int[] ids) {
        return this.managerService.batchDelete(ids);
    }

    /**
     * 用户上传身份证图片
     * 
     * @param uploadFile
     * @param request
     * @return
     */
    @RequestMapping(value = "/upload")
    public @ResponseBody AjaxJson upload(@RequestParam MultipartFile uploadFile, HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("/");
        AjaxJson ajaxJson = new AjaxJson();
        try {
            if (!uploadFile.isEmpty()) {
                String suffix = uploadFile.getOriginalFilename().toLowerCase();
                suffix = suffix.substring(suffix.lastIndexOf("."));
                if (".jpg".equals(suffix) || ".jpeg".equals(suffix) || ".png".equals(suffix)) {
                    String filename = String.valueOf(System.currentTimeMillis());
                    Calendar calendar = Calendar.getInstance();
                    String filepath = path + File.separator + "idcard" + File.separator + calendar.get(Calendar.YEAR)
                            + File.separator + (calendar.get(Calendar.MONTH) + 1) + File.separator
                            + calendar.get(Calendar.DAY_OF_MONTH) + File.separator + filename + suffix;
                    File file = new File(filepath);
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    if (!file.exists()) {
                        file.createNewFile();
                        FileUtils.copyInputStreamToFile(uploadFile.getInputStream(), file);
                    }
                    String url = "idcard" + "/" + calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1)
                            + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + filename + suffix;
                    ajaxJson.setSuccess(true);
                    ajaxJson.setData(url);
                }
            }
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(MsgUtil.getSessionLgMsg(Constant.REGISTERED_ERROR_MESSAGE_UPLOAD));
            LOG.error("", e);
        }
        return ajaxJson;
    }

    /**
     * 查询直接下属
     * 
     * @return
     */
    @RequestMapping(value = "findUnderlingList")
    @ResponseBody
    public AjaxJson findUnderlingList() {
        try {
            ManagerEntity user = SessionUtil.getSession();
            return managerService.getUnderling(user.getManagerid());
        } catch (Exception e) {
            LOG.error("", e);
        }
        return null;
    }

    /**
     * 查询所有下属（呈树形结构展示）
     * 
     * @return
     */
    @RequestMapping(value = "findUnderlingTree")
    @ResponseBody
    public AjaxJson findUnderlingTree() {
        try {
            ManagerEntity user = SessionUtil.getSession();
            return managerService.getUnderlingTreeJson(user.getManagerid());
        } catch (Exception e) {
            LOG.error("", e);
        }
        return null;
    }

    /**
     * 查询已审核通过用户列表
     * 
     * @return
     */
    @RequestMapping(value = "listReviewedManager")
    @ResponseBody
    public AjaxJson listReviewedManager() {
        return managerService.listReviewedManager();
    }

    /**
     * 打开设置上级页
     * 
     * @param managerId
     * @return
     */
    @RequestMapping("setting")
    public String settingPage(Integer managerId, Model model) {
        model.addAttribute("managerId", managerId);
        return "pub/manager/setting";
    }

    /**
     * 设置散户上级代理
     * 
     * @param managerId
     * @param belongId
     * @return
     */
    @RequestMapping("settingBelong")
    @ResponseBody
    public AjaxJson settingBelong(Integer managerId, Integer belongId) {
        return managerService.settingBelong(managerId, belongId);
    }

    /**
     * 打开设置交易类型
     * 
     * @param managerId
     * @param model
     * @return
     */
    @RequestMapping("updateTransactionType")
    public String updateTransactionType(Integer managerId, Model model) {
        model.addAttribute("managerId", managerId);
        return "pub/manager/updateTransactionType";
    }

    /**
     * 打开设置返佣账号页
     * 
     * @param managerId
     * @return
     */
    @RequestMapping("setRebateLogin")
    public String setRebateLogin(Model model, Integer managerId) {
        model.addAttribute("managerId", managerId);
        model.addAttribute("dto", managerService.selectByPrimaryKey(managerId));
        return "pub/manager/setRebateLogin";
    }

    /**
     * 设置返佣账号
     * 
     * @param managerId
     * @param rebateLogin
     * @param rebateLoginPwd
     * @return
     */
    @RequestMapping("updateRebateLogin")
    @ResponseBody
    public AjaxJson updateRebateLogin(Integer managerId, Integer rebateLogin, String rebateLoginPwd) {
        return managerService.updateRebateLogin(managerId, rebateLogin, rebateLoginPwd);
    }

    /**
     * 重发审核通过邮件
     * 
     * @param id
     * @return
     */
    @RequestMapping("sendEmail")
    @ResponseBody
    public AjaxJson sendEmail(Integer id) {
        return managerService.sendEmail(id);
    }
}
