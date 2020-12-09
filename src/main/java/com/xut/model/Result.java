package com.xut.model;

import java.util.List;
import java.util.Map;

public class Result<T> {
    int code;
    T data;

    public Result() {
        this.code = Code.SUCCESS;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isValid() {
        return isOK() && isNotEmpty();
    }

    public boolean isNotValid() {
        return isNotOK() || isEmpty();
    }

    public boolean isOK() {
        return this.code == Code.SUCCESS;
    }

    public boolean isNotOK() {
        return !isOK();
    }

    public boolean isEmpty() {
        return data == null
                || data instanceof List && ((List<?>) data).size() == 0
                || data instanceof Map && ((Map<?, ?>) data).size() == 0;
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }
}
