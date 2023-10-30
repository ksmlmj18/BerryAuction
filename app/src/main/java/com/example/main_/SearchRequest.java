package com.example.main_;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SearchRequest extends StringRequest {

    final static private String URL = "http://strawberry.dothome.co.kr/get_search.php";
    private Map<String, String> map;

    public SearchRequest(String poster_title, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);


        map = new HashMap<>();
        map.put("poster_title",poster_title);


    }

    @Nullable
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
//title_array