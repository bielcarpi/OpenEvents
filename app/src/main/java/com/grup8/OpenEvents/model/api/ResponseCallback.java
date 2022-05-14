package com.grup8.OpenEvents.model.api;

public interface ResponseCallback {
    void OnResponse(String response);
    void OnErrorResponse(String error);
}
