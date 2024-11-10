package com.iamamansid.spring_ai_backend.models.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ApiResponse<T> {

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    private boolean result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getCookies() {
        return cookies;
    }

    public void setCookies(T cookies) {
        this.cookies = cookies;
    }

    public T getSessionKeys() {
        return sessionKeys;
    }

    public void setSessionKeys(T sessionKeys) {
        this.sessionKeys = sessionKeys;
    }

    private int code;
    private T response;
    private String error;
    private T cookies;
    private T sessionKeys;

}
