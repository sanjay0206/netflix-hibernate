package com.netflix.dto;

import com.netflix.enums.Status;

public class ResponseDto {
    private Status status;
    private String message;
    private int code;

    public ResponseDto() {
    }

    public ResponseDto(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseDto(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ResponseDto{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", code=" + code +
                '}';
    }
}
