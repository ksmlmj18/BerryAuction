package com.example.main_;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ServerChatRequest extends StringRequest {

    //서버 URL설정 (PHP파일 연동)
    final static private String URL = "http://strawberry.dothome.co.kr/chatget.php";
    private Map<String, String> map;

    public ServerChatRequest(String sender, String getter, String chat_content, String chat_day
            , String chat_time, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        // [ 보낼 데이터 ]
        // - sender_nick : 보낸 사람 => sender 키 값으로 보내줘야함
        // - getter_nick : 받는 사람 => getter 키 값으로 보내줘야함
        // - inputStr : 메시지 입력 값 => chat_content 키 값으로 보내줘야함
        // - current_date : 현재 날짜 => chat_day 키 값으로 보내줘야함
        // - current_time : 현재 시간 => chat_time 키 값으로 보내줘야함

        map = new HashMap<>();
        map.put("sender", sender);
        map.put("getter", getter);
        map.put("chat_content", chat_content);
        map.put("chat_day", chat_day);
        map.put("chat_time", chat_time);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}
