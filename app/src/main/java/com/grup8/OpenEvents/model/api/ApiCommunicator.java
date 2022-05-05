package com.grup8.OpenEvents.model.api;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.grup8.OpenEvents.model.OpenEventsApplication;

import org.json.JSONObject;

public class ApiCommunicator {

    private static final String BASE_URI = "http://puigmal.salle.url.edu/api";
    private static RequestQueue queue = Volley.newRequestQueue(OpenEventsApplication.getAppContext());

    public static void makeRequest(String relativeURL, RequestMethod requestMethod, JSONObject requestBody, ResponseCallback callback){
        int method;
        switch (requestMethod){
            case POST: method = Request.Method.POST;
            case PUT: method = Request.Method.PUT;
            case DELETE: method = Request.Method.DELETE;
            default: method = Request.Method.GET;
        }

        JsonObjectRequest request = new JsonObjectRequest(method, BASE_URI + relativeURL, requestBody,
                response -> callback.OnResponse(response),
                error -> callback.OnErrorResponse(error.getMessage()));

        queue.add(request); //Add the request to the request queue
    }


}
