package com.mhj.web;

/**
 * @author mhj
 * @date 2019/11/15
 */
public class AbstractResponse<T> {

    public static final String SUCCESS = "success";
    protected int status;
    protected String message;

    public AbstractResponse() {
    }

    public static String getSUCCESS() {
        return "success";
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
