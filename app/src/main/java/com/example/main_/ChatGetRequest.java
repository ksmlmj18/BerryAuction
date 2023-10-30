package com.example.main_;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ChatGetRequest extends StringRequest {

    //서버 URL설정 (PHP파일 연동)
    final static private String URL = "http://strawberry.dothome.co.kr/chatget.php";
    private Map<String, String> map;

    public ChatGetRequest(String sender, String chat_day, String chat_time, String getter, String chat_content, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("sender", sender); // 유저 닉네임
        map.put("chat_day", chat_day); // 현재 날짜
        map.put("chat_time", chat_time); // 현재 시간
        map.put("getter", getter); // 채팅 상대방 아이디
        map.put("chat_content", chat_content); // 채팅 내용

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}