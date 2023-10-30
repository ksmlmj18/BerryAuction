package com.example.main_;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
 // 영상 카테고리
public class CategoryRequest extends StringRequest {

    final static private String URL = "http://strawberry.dothome.co.kr/get_poster2_video.php";

    private Map<String, String> map;

    public CategoryRequest(String poster_parentCategory, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);


        map = new HashMap<>();
        map.put("poster_parentCategory", poster_parentCategory);


    }

    @Nullable
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
