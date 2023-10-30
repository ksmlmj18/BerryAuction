// 아이디 중복 체크
package com.example.main_;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest3 extends StringRequest {

    //서버 URL설정 (PHP파일 연동)
    final static private String URL = "http://strawberry.dothome.co.kr/idoverlap.php";
    private Map<String, String> map;



    public RegisterRequest3(String user_id, Response.Listener<String> responseListener) {
        super(Method.POST, URL, responseListener, null);

        map = new HashMap<>();
        map.put("user_id", user_id);
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}