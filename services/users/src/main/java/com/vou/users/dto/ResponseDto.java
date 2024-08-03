package com.vou.users.dto;

import org.springframework.http.HttpStatus;

public class ResponseDto {
    private HttpStatus status;
    private String message;

    public ResponseDto(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseDto{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}