package com.grup8.OpenEvents.model.api;

import android.app.Application;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.grup8.OpenEvents.model.OpenEventsApplication;
import com.grup8.OpenEvents.model.UserModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ApiCommunicator {

    private static final String BASE_URI = "http://puigmal.salle.url.edu/api/v2";
    private static final RequestQueue queue;

    static {
        queue = new RequestQueue(new DiskBasedCache(OpenEventsApplication.getAppContext().getCacheDir(), 1024 * 1024),
                new BasicNetwork(new HurlStack()));

        queue.start();
    }

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

        StringRequest request = new StringRequest(method, BASE_URI + relativeURL,
                callback::OnResponse,
                error -> callback.OnErrorResponse(error.getMessage())){

            @Override
            public byte[] getBody() {
                return requestBody.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                //Add OAuth 2.0 Bearer Token Header
                if(useToken && token != null)
                    headers.put("Authorization", "Bearer " + token);

                return headers;
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
