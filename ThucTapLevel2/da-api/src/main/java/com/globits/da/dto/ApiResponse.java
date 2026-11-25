package com.globits.da.dto;

public class ApiResponse<T> {
    private T data;
    private String errorCode;
    private String errorMessage;

    public ApiResponse(T data) {
        this.data = data;
        this.errorCode = "SUCCESS";
        this.errorMessage = "Thao tác thành công";
    }

    public ApiResponse(String errorCode, String errorMessage) {
        this.data = null;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
