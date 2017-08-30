package com.example.priya.testquestionone.pojo;

/**
 * Created by priya on 25/8/17.
 */

public class Response {
    String status;
    String message;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public Response(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;

    }

}
