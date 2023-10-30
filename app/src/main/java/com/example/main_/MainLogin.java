package com.example.main_;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainLogin extends AppCompatActivity {

    private Button btn_move;
    private AlertDialog dialog;
    private String dumy = "하이";
    static public JSONArray poster_infoList;
    static public JSONArray reply_infoList;
    static public JSONArray rating_array;
    static public JSONArray info_array;
    static public int jsonSize; // 게시물 개수
    static public int jsonSize3; //댓글 개수
    static public int jsonSize4; // 유저 정보 수
    static public JSONArray user_infoList;
    private EditText et_search;
    public static JSONArray search_infolist; // 검색 게시글 정보
    public static int jsonSize5; // 검색 정보 게시글 수
    String user_search;
    private EditText et_main_search;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        btn_move = findViewById(R.id.bt_mainL_post);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {// 클릭 시 메인에서 게시물 등록창으로 이동
                Intent intent = new Intent(MainLogin.this, Post.class);
                startActivity(intent); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_mainL_chat);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {// 클릭 시 메인에서 채팅방으로 이동
                Intent intent = new Intent(MainLogin.this, Chat.class);
                startActivity(intent); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_mainL_logout);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login.userL = 0;
                Toast.makeText(MainLogin.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainLogin.this, Main.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);

//                Login.timer.cancel();
            }
        });


        btn_move = findViewById(R.id.bt_mainL_mypage);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {// 클릭 시 메인에서 마이페이지로 이동
                Intent intent2 = new Intent(MainLogin.this, MyPage.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_mainL_auction);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {// 클릭 시 메인에서 경매창으로 이동


                Intent intent2 = new Intent(MainLogin.this, Auction.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        et_main_search = (EditText)findViewById(R.id.et_mainL_search);
        et_main_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                user_search = et_main_search.getText().toString();
                String[] check = user_search.split("");
                int cnt = 0;
                for(int i = 0; i < check.length; i++) {
                    cnt++;
                }
                if(cnt < 2)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainLogin.this);
                    dialog=builder.setMessage("2글자 이상 검색해주세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return false;
                }



                if(user_search.isEmpty()) {
                    Toast.makeText(MainLogin.this, "빈칸입니다.", Toast.LENGTH_SHORT).show();
                    v.clearFocus();
                    v.setFocusable(false);
                    v.setFocusableInTouchMode(true);
                    v.setFocusable(true);

                    return true;
                }
                Main.search_num = 2;

                switch (actionId) {
                    default:
                        Response.Listener<String> responseListener5 = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    System.out.println("검색결과~:"+response);
                                    JSONObject jsonObject = new JSONObject(response);
                                    search_infolist = jsonObject.getJSONArray("title_array");
                                    jsonSize5 = search_infolist.length();


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        SearchRequest searchRequest = new SearchRequest(user_search,responseListener5);
                        RequestQueue queue5 = Volley.newRequestQueue(MainLogin.this);
                        queue5.add(searchRequest);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent2 = new Intent(MainLogin.this, Search.class);
                                startActivity(intent2); // 액티비티 이동
                            }
                        }, 200);
                }

                v.clearFocus();
                v.setFocusable(false);
                v.setText("");
                v.setFocusableInTouchMode(true);
                v.setFocusable(true);

                return true;


            }
        });

        // poster
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    System.out.println(response);
                    poster_infoList = jsonObject.getJSONArray("poster_infoList");
                    jsonSize = poster_infoList.length();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        MainRequest mainRequest = new MainRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainLogin.this);
        queue.add(mainRequest);




        // reply
        Response.Listener<String> responseListener2 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
//                            System.out.println(response);

                    JSONObject jsonObject = new JSONObject(response);
                    reply_infoList = jsonObject.getJSONArray("reply_infoList");
                    jsonSize3 = reply_infoList.length();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        MainRequest2 mainRequest2 = new MainRequest2(responseListener2);
        RequestQueue queue2 = Volley.newRequestQueue(MainLogin.this);
        queue2.add(mainRequest2);

        //모든 user_info
        Response.Listener<String> responseListener3 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("------------------------kkkkkkkkkkkkkkkkkkkkkkkkk");
                    System.out.println(response);

                    JSONObject jsonObject = new JSONObject(response);
                    rating_array = jsonObject.getJSONArray("rating_array");
                    jsonSize4 = rating_array.length();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        InfoRequest infoRequest = new InfoRequest(responseListener3);
        RequestQueue queue3 = Volley.newRequestQueue(MainLogin.this);
        queue3.add(infoRequest);

        // 현재 로그인한 유저의 정보
        Response.Listener<String> responseListener4 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println(response);

                    JSONObject jsonObject = new JSONObject(response);
                    info_array = jsonObject.getJSONArray("info_array");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        UserInfoRequest userInfoRequest = new UserInfoRequest(Login.userNick,responseListener4);
        RequestQueue queue4 = Volley.newRequestQueue(MainLogin.this);
        queue4.add(userInfoRequest);



//        // 현재 로그인한 유저의 정보
//        Response.Listener<String> responseListener4 = new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
////                            System.out.println(response);
//
//                    JSONObject jsonObject = new JSONObject(response);
//                    user_infoList = jsonObject.getJSONArray("user_infoList");
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//
//        CommentRequest commentRequest = new CommentRequest(responseListener);
//        RequestQueue queue4 = Volley.newRequestQueue(MainLogin.this);
//        queue4.add(commentRequest);
    }
}