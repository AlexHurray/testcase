package com.example.ermolaenkoalex.admodule.api;

import com.google.gson.annotations.SerializedName;

public class Answer {
    private static final String STATUS_OK = "OK";

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("url")
    private String url;

    public Answer(String status, String message, String url) {
        this.status = status;
        this.message = message;
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public String getUrl() {
        return url;
    }

    public boolean isOK() {
        return status != null && status.equalsIgnoreCase(STATUS_OK);
    }
}
