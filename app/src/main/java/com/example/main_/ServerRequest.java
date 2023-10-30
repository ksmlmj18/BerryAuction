package com.example.main_;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ServerRequest extends StringRequest {

    //서버 URL설정 (PHP파일 연동)
    final static private String URL = "http://strawberry.dothome.co.kr/server.php";
    private Map<String, String> map;

    public ServerRequest(String user_id, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("user_id", user_id); // 유저 아이디
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}