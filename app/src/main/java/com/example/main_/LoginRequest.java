package com.example.main_;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    //서버 URL설정 (PHP파일 연동)
    final static private String URL = "http://strawberry.dothome.co.kr/login.php";
    private Map<String, String> map;

    public LoginRequest(String user_id, String user_pw, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("user_id", user_id); // 유저 아이디
        map.put("user_pw", user_pw); // 유저 비밀번호
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
