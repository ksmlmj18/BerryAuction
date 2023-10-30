package com.example.main_;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main extends AppCompatActivity {

    private Button btn_move;
    private AlertDialog dialog;
    private String dumy = "하이";
    private EditText et_main_search;
    public static int search_num = 1;

    static public JSONArray poster_infoList; // 실제 게시글 정보
    static public JSONArray reply_infoList; // 실제 댓글 정보
    static public JSONArray rating_array;
    static public int jsonSize; // 게시글 수
    static public int jsonSize3; // 댓글 수
    static public int jsonSize4; // 유저 정보 수
    public static JSONArray search_infolist; // 검색 게시글 정보
    public static int jsonSize5; // 검색 정보 게시글 수
    String user_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_move = findViewById(R.id.bt_main_post);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Main.this);
                dialog=builder.setMessage("로그인 후 입장 가능합니다.").setPositiveButton("확인", null).create();
                dialog.show();
                return;
            }
        });

        btn_move = findViewById(R.id.bt_main_chat);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Main.this);
                dialog=builder.setMessage("로그인 후 입장 가능합니다.").setPositiveButton("확인", null).create();
                dialog.show();
                return;
            }
        });

        btn_move = findViewById(R.id.bt_main_login);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {// 클릭 시 메인에서 로그인창으로 이동
                Intent intent2 = new Intent(Main.this, Login.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_main_regist);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {// 클릭 시 메인에서 회원가입창으로 이동
                Intent intent2 = new Intent(Main.this, Register.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_main_mypage);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Main.this);
                dialog=builder.setMessage("로그인 후 입장 가능합니다.").setPositiveButton("확인", null).create();
                dialog.show();
                return;
            }
        });

        btn_move = findViewById(R.id.bt_main_auction);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 클릭 시 메인에서 경매장으로 이동
                Intent intent2 = new Intent(Main.this, Auction.class);
                startActivity(intent2); // 액티비티 이동

            }
        });

        et_main_search = (EditText)findViewById(R.id.et_main_search);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(Main.this);
                    dialog=builder.setMessage("2글자 이상 검색해주세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return false;
                }


                if(user_search.isEmpty()) {
                    Toast.makeText(Main.this, "빈칸입니다.", Toast.LENGTH_SHORT).show();
                    v.clearFocus();
                    v.setFocusable(false);
                    v.setFocusableInTouchMode(true);
                    v.setFocusable(true);

                    return true;
                }

                Main.search_num = 1;

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
                        RequestQueue queue5 = Volley.newRequestQueue(Main.this);
                        queue5.add(searchRequest);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent2 = new Intent(Main.this, Search.class);
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

//        Response.Listener<String> responseListener = new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//
//                    JSONObject jsonObject = new JSONObject(response);
//                    category_infoList = jsonObject.getJSONArray("poster_infoList");
//                    jsonSize = category_infoList.length();
//
//                    JSONObject jsonObject2 = category_infoList.getJSONObject(0);
//
//                    System.out.println(category_infoList.get(0));
//                    String c = jsonObject2.getString("startTime");
//                    System.out.println(c);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//        CategoryRequest categoryRequest = new CategoryRequest("영상",responseListener);
//        RequestQueue queue = Volley.newRequestQueue(Auction.this);
//        queue.add(categoryRequest);


        // poster
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
//                    System.out.println("------------------------gggggggggggggggggggggggggggg");
//                    System.out.println(response);
                    JSONObject jsonObject = new JSONObject(response);
                    poster_infoList = jsonObject.getJSONArray("poster_infoList");
                    jsonSize = poster_infoList.length();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        MainRequest mainRequest = new MainRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(Main.this);
        queue.add(mainRequest);



        // reply
        Response.Listener<String> responseListener2 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("------------------------kkkkkkkkkkkkkkkkkkkkkkkkk");
                    System.out.println(response);

                    JSONObject jsonObject = new JSONObject(response);
                    reply_infoList = jsonObject.getJSONArray("reply_infoList");
                    jsonSize3 = reply_infoList.length();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        MainRequest2 mainRequest2 = new MainRequest2(responseListener2);
        RequestQueue queue2 = Volley.newRequestQueue(Main.this);
        queue2.add(mainRequest2);


        //user_info
        Response.Listener<String> responseListener3 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("------------------------kkkkkkkkkkkkkkkkkkkkkkkkk2");
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
        RequestQueue queue3 = Volley.newRequestQueue(Main.this);
        queue3.add(infoRequest);


    }
}