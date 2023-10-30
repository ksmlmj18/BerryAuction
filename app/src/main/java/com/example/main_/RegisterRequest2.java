// 닉네임 중복 체크
package com.example.main_;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest2 extends StringRequest {

    //서버 URL설정 (PHP파일 연동)
    final static private String URL = "http://strawberry.dothome.co.kr/nicknameoverlap.php";
    private Map<String, String> map;



    public RegisterRequest2(String user_nickName, Response.Listener<String> responseListener) {
        super(Method.POST, URL, responseListener, null);

        map = new HashMap<>();
        map.put("user_nickName", user_nickName);
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}