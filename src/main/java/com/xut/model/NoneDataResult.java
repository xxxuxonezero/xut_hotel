package com.xut.model;

public class NoneDataResult {
    int code;
    public NoneDataResult() {
        this.code = Code.SUCCESS;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isOK() {
        return this.code == Code.SUCCESS;
    }

    public boolean isNotOK() {
        return !isOK();
    }
}
