package com.grup8.OpenEvents.model.api;

import org.json.JSONObject;

public interface ResponseCallback {
    void OnResponse(JSONObject response);
    void OnErrorResponse(String error);
}
