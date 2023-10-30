package com.example.main_;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UserInfoEditRequest extends StringRequest {

    final static private String URL = "http://strawberry.dothome.co.kr/get_info2.php";
    private Map<String, String> map;

    public UserInfoEditRequest(String user_nickName,String user_career,String user_phoneNum,String user_email, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("user_nickName",user_nickName);
        map.put("user_career",user_career);
        map.put("user_phoneNum",user_phoneNum);
        map.put("user_email",user_email);

    }

    @Nullable
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}
