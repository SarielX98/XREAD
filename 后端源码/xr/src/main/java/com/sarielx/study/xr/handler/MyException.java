package com.sarielx.study.xr.handler;

public class MyException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public MyException(String code,String msg){
        this.code = code;
        this.msg = msg;
    }

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
