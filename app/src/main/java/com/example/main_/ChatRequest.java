package com.example.main_;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ChatRequest extends StringRequest {

    //서버 URL설정 (PHP파일 연동)
    final static private String URL = "http://strawberry.dothome.co.kr/allchattingget.php";
    private Map<String, String> map;

    public ChatRequest(String user_id, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("user_id", user_id); // 유저 아이디
//        map.put("current_date", current_date); // 현재 날짜
//        map.put("current_time", current_time); // 현재 시간

//        // 채팅방 ID
//        map.put("user_id", chat_id);
//        // 채팅 상대방 아이디
//        map.put("getter", getter_id);
//        // 채팅 내용
//        map.put("chat_content", chat_content);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}
