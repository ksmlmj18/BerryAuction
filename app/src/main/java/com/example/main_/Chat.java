package com.example.main_;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;


public class Chat extends AppCompatActivity {

    public static Context CONTEXT;

    private Button btn_move;
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


    Intent intent2;
    ListView listview ;
    ListViewAdapter3 adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        CONTEXT = this;


        // 만약 특정 값에 변화가 생겼다면..
        // 한번만 새로고침해라..
        System.out.println("hhhhhhhhhhhhhhhhhh");
        System.out.println(Login.change_check);



        // < 대화 목록 정보 리스폰 >
        Response.Listener<String> response_chat = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // 리스폰 값 확인
                System.out.println("=================================1 채팅방 목록");
                System.out.println(response);
                System.out.println("=================================1 채팅방 목록");

                try{
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");

                    // DB 연동 성공 후
                    if(success){
//                        Toast.makeText(getApplicationContext(), "채팅 연동에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                        chatList = new ArrayList<>();


                        // 1. [ "채팅방 정보"의 배열을 chat_infoList변수에 담는다. ]
                        chat_infoList = jsonObject.getJSONArray("chat_list");
                        chat_infoList_length = chat_infoList.length();  // 총 채팅 갯수

                        System.out.println("=================================채팅 갯수");
                        System.out.println(chat_infoList_length);
                        System.out.println("=================================채팅 갯수");



                        // ---------- adapter ----------
                        // Adapter 생성
                        adapter = new ListViewAdapter3() ;
                        // 리스트뷰 참조 및 Adapter달기
                        listview = (ListView) findViewById(R.id.listView1);
                        listview.setAdapter(adapter);



                        // 1. [ 채팅방 목록의 데이터 정제 과정 ]
                        // 총 채팅방 갯수만큼 반복
                        for(int i = 0; i < chat_infoList_length; i++){
                            // 다른 맵 객체 생성
                            chatMap = new HashMap<>();

                            // 채팅방 정보(chat_id, sender, getter, chat_content, chat_day, chat_time)
                            JSONObject json_chatInfo = chat_infoList.getJSONObject(i);
                            chat_id = json_chatInfo.getString("chatid");
                            chat_sender = Login.userNick;
                            // chat_sender = json_chatInfo.getString("sender");
                            chat_getter = json_chatInfo.getString("person");
                            chat_day = json_chatInfo.getString("day");
                            chat_time = json_chatInfo.getString("time");
                            chat_content = json_chatInfo.getString("content");

                            chatMap.put("chat_id", chat_id);
                            chatMap.put("getter", chat_getter);
                            chatMap.put("chat_day", chat_day);
                            chatMap.put("chat_time", chat_time);
                            chatMap.put("chat_content", chat_content);
                            chatMap.put("sender", chat_sender);

                            chatList.add(chatMap);  // 만든 map을 ArrayList에 넣어준다.
                        }




                        // [ 채팅방 목록 최신순으로 변경하는 과정 ]
                        // chat_day와 chat_time값을 꺼내서 String 값으로 합치고,
                        // 각각의 chat_list 의 인덱스 안의 String 값들을 비교 후,
                        // 크다면 인덱스를 바꿔준다.
                        // 날짜 형식에 맞게 바꿔준다.
                        String str1;
                        ArrayList<Date> dateList = new ArrayList<>();
                        for(int i=0; i<chat_infoList_length; i++){
                            str1 = chatList.get(i).get("chat_day") + chatList.get(i).get("chat_time");
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddh:mm:ss");
                            try {
                                dateList.add(sdf.parse(str1));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                        // 비교
                        for(int i=0; i<chat_infoList_length-1; i++){
                            // 만약 i가 i+1보다 과거에 일이면..
                            if(dateList.get(i).before((dateList.get(i+1)))){
                                Collections.swap(chatList, i, i+1);
                                Collections.swap(dateList, i, i+1);
                            }
                        }



                        // [ 채팅방 목록 아이템 추가 반복문 ]
                        for(int i=0; i<chat_infoList_length; i++){
                            System.out.println("=================================zzzzz");
                            System.out.println(chatList.get(i));
                            System.out.println("=================================zzzzz");
                            // 아이템 추가.
                            adapter.addItem(chatList.get(i).get("chat_id"), chat_sender, chatList.get(i).get("getter")
                                    , chatList.get(i).get("chat_content"), chatList.get(i).get("chat_day"), chatList.get(i).get("chat_time"));

                            adapter.notifyDataSetChanged();
                        }



                        // [ 액티비티 이동 관련 ]
                        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                // 해당 아이템의 포지션 값 저장하기
                                String ch_id = ((ListViewItem)adapter.getItem(i)).getChat_id();
                                String ch_sender = ((ListViewItem)adapter.getItem(i)).getChat_sender();
                                String ch_getter = ((ListViewItem)adapter.getItem(i)).getChat_getter();
//                                String ch_content = ((ListViewItem)adapter.getItem(i)).getChat_content();

                                // 임시로 생각해본 각 채팅방의 sender와 getter의 채팅 내역
//                                ArrayList<String> chat_contentList = new ArrayList<>();

                                // 이동할 액티비티 지정
                                intent2 = new Intent(Chat.this, SingleChat.class);

                                // 액티비티 이동과 함께 값 넘겨주기
                                intent2.putExtra("chat_id", ch_id);
                                System.out.println("===============나 넘겨주기 전 ch_id 값이야");
                                System.out.println(ch_id);
                                System.out.println("===============나 넘겨주기 전 ch_id 값이야");
                                intent2.putExtra("ch_sender", ch_sender);
                                System.out.println("===============나 넘겨주기 전 ch_sender 값이야");
                                System.out.println(ch_sender);
                                System.out.println("===============나 넘겨주기 전 ch_sender 값이야");

                                intent2.putExtra("ch_getter", ch_getter);
                                System.out.println("===============나 넘겨주기 전 ch_getter 값이야");
                                System.out.println(ch_getter);
                                System.out.println("===============나 넘겨주기 전 ch_getter 값이야");
//                                intent2.putExtra("ch_content", ch_content);
//                                intent2.putse("ch_contentList", chat_contentList);

                                startActivity(intent2); // 액티비티 이동
                            }
                        });




                    }else{
                        Toast.makeText(getApplicationContext(), "서버 연동에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };




        // < 대화 목록 정보 리스폰 >
        Response.Listener<String> response_chatContent = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // 리스폰 값 확인
                System.out.println("=================================2 채팅 내역 목록");
                System.out.println(response);
                System.out.println("=================================2 채팅 내역 목록");

                try{
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");

                    // DB 연동 성공 후
                    if(success){
//                        Toast.makeText(getApplicationContext(), "채팅2 연동에 성공하였습니다.", Toast.LENGTH_SHORT).show();



                        // 2. [ "채팅방 안에 대화 내역" 관련 정보를 담는다. ]
                        chat_contentInfoList = jsonObject.getJSONArray("chat_contentList");
                        chat_contentInfoList_length = chat_contentInfoList.length();  // 총 채팅방 내역 갯수

                        System.out.println("=================================채팅 내역 갯수");
                        System.out.println(chat_contentInfoList_length);
                        System.out.println("=================================채팅 내역 갯수");





                        // 2. [ 채팅방 내역의 데이터 정제 과정 ]
                        // 총 본인 채팅 내역 갯수만큼 반복
                        for(int i=0; i<chat_contentInfoList_length; i++){
                            // 다른 맵 객체 생성
                            chat_contentMap = new HashMap<>();

                            // 채팅방 내역 정보(chat_id, sender, getter, chat_content, chat_day, chat_time)
                            JSONObject json_chContentInfo = chat_contentInfoList.getJSONObject(i);
                            ch_c_id = json_chContentInfo.getString("chat_id");
                            ch_c_sender = json_chContentInfo.getString("sender");
                            ch_c_getter = json_chContentInfo.getString("getter");
                            ch_c_content = json_chContentInfo.getString("chat_content");
                            ch_c_day = json_chContentInfo.getString("chat_day");
                            ch_c_time = json_chContentInfo.getString("chat_time");


                            chat_contentMap.put("chat_id", ch_c_id);
                            chat_contentMap.put("sender", ch_c_sender);
                            chat_contentMap.put("getter", ch_c_getter);
                            chat_contentMap.put("chat_content", ch_c_content);
                            chat_contentMap.put("chat_day", ch_c_day);
                            chat_contentMap.put("chat_time", ch_c_time);

                            chat_contentList.add(chat_contentMap);


                        }


//                            intent2 = new Intent(Chat.this, SingleChat.class);
//                            overridePendingTransition(0, 0); //인텐트 애니메이션 없애기
//                            startActivity(intent2); // 액티비티 이동
//                            overridePendingTransition(0, 0); //인텐트 애니메이션 없애기


//                        if(Login.change_check == true){
//                            try {
//                                Thread.sleep(1000);
//                                finish();//인텐트 종료
//                                overridePendingTransition(0, 0);//인텐트 효과 없애기
//                                Intent intent2 = getIntent(); //인텐트
//                                startActivity(intent2); //액티비티 열기
//                                overridePendingTransition(0, 0);//인텐트 효과 없애기
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "서버 연동에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };



        // < 채팅 목록 관련 요청 >
        ChatRequest chatRequest = new ChatRequest(user_id, response_chat);
        RequestQueue queue = Volley.newRequestQueue(Chat.this);
        queue.add(chatRequest);


        // < 채팅방 내역 관련 요청 >
        SingleChatRequest singleChatRequest = new SingleChatRequest(user_id, response_chatContent);
        RequestQueue queue2 = Volley.newRequestQueue(Chat.this);
        queue2.add(singleChatRequest);




//        ListView listview ;
//        ListViewAdapter3 adapter;
//        // Adapter 생성
//        adapter = new ListViewAdapter3() ;
//        // 리스트뷰 참조 및 Adapter달기
//        listview = (ListView) findViewById(R.id.listView1);
//        listview.setAdapter(adapter);
//
////        System.out.println("===============================");
////        System.out.println(chatList.get(0).get("chat_id"));
////        System.out.println("===============================");
//
//
//        for(int i=0; i<10; i++) {
//            // 만약에, (보내는 이 닉네임 == 로그인한 user의 닉네임) 이라면, item에 추가해라.
////            if(chatList.get(i).get("sender") == Login.userNick) {
////                // 아이템 추가.
////                adapter.addItem(chatList.get(i).get("chat_id"), chatList.get(i).get("chat_sender"), chatList.get(i).get("chat_getter")
////                        , chatList.get(i).get("chat_content"), chatList.get(i).get("chat_day"), chatList.get(i).get("chat_time"));
////            }
//            adapter.addItem("1", "쵸빗요잇","천사", "오늘 거래는 없던 걸로 합시다", "2022-08-21" ,"16:28");
//            adapter.notifyDataSetChanged();
//        }
//
//
//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                // 해당 아이템의 포지션 값 저장하기
//                String ch_id = ((ListViewItem)adapter.getItem(i)).getChat_id();
//                String ch_sender = ((ListViewItem)adapter.getItem(i)).getChat_sender();
//                String ch_getter = ((ListViewItem)adapter.getItem(i)).getChat_getter();
//                String ch_content = ((ListViewItem)adapter.getItem(i)).getChat_content();
//
//
//                // 이동할 액티비티 지정
//                Intent intent2 = new Intent(Chat.this, SingleChat.class);
//
//                // 액티비티 이동과 함께 값 넘겨주기
//                intent2.putExtra("ch_sender", ch_sender);
//                intent2.putExtra("ch_getter", ch_getter);
//                intent2.putExtra("ch_content", ch_content);
//
//                startActivity(intent2); // 액티비티 이동
//            }
//        });






        // ---------------------- 뒤돌아가기 및 하단바 ----------------------
        btn_move = findViewById(R.id.bt_chat_back);
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
                    Intent intent2 = new Intent(Chat.this, Main.class);
                    startActivity(intent2); // 액티비티 이동
                }else if(Login.userL == 1) {
                    Intent intent2 = new Intent(Chat.this, MainLogin.class);
                    startActivity(intent2); // 액티비티 이동
                }
            }
        });

        btn_move = findViewById(R.id.bt_auction);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Chat.this, Auction.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_scrap);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Chat.this, MyScrap.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_chat);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Chat.this, Chat.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_myPage);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Chat.this, MyPage.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

    }
}