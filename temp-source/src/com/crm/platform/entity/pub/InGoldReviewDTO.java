package com.crm.platform.entity.pub;

public class InGoldReviewDTO {

    private Integer id;
    private String login;
    private Integer state;
    private String inCreateDateStart;
    private String inCreateDateEnd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getInCreateDateStart() {
        return inCreateDateStart;
    }

    public void setInCreateDateStart(String inCreateDateStart) {
        this.inCreateDateStart = inCreateDateStart;
    }

    public String getInCreateDateEnd() {
        return inCreateDateEnd;
    }

    public void setInCreateDateEnd(String inCreateDateEnd) {
        this.inCreateDateEnd = inCreateDateEnd;
    }

}
