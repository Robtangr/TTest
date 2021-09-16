package com.test.intproj.dto;

public class OutputMessage {

    public OutputMessage(){}

    public OutputMessage(String code, String name, String msg, String target){
        this.code = code;
        this.name = name;
        this.msg = msg;
        this.target = target;
    }

    private String code;
    private String name;
    private String msg;
    private String target;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }



        /*
            {
                "countryCode":""
                "countryName":"",
                "message": ""
                "target": ""
            }

         */
}
