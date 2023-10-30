package com.example.main_;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CommentRequest extends StringRequest {

    final static private String URL = "http://strawberry.dothome.co.kr/replyget.php";
    private Map<String, String> map;
    private Map<String, String> map2;

    public CommentRequest(String poster_id, String reply_content,String user_nickName, String reply_year, String reply_month, String reply_day, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("poster_id", poster_id);
        map.put("reply_content", reply_content);
        map.put("user_nickName", user_nickName);
        map.put("reply_year", reply_year);
        map.put("reply_month", reply_month);
        map.put("reply_day", reply_day);
    }





    @Nullable
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}