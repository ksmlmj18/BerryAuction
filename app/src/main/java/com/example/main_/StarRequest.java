package com.example.main_;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class StarRequest extends StringRequest {

    final static private String URL = "http://strawberry.dothome.co.kr/successrating.php";
    private Map<String, String> map;

    public StarRequest(String user_nickName, String user_rating, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);


        map = new HashMap<>();
        map.put("user_nickName",user_nickName);
        map.put("user_rating", user_rating);

    }

    @Nullable
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}