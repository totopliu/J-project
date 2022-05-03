package com.crm.platform.controller.pub;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.crm.platform.constant.Constant;
import com.crm.platform.entity.AjaxJson;
import com.crm.platform.entity.pub.ManagerEntity;
import com.crm.platform.service.pub.ManagerService;
import com.crm.util.MsgUtil;
import com.crm.util.SessionUtil;
import com.crm.util.http.ResponseUtils;
import com.crm.util.upload.FileRepository;
import com.crm.util.upload.UploadUtils;

/**
 * 个人资料
 * 
 * 
 */
@Controller
@RequestMapping
public class ProfileController {

    private final static Logger LOG = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private ManagerService managerService;
    @Autowired
    private FileRepository fileRepository;

    /**
     * 打开个人头像页
     * 
     * @return
     */
    @RequestMapping("profile")
    public String profile() {
        return "profile";
    }

    /**
     * 打开个人资料页
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "profileInfo")
    public String profileInfo(Model model) {
        ManagerEntity user = SessionUtil.getSession();
        model.addAttribute("dto", managerService.selectByPrimaryKey(user.getManagerid()));
        if (user.getLogin() != null) {
            Double balance = managerService.getBalanceByLogin(user.getLogin());
            model.addAttribute("balance", balance);
        } else {
            model.addAttribute("balance", BigDecimal.ZERO);
        }
        return "profile_info";
    }

    /**
     * 打开个人设置页
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "profileSetting")
    public String profileSetting(Model model) {
        ManagerEntity user = SessionUtil.getSession();
        ManagerEntity dto = managerService.selectByPrimaryKey(user.getManagerid());
        model.addAttribute("dto", dto);
        return "profile_setting";
    }

    /**
     * 保存个人资料
     * 
     * @param dto
     * @return
     */
    @RequestMapping(value = "updProfileInfo")
    @ResponseBody
    public AjaxJson updProfileInfo(ManagerEntity dto) {
        return managerService.updateProfileInfo(dto);
    }

    /**
     * 保存个人设置
     * 
     * @param autoRebate
     * @return
     */
    @RequestMapping(value = "saveProfileSetting")
    @ResponseBody
    public AjaxJson saveProfileSetting(Integer autoRebate) {
        ManagerEntity user = SessionUtil.getSession();
        return managerService.updateProfileSetting(autoRebate, user.getManagerid());
    }

    /**
     * 修改个人密码
     * 
     * @param password
     * @return
     */
    @RequestMapping(value = "savePass")
    @ResponseBody
    public AjaxJson savePass(String password) {
        ManagerEntity user = SessionUtil.getSession();
        return managerService.updatePassword(user.getManagerid(), password);
    }

    /**
     * 修改MT主密码
     * 
     * @param password
     * @return
     */
    @RequestMapping(value = "saveMTPass")
    @ResponseBody
    public AjaxJson saveMTPass(String password) {
        ManagerEntity user = SessionUtil.getSession();
        return managerService.updateMTPass(user.getManagerid(), password);
    }

    /**
     * 修改MT投资密码
     * 
     * @param password
     * @return
     */
    @RequestMapping(value = "saveMTGcPass")
    @ResponseBody
    public AjaxJson saveMTGcPass(String password) {
        ManagerEntity user = SessionUtil.getSession();
        return managerService.updateMTGcPass(user.getManagerid(), password);
    }

    /**
     * 上传个人头像
     * 
     * @param request
     * @param file3
     * @param response
     * @param model
     * @throws IOException
     */
    @RequestMapping(value = "/upload")
    public void upload(MultipartHttpServletRequest request,
            @RequestParam(value = "__source", required = false) MultipartFile file3, HttpServletResponse response,
            ModelMap model) throws IOException {
        Result result = new Result();
        MultipartFile file = null;
        Map<String, MultipartFile> file2 = request.getFileMap();
        String path = "";
        for (Map.Entry<String, MultipartFile> entry : file2.entrySet()) {
            file = entry.getValue();
        }
        if (file == null || file.isEmpty()) {
            result.msg = MsgUtil.getSessionLgMsg(Constant.FILE_SMALL);
        } else {
            result.success = false;
            result.msg = MsgUtil.getSessionLgMsg(Constant.REGISTERED_ERROR_MESSAGE_UPLOAD);
            boolean isSourcePic = "__source".equals(file.getName());
            // file对象有问题
            String fieldName = file.getName();
            if (file3 != null) {
                isSourcePic = true;
                fieldName = file3.getOriginalFilename();
                file = file3;
            }
            // 如果是原始图片 file
            // 域的名称或者以默认的头像域名称的部分“__avatar”打头(默认的头像域名称：__avatar1,2,3...，可在插件配置参数中自定义，参数名：avatar_field_names)
            else if (isSourcePic || fieldName.startsWith("__avatar")) {
                // 原始图片（默认的 file 域的名称是__source，可在插件配置参数中自定义。参数名：src_field_name）。
                if (isSourcePic) {
                    // 文件名，如果是本地或网络图片为原始文件名、如果是摄像头拍照则为 *FromWebcam.jpg
                    String sourceFileName = fieldName;
                    // 原始文件的扩展名(不包含“.”)
                    String sourceExtendName = sourceFileName.substring(sourceFileName.lastIndexOf('.') + 1);
                    String filename = UploadUtils.generateFilename(sourceExtendName);
                    path = "upload" + filename;
                }
                // 头像图片（默认的 file
                // 域的名称：__avatar1,2,3...，可在插件配置参数中自定义，参数名：avatar_field_names）。
                else {
                    String filename = UploadUtils.generateFilename("jpg");
                    path = "upload" + filename;
                }
                fileRepository.storeByFilename(path, file);
            }
        }
        if (path != null) {
            result.sourceUrl = request.getContextPath() + "/" + path;
            System.out.println(result.sourceUrl);
            result.success = true;
            result.msg = MsgUtil.getSessionLgMsg(Constant.UPLOAD_SUCCESS);
            ManagerEntity userEntity = SessionUtil.getSession();
            userEntity.setPhoto(path);
            this.managerService.update(userEntity);
        }
        ResponseUtils.renderText(response, JSONObject.toJSONString(result));
    }

    /**
     * 上传银行卡照片
     * 
     * @param uploadFile
     * @param request
     * @return
     */
    @RequestMapping(value = "/uploadCard")
    public @ResponseBody AjaxJson upload(@RequestParam MultipartFile uploadFile, HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("/");
        AjaxJson ajaxJson = new AjaxJson();
        try {
            if (!uploadFile.isEmpty()) {
                String suffix = uploadFile.getOriginalFilename().toLowerCase();
                suffix = suffix.substring(suffix.lastIndexOf("."));
                String filename = String.valueOf(System.currentTimeMillis());
                Calendar calendar = Calendar.getInstance();
                String filepath = path + File.separator + "bankcard" + File.separator + calendar.get(Calendar.YEAR)
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
                String url = "bankcard" + "/" + calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1)
                        + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + filename + suffix;
                ajaxJson.setSuccess(true);
                ajaxJson.setData(url);
            }
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(MsgUtil.getSessionLgMsg(Constant.REGISTERED_ERROR_MESSAGE_UPLOAD));
            LOG.error("", e);
        }
        return ajaxJson;
    }

    /**
     * 表示上传的结果。
     */
    @SuppressWarnings("unused")
    private class Result {
        /**
         * 表示图片是否已上传成功。
         */
        public Boolean success;
        public String userid;
        public String username;

        /**
         * 自定义的附加消息。
         */
        public String msg;
        /**
         * 表示原始图片的保存地址。
         */
        public String sourceUrl;
    }

}
