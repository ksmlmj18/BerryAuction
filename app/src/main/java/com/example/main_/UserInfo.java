package com.example.main_;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInfo extends AppCompatActivity {

    private Button btn_move;
    private AlertDialog dialog;

    // 챗 변수
    private List<String> data;

    //    SingleChat singleChat = new SingleChat();
    //    private Map<String, String> chat_map = new HashMap<String, String>();
    private String user_id = Login.userL2;  // 로그인 한 사람의 id
    private String chat_id;                 // 채팅방 id
    private String chat_sender;             // 채팅 메시지 보낸 사람(nickName)
    private String chat_getter;             // 채팅 메시지 받는 사람(nickName)
    private String chat_content;            // 채팅 마지막 내용
    private String chat_day;                // 채팅 마지막 날짜
    private String chat_time;               // 채팅 마지막 시간
    //----------------------------------------------------------------------------------
    // 채팅방 내역 정보들
    private String ch_c_id;
    private String ch_c_sender;
    private String ch_c_getter;
    private String ch_c_content;
    private String ch_c_day;
    private String ch_c_time;
    private String dumy_sender;

    private int chat_infoList_length;       // chat_infoList 배열 길이(총 채팅방 갯수)
    private int chat_contentInfoList_length;    // chat_contentList 배열 길이(총 채팅 내역 갯수)
    private List<Map<String, String>> chatList;
    private Map<String, String> chatMap;
    public static List<Map<String, String>> chat_contentList = new ArrayList<>();
    private Map<String, String> chat_contentMap;


    // 채팅방 정보의 배열을 담기 위한 변수
    static public JSONArray chat_infoList;  // 채팅방 정보(chat_id, sender, getter, chat_content, chat_day, chat_time)

    // 채팅방 내역의 정보의 배열을 담기 위한 변수
    static public JSONArray chat_contentInfoList;  // 채팅방 내역 정보(chat_id, sender, getter, chat_content, chat_day, chat_time)


    // 현재 시간 구하기
    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");

    String day = String.valueOf(formatter);
    String time = String.valueOf(formatter2);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);


        //id 속성이 text id값의 뷰의 주소 값을 얻어온다.
        TextView user_name = (TextView) findViewById(R.id.tv_userInfo_name);
        TextView user_point = (TextView) findViewById(R.id.tv_userInfo_starNum);
        TextView user_num = (TextView) findViewById(R.id.tv_userInfo_transactionNum);
        TextView user_detail = (TextView) findViewById(R.id.tv_userInfo_careertxt);

        // 닉네임 변수로 초기화
        String nickName = String.valueOf(user_name);

        // 보내온 Intent를 얻는다.
        Intent intent = getIntent();

        //새로운 문자열을 설정한다.
        user_name.setText(intent.getStringExtra("name"));
        user_point.setText(intent.getStringExtra("point"));
        user_num.setText(intent.getStringExtra("num"));
        user_detail.setText(intent.getStringExtra("detail"));

        btn_move = findViewById(R.id.bt_userInfo_back);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_move = findViewById(R.id.bt_home);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Login.userL == 0) {
                    Intent intent2 = new Intent(UserInfo.this, Main.class);
                    startActivity(intent2); // 액티비티 이동
                }else if(Login.userL == 1) {
                    Intent intent2 = new Intent(UserInfo.this, MainLogin.class);
                    startActivity(intent2); // 액티비티 이동
                }
            }
        });



        btn_move = findViewById(R.id.bt_scrap);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Login.userL == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserInfo.this);
                    dialog=builder.setMessage("로그인 후 사용할 수 있습니다..").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }else if(Login.userL == 1) {
                    Intent intent2 = new Intent(UserInfo.this, MyScrap.class);
                    startActivity(intent2); // 액티비티 이동
                }
            }
        });

        btn_move = findViewById(R.id.bt_chat);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Login.userL == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserInfo.this);
                    dialog=builder.setMessage("로그인 후 사용할 수 있습니다..").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }else if(Login.userL == 1) {
                    Intent intent2 = new Intent(UserInfo.this, Chat.class);
                    startActivity(intent2); // 액티비티 이동
                }
            }
        });

        btn_move = findViewById(R.id.bt_myPage);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Login.userL == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserInfo.this);
                    dialog=builder.setMessage("로그인 후 사용할 수 있습니다.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }else if(Login.userL == 1) {
                    Intent intent2 = new Intent(UserInfo.this, MyPage.class);
                    startActivity(intent2); // 액티비티 이동
                }
            }
        });

        btn_move = findViewById(R.id.bt_auction);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Login.userL == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserInfo.this);
                    dialog=builder.setMessage("로그인 후 사용할 수 있습니다.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }else if(Login.userL == 1) {
                    Intent intent2 = new Intent(UserInfo.this, Auction.class);
                    startActivity(intent2); // 액티비티 이동
                }
            }
        });

        // 1:1 채팅
//        Button btn = findViewById(R.id.bt_userInfo_chat);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // --------------------채팅방이 이미 개설된 상태의 경우 예외처리 이 곳에---------------------


                // 1:1 채팅 재확인 다이얼로그
                AlertDialog.Builder builder = new AlertDialog.Builder(UserInfo.this);
                builder.setTitle("확인"); //다이얼로그 제목
                builder.setMessage("채팅하시겠습니까?"); //다이얼로그 내용

                // 다이얼로그 (예)
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Response.Listener<String> responseListener_chat = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");




                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        ChatGetRequest chatGetRequest = new ChatGetRequest(Login.userNick, day, time, nickName,
                                Login.userNick + "님과 " + nickName + "님의 채팅방이 개설되었습니다.", responseListener_chat);
                        RequestQueue queue = Volley.newRequestQueue(UserInfo.this);
                        queue.add(chatGetRequest);

                        // 이동할 액티비티 지정
                        Intent intent2 = new Intent(UserInfo.this, Chat.class);

                        startActivity(intent2); // 액티비티 이동


                    }
                });


                // 다이얼로그 (아니오)
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });
                builder.create().show();
            }

        });

    }
}