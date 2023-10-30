package com.example.main_;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PostRequest extends StringRequest {

    final static private String URL = "http://strawberry.dothome.co.kr/postget.php";
    private Map<String, String> map;

    public PostRequest(String poster_registrant, String poster_parentCategory, String poster_childCategory ,
                       String poster_title, String poster_content, String startYear, String startMonth, String startDay,
                       String endYear, String endMonth, String endDay, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("poster_registrant", poster_registrant);
        map.put("poster_parentCategory", poster_parentCategory);
        map.put("poster_childCategory", poster_childCategory);
        map.put("poster_title", poster_title);
        map.put("poster_content", poster_content);
        map.put("startYear", startYear);
        map.put("startMonth", startMonth);
        map.put("startDay", startDay);
        map.put("endYear", endYear);
        map.put("endMonth", endMonth);
        map.put("endDay", endDay);
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}