package com.crm.platform.entity.param;

public class OutGoldReviewParam {

    private String login;
    private Integer state;
    private String outCreateDateStart;
    private String outCreateDateEnd;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getOutCreateDateStart() {
        return outCreateDateStart;
    }

    public void setOutCreateDateStart(String outCreateDateStart) {
        this.outCreateDateStart = outCreateDateStart;
    }

    public String getOutCreateDateEnd() {
        return outCreateDateEnd;
    }

    public void setOutCreateDateEnd(String outCreateDateEnd) {
        this.outCreateDateEnd = outCreateDateEnd;
    }

}
