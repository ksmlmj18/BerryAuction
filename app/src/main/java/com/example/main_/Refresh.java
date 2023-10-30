package com.example.main_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Refresh extends AppCompatActivity {

//    static public JSONArray poster_infoList; // 실제 게시글 정보
//    static public JSONArray reply_infoList;
//    static public int jsonSize; // 게시글 수
//    static public int jsonSize3; // 댓글 수
//    public static int check = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);

        // poster
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("------------------------Refresh");
                    System.out.println(response);
                    JSONObject jsonObject = new JSONObject(response);
                    Main.poster_infoList = jsonObject.getJSONArray("poster_infoList");
                    Main.jsonSize = Main.poster_infoList.length();

                    MainLogin.poster_infoList = jsonObject.getJSONArray("poster_infoList");
                    MainLogin.jsonSize = MainLogin.poster_infoList.length();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        MainRequest mainRequest = new MainRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(Refresh.this);
        queue.add(mainRequest);



        // reply
        Response.Listener<String> responseListener2 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("------------------------Refresh");
                    System.out.println(response);

                    JSONObject jsonObject = new JSONObject(response);
                    Main.reply_infoList = jsonObject.getJSONArray("reply_infoList");
                    Main.jsonSize3 = Main.reply_infoList.length();

                    MainLogin.reply_infoList = jsonObject.getJSONArray("reply_infoList");
                    MainLogin.jsonSize3 = MainLogin.reply_infoList.length();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        MainRequest2 mainRequest2 = new MainRequest2(responseListener2);
        RequestQueue queue2 = Volley.newRequestQueue(Refresh.this);
        queue2.add(mainRequest2);

//        try {
//            Thread.sleep(3000);
        System.out.println("내가 왔다!!!!!!!!!!!!!!!!!!!!!!!!!");
//            overridePendingTransition(0, 0);//인텐트 효과 없애기
        onBackPressed();
//            e.printStackTrace();
    }
}