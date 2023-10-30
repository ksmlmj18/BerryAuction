package com.example.main_;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingleChat extends AppCompatActivity {
    private Button btn_move;
    private List<String> data;
    private int chat_length;
    private int check = 0;
    private String inputStr;


    // 채팅방

    // 내용 저장
    private Map<String, String> save_chatMap = new HashMap<>();
    private List<Map<String, String>> save_chatList = new ArrayList<>();

    // 방 마다의 채팅 정보
    public static Map<Integer, List<Map<String, String>>> chatRoom_content = new HashMap<>();

    // 새로 들어온 정보
    public static Map<Integer, List<Map<String, String>>> chatRoom_new_content = new HashMap<>();

    public static Map<String, Boolean> check_getChat_push = new HashMap<>();

    private ListView listview ;
    private ListViewAdapter5 adapter5;

    public static Boolean intentCheck = false;

    private String check_getter_id;
    public static String check_nickName;
    public static boolean check_value;


    //뷰의 주소값을 담을 참조 변수
    TextView text1;

    //    Chat chat = new Chat();
//    List<Map<String, String>> all_chatList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_chat);

        // [ 누군가가 메시지를 보낼 때마다 새로고침을 해준다. ]
        // 만약 특정 값에 변화가 생겼다면..
        // 한번만 새로고침해라..
        System.out.println("hhhhhhhhhhhhhhhhhh");
        System.out.println(Login.change_check);
        if(Login.change_check == true){
            try {
                Thread.sleep(1000);
                finish();//인텐트 종료
                overridePendingTransition(0, 0);//인텐트 효과 없애기
                Intent intent2 = getIntent(); //인텐트
                startActivity(intent2); //액티비티 열기
                overridePendingTransition(0, 0);//인텐트 효과 없애기
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        // --------------------------- 각 자리에 item 값을 넣기 위한 과정 ---------------------------
//        ListView listview ;
//        ListViewAdapter5 adapter5;
        // 초기화
        ListViewAdapter5.items.clear();

        // Adapter 생성
        adapter5 = new ListViewAdapter5();

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listView_single_chat);
        listview.setAdapter(adapter5);


        //id 속성이 text id값의 뷰의 주소 값을 얻어온다.
        TextView chat_name = (TextView)findViewById(R.id.tv_Single_chat_name);

        // 보내온 Intent를 얻는다.
        Intent intent = getIntent();

        //새로운 문자열을 설정한다.
        chat_name.setText(intent.getStringExtra("ch_getter"));
        String sender_nick = intent.getStringExtra("ch_sender");
        String getter_nick = intent.getStringExtra("ch_getter");
        String ch_id = intent.getStringExtra("chat_id");

        check_getter_id = getter_nick;

        System.out.println("-----------나 sender_nick");
        System.out.println(sender_nick);
        System.out.println("-----------나 sender_nick");


        System.out.println("-----------나 가져온 list 인덱스 0의 내용이야");
        System.out.println(Chat.chat_contentList.get(0).get("chat_content"));
        System.out.println("-----------나 가져온 list 인덱스 0의 내용이야");




        chat_length = 0;    // 그 해당 방의 채팅 갯수
        for(int i=0; i<Chat.chat_contentList.size(); i++){
            if(Chat.chat_contentList.get(i).get("chat_id").equals(ch_id)){
                chat_length++;
            }
        }

        // 채팅방에 들어왔을 때 갯수 체크
        System.out.println("=====================================");
        System.out.println("해당 방의 총 채팅 갯수: " + chat_length);
        System.out.println("=====================================");





        // [ 아이템 추가 ]
        System.out.println("--------------1");
        System.out.println(Chat.chat_contentList.get(0).get("chat_id"));
        System.out.println(ch_id);
        System.out.println("--------------1");

        System.out.println("--------------2");
        System.out.println(adapter5.getCount());
        System.out.println("--------------2");

        System.out.println(Chat.chat_contentList);



        for (int i = 0; i < Chat.chat_contentList.size(); i++) {
            // 만약에, 데이터값에서의 chat_id가 해당 방의 chat_id와 같다면..
            if (Chat.chat_contentList.get(i).get("chat_id").equals(ch_id)) {
                if (sender_nick.equals(Chat.chat_contentList.get(i).get("sender"))) {  // sender_nick == 로그인한 사람
                    System.out.println("첫 번째 if문 들어왔따.");
                    System.out.println(Chat.chat_contentList.get(i).get("chat_content"));
                    adapter5.addItem_sender(sender_nick, Chat.chat_contentList.get(i).get("chat_content"));
                    // 상대가 보내는거라면..
                } else if (getter_nick.equals(Chat.chat_contentList.get(i).get("sender"))) {
                    System.out.println("두 번째 if문 들어왔따.");
                    System.out.println(Chat.chat_contentList.get(i).get("chat_content"));
                    // getter 관련된 item 추가
                    adapter5.addItem_getter(getter_nick, Chat.chat_contentList.get(i).get("chat_content"));
                }
            }
        }

//        else {
//            System.out.println("---------- 처음 들어온게 아니에요 --------");
////            // 초기화
////            listview.setAdapter(adapter5);
//            ListViewAdapter5.items.clear();
////            // 리스트뷰 갱신
////            adapter5.notifyDataSetChanged();
//
//            for (int i = 0; i < Chat.chat_contentList.size(); i++) {
//                // 만약에, 데이터값에서의 chat_id가 해당 방의 chat_id와 같다면..
//                if (Chat.chat_contentList.get(i).get("chat_id").equals(ch_id)) {
//                    if (sender_nick.equals(Chat.chat_contentList.get(i).get("sender"))) {  // sender_nick == 로그인한 사람
//                        System.out.println("첫 번째 if문 들어왔따.");
//                        System.out.println(Chat.chat_contentList.get(i).get("chat_content"));
//                        adapter5.addItem_sender(sender_nick, Chat.chat_contentList.get(i).get("chat_content"));
//                        // 상대가 보내는거라면..
//                    } else if (getter_nick.equals(Chat.chat_contentList.get(i).get("sender"))) {
//                        System.out.println("두 번째 if문 들어왔따.");
//                        System.out.println(Chat.chat_contentList.get(i).get("chat_content"));
//                        // getter 관련된 item 추가
//                        adapter5.addItem_getter(getter_nick, Chat.chat_contentList.get(i).get("chat_content"));
//                    }
//                }
//            }
//
//        }
//
//        adapter5.notifyDataSetChanged();

//        // 리스트뷰 갱신
//        adapter5.notifyDataSetChanged();
//
//        try {
//            Thread.sleep(1000);
//            // 초기화
//            items.clear();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        System.out.println("--------------3");
        System.out.println(adapter5.getCount());
        System.out.println("--------------3");
//            chatRoom_content.put(Integer.valueOf(ch_id), save_chatList);
//        }










//            cnt++;
        // 대화방에 들어온 적이 있다면
//        else if(adapter5.getCount() > 0){
//
//            adapter5.notifyDataSetChanged();
////            adapter5 = new ListViewAdapter5();
////            adapter5.notifyDataSetChanged();
//
//
//
//            for(int i=0; i<Chat.chat_contentList.size(); i++){
////                System.out.println("i: " + i);
////                System.out.println("--------------1");
////                System.out.println(all_chatList.get(i).get("chat_id"));
////                System.out.println(ch_id);
////                System.out.println("--------------1");
//                // 만약에, 데이터값에서의 chat_id가 해당 방의 chat_id와 같다면..
//                if(Chat.chat_contentList.get(i).get("chat_id").equals(ch_id)){
//                    System.out.println("확인용");
//                    if(sender_nick.equals(Chat.chat_contentList.get(i).get("sender"))) {  // sender_nick == 로그인한 사람
//                        System.out.println("확인용1");
//                        adapter5.addItem_sender(sender_nick, Chat.chat_contentList.get(i).get("chat_content"));
//                        adapter5.notifyDataSetChanged();
//                        // 상대가 보내는거라면..
//                    }else if(getter_nick.equals(Chat.chat_contentList.get(i).get("sender"))){
//                        // getter 관련된 item 추가
//                        System.out.println("확인용2");
//                        adapter5.addItem_getter(getter_nick, Chat.chat_contentList.get(i).get("chat_content"));
//                        adapter5.notifyDataSetChanged();
//                    }
//                }
//
//            }
//        }
//        else if(chatRoom_content.get(Integer.valueOf(ch_id)) != null) {
//            System.out.println("나 대화방 들어온 적이 있어 그래서 여기로 왔어");
//            for (int key : chatRoom_content.keySet()) {
//                // 가져온 키가 들어온 채팅방의 아이디와 같다면..
//                if (key == Integer.valueOf(ch_id)) {
//                    chat_key = key;
//                }
//            }
//
//            System.out.println("내 키야1: " + chat_key);
//
////            int change_chatSize = Integer.parseInt(Login.max_countMap.get(Integer.toString(chat_key)));
//
//
////            if (chatRoom_content.get(chat_key).size() == change_chatSize) {
////                adapter5.notifyDataSetChanged();
//////            if(false){
////                // getter의 시점(추가 된 갯수만큼 반복한다.)
////                for (int i = 0; i < change_chatSize; i++) {
//////                for (int i = 0; i < chatRoom_content.get(chat_key).size(); i++) {
////                    System.out.println("내 키야2: " + chat_key);
////                    System.out.println("채팅방 채팅 갯수야: " + chatRoom_content.get(chat_key).size());
////
////                    // 저장된 값만 item에 추가해준다.
////                    if (chatRoom_content.get(chat_key).get(i).get(sender_nick) != null) {
////                        System.out.println("나 대화값이야1:" + chatRoom_content.get(chat_key).get(i).get(sender_nick));
////                        adapter5.addItem_sender(sender_nick, chatRoom_content.get(chat_key).get(i).get(sender_nick));
////
////                    } else if (chatRoom_content.get(chat_key).get(i).get(getter_nick) != null) {
////                        System.out.println("나 대화값이야2: " + chatRoom_content.get(chat_key).get(i).get(getter_nick));
////                        adapter5.addItem_getter(getter_nick, chatRoom_content.get(chat_key).get(i).get(getter_nick));
////                    }
////
////                }
////            }else
//                // setter의 시점
//            for (int i = 0; i < chatRoom_content.get(chat_key).size(); i++) {
//                System.out.println("내 키야2: " + chat_key);
//                System.out.println("채팅방 채팅 갯수야: " + chatRoom_content.get(chat_key).size());
//                // 저장된 값만 item에 추가해준다.
////                for(String type_human : chatRoom_content.get(chat_key).get(i).keySet()){
////                type_human.equals(sender_nick)
//                if (chatRoom_content.get(chat_key).get(i).get(sender_nick) != null) {
////                    System.out.println("내 type이야1: " + type_human);
//                    System.out.println("나 대화값이야1:" + chatRoom_content.get(chat_key).get(i).get(sender_nick));
//                    adapter5.addItem_sender(sender_nick, chatRoom_content.get(chat_key).get(i).get(sender_nick));
//                    save_chatMap.put(sender_nick, chatRoom_content.get(chat_key).get(i).get(sender_nick));
//                    save_chatList.add(save_chatMap);
//                    save_chatList.remove(0);
//
//                } else if (chatRoom_content.get(chat_key).get(i).get(getter_nick) != null) {
//                    System.out.println("나 대화값이야2: " + chatRoom_content.get(chat_key).get(i).get(getter_nick));
//                    adapter5.addItem_getter(getter_nick, chatRoom_content.get(chat_key).get(i).get(getter_nick));
//                    save_chatMap.put(getter_nick, chatRoom_content.get(chat_key).get(i).get(getter_nick));
//                    save_chatList.add(save_chatMap);
//                    save_chatList.remove(0);
//                }
////                chatRoom_content.put(Integer.valueOf(ch_id), save_chatList);
////                }
////                for (Map<String, String> senderOrGetter : chatRoom_content.get(chat_key).get(i)) {
//                //                if (chatRoom_content.get(chat_key).get(i).keySet("sender")) {  // sender_nick == 로그인한 사람
//                //                    adapter5.addItem_sender(sender_nick, all_chatList.get(i).get("chat_content"));
//                //                    save_chatList.add(all_chatList.get(i).get("chat_content"));
//                //
//                //                    // 상대가 보내는거라면..
//                //                } else if (getter_nick.equals(all_chatList.get(i).get("sender"))) {
//                //                    // getter 관련된 item 추가
//                //                    adapter5.addItem_getter(getter_nick, all_chatList.get(i).get("chat_content"));
//                //                    save_chatList.add(all_chatList.get(i).get("chat_content"));
//                //                }
////                }
////                cnt++;
//
//            }
//        }



        Button btn = findViewById(R.id.bt_single_chat_send);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // [ 메시지 입력 및 메시지 저장 ]
                EditText editText = (EditText) findViewById(R.id.et_single_chat_input);
                inputStr = editText.getText().toString();    // 메시지 값 저장.
                System.out.println("==============1");
                System.out.println(inputStr);
                System.out.println("==============1");


                // 채팅 갯수 증가
                chat_length++;


                Toast.makeText(SingleChat.this, "보내기 버튼 눌러짐", Toast.LENGTH_SHORT).show();



                // [ 현재 날짜, 시간 계산 ]
                long mNow;
                Date mDate;
                SimpleDateFormat mf_date = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat mf_time = new SimpleDateFormat("HH:mm:ss");

                mNow = System.currentTimeMillis();
                mDate = new Date(mNow);
                String current_date = mf_date.format(mDate);    // 현재 날짜
                String current_time = mf_time.format(mDate);    // 현재 시간



                // < 대화 목록 정보 리스폰 >
                Response.Listener<String> response_server = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // 리스폰 값 확인
                        System.out.println("=================================1");
                        System.out.println(response);
                        System.out.println("=================================1");

                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            // DB 연동 성공 후
                            if(success){
                                Toast.makeText(getApplicationContext(), "소켓 서버 연동에 성공하였습니다.", Toast.LENGTH_SHORT).show();
//                                 새로고침을 해준다.

                                check_nickName = check_getter_id;
                                check_value = true;

                                System.out.println("알림메시지를 위한 닉네임 값: " +check_nickName);
                                System.out.println("알림메시지를 위한 닉네임 값: " +check_nickName);
                                System.out.println("알림메시지를 위한 닉네임 값: " +check_nickName);

//                                check_getChat_push.put(check_getter_id, true);

                                // 초기화
//                                ListViewAdapter5.items.clear();

                                try {
                                    Thread.sleep(1000);
                                    SingleChat.intentCheck = true;
                                    Intent intent2 = new Intent(SingleChat.this, Chat.class);
                                    overridePendingTransition(0, 0); //인텐트 애니메이션 없애기
                                    startActivity(intent2); // 액티비티 이동
                                    overridePendingTransition(0, 0); //인텐트 애니메이션 없애기
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }


//                                try {
//                                    Thread.sleep(500);
//                                    Intent intent = getIntent();
//                                    finish(); //현재 액티비티 종료 실시
//                                    overridePendingTransition(0, 0); //인텐트 애니메이션 없애기
//                                    startActivity(intent); //현재 액티비티 재실행 실시
//                                    overridePendingTransition(0, 0); //인텐트 애니메이션 없애기
//                                }
//                                catch (Exception e){
//                                    e.printStackTrace();
//                                }
                            }else{
                                Toast.makeText(getApplicationContext(), "소켓 서버 연동에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };



                // [[ 서버에 요청 ]]
                // [ 보낼 데이터 ]
                // - sender_nick : 보낸 사람 => sender 키 값으로 보내줘야함
                // - getter_nick : 받는 사람 => getter 키 값으로 보내줘야함
                // - inputStr : 메시지 입력 값 => chat_content 키 값으로 보내줘야함
                // - current_date : 현재 날짜 => chat_day 키 값으로 보내줘야함
                // - current_time : 현재 시간 => chat_time 키 값으로 보내줘야함
                ServerChatRequest serverChatRequest = new ServerChatRequest(sender_nick, getter_nick, inputStr, current_date, current_time, response_server);
                RequestQueue queue = Volley.newRequestQueue(SingleChat.this);
                queue.add(serverChatRequest);
            }
        });







        // -------------------- 하단바 및 뒤로가기 --------------------
        btn_move = findViewById(R.id.bt_singlechat_back);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

        btn_move = findViewById(R.id.bt_home);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Login.userL == 0) {
                    Intent intent2 = new Intent(SingleChat.this, Main.class);
                    startActivity(intent2); // 액티비티 이동
                    finish();
                }else if(Login.userL == 1) {
                    Intent intent2 = new Intent(SingleChat.this, MainLogin.class);
                    startActivity(intent2); // 액티비티 이동
                    finish();
                }
            }
        });

        btn_move = findViewById(R.id.bt_auction);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(SingleChat.this, Auction.class);
                startActivity(intent2); // 액티비티 이동
                finish();
            }
        });

        btn_move = findViewById(R.id.bt_scrap);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(SingleChat.this, MyScrap.class);
                startActivity(intent2); // 액티비티 이동
                finish();
            }
        });

        btn_move = findViewById(R.id.bt_chat);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(SingleChat.this, Chat.class);
                startActivity(intent2); // 액티비티 이동
                finish();
            }
        });

        btn_move = findViewById(R.id.bt_myPage);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(SingleChat.this, MyPage.class);
                startActivity(intent2); // 액티비티 이동
                finish();
            }
        });

    }
}