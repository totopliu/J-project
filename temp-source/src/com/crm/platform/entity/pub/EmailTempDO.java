package com.crm.platform.entity.pub;

import java.io.Serializable;

/**
 * 邮件模板数据对象
 * 
 *
 */
public class EmailTempDO implements Serializable {

    private static final long serialVersionUID = -1844103539358610569L;

    private Integer id;
    private String code;
    private String effect;
    private String title;
    private String content;
    private String etitle;
    private String econtent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEtitle() {
        return etitle;
    }

    public void setEtitle(String etitle) {
        this.etitle = etitle;
    }

    public String getEcontent() {
        return econtent;
    }

    public void setEcontent(String econtent) {
        this.econtent = econtent;
    }

}
