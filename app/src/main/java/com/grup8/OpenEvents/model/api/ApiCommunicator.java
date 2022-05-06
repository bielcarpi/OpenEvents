package com.grup8.OpenEvents.model.api;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.grup8.OpenEvents.model.OpenEventsApplication;
import com.grup8.OpenEvents.model.UserModel;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ApiCommunicator {

    private static final String BASE_URI = "http://puigmal.salle.url.edu/api/v2";
    private static final RequestQueue queue = Volley.newRequestQueue(OpenEventsApplication.getAppContext());

    private static String token;

    public static void makeRequest(String relativeURL, RequestMethod requestMethod, JSONObject requestBody, ResponseCallback callback){
        makeRequest(relativeURL, requestMethod, requestBody, false, callback);
    }


    public static void makeRequest(String relativeURL, RequestMethod requestMethod, JSONObject requestBody, boolean useToken, ResponseCallback callback){
        int method;
        switch (requestMethod){
            case POST: method = Request.Method.POST; break;
            case PUT: method = Request.Method.PUT; break;
            case DELETE: method = Request.Method.DELETE; break;
            default: method = Request.Method.GET; break;
        }

        JsonObjectRequest request = new JsonObjectRequest(method, BASE_URI + relativeURL, requestBody,
            response -> callback.OnResponse(response),
            error -> callback.OnErrorResponse(error.getMessage())){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = super.getHeaders();
                //Add OAuth 2.0 Bearer Token Header
                if(token != null) params.put("Authorization", "Bearer" + token);
                return params;
            }
        };

        queue.add(request); //Add the request to the request queue
    }


    public static void setToken(String token){
        ApiCommunicator.token = token;
    }
    public static void deleteToken(){
        token = null;
    }


}
