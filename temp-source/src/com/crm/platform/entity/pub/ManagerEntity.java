package com.crm.platform.entity.pub;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * TManager entity
 */
@Table(name = "t_sys_manager")
public class ManagerEntity implements java.io.Serializable {

    @Transient
    private static final long serialVersionUID = 7868123622345888336L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer managerid;
    private Integer role;
    private String account;
    private String password;
    private String name;
    private String lastloginip;
    private Date lastlogintime;
    private String credentialsSalt;
    private String locked;
    private String photo;
    private String idcard;
    private String idcardurl;
    private String idcardbkurl;
    private Date createtime;
    private Integer reviewState;
    private String reviewReason;
    private Integer belongid;
    private String phone;
    private String email;
    private Integer login;
    private String bank;
    private String bankCard;
    private String bankCardUrl;
    private Integer autoRebate;
    private String loginPwd;
    private Integer rebateLogin;
    private String rebateLoginPwd;
    private String passgc;
    private Integer type;
    private Integer level;
    private String ename;
    private String addrurl;
    private Integer country;

    public Integer getAutoRebate() {
        return autoRebate;
    }

    public void setAutoRebate(Integer autoRebate) {
        this.autoRebate = autoRebate;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getBankCardUrl() {
        return bankCardUrl;
    }

    public void setBankCardUrl(String bankCardUrl) {
        this.bankCardUrl = bankCardUrl;
    }

    public Integer getLogin() {
        return login;
    }

    public void setLogin(Integer login) {
        this.login = login;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getBelongid() {
        return belongid;
    }

    public void setBelongid(Integer belongid) {
        this.belongid = belongid;
    }

    public String getReviewReason() {
        return reviewReason;
    }

    public void setReviewReason(String reviewReason) {
        this.reviewReason = reviewReason;
    }

    public Integer getReviewState() {
        return reviewState;
    }

    public void setReviewState(Integer reviewState) {
        this.reviewState = reviewState;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getIdcardurl() {
        return idcardurl;
    }

    public void setIdcardurl(String idcardurl) {
        this.idcardurl = idcardurl;
    }

    public String getIdcardbkurl() {
        return idcardbkurl;
    }

    public void setIdcardbkurl(String idcardbkurl) {
        this.idcardbkurl = idcardbkurl;
    }

    public Integer getManagerid() {
        return managerid;
    }

    public void setManagerid(Integer managerid) {
        this.managerid = managerid;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastloginip() {
        return this.lastloginip;
    }

    public void setLastloginip(String lastloginip) {
        this.lastloginip = lastloginip;
    }

    public Date getLastlogintime() {
        return this.lastlogintime;
    }

    public void setLastlogintime(Date lastlogintime) {
        this.lastlogintime = lastlogintime;
    }

    public String getCredentialsSalt() {
        return credentialsSalt;
    }

    public void setCredentialsSalt(String credentialsSalt) {
        this.credentialsSalt = credentialsSalt;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    @Transient
    public boolean getIssuper() {
        return this.managerid == 1;
    }

    public Integer getRebateLogin() {
        return rebateLogin;
    }

    public void setRebateLogin(Integer rebateLogin) {
        this.rebateLogin = rebateLogin;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getRebateLoginPwd() {
        return rebateLoginPwd;
    }

    public void setRebateLoginPwd(String rebateLoginPwd) {
        this.rebateLoginPwd = rebateLoginPwd;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getPassgc() {
        return passgc;
    }

    public void setPassgc(String passgc) {
        this.passgc = passgc;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getAddrurl() {
        return addrurl;
    }

    public void setAddrurl(String addrurl) {
        this.addrurl = addrurl;
    }

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

}