package com.example.main_;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;


public class Login extends AppCompatActivity {

    public static int userL = 0; // 비로그인 상태는 0, 로그인 상태는 1
    public static String userL2 = "";   // 로그인 된 user_id 값이 담겨있는 변수
    public static String userNick = "";
    public static String check = "0";    // 서버 연동 파악을 위한 변수
    //    public static boolean check2 = false;   // 로그인 성공 시 확인 변수
    private AlertDialog dialog;

    private EditText et_login_id, et_login_pw;
    private Button btn_move, btn_login;
    public static Timer timer;
    public static boolean change_check; // 채팅의 갯수가 변화하는 시점 체크
    public static int temp_change;

    public static List<Integer> chat_lengthList = new ArrayList<>();

    public static Map<String, String> max_countMap = new HashMap<>();

    ArrayList<String> keyList = new ArrayList<>();

    public static int beforeCnt;
    public static int afterCnt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_login_id = findViewById(R.id.et_login_id);
        et_login_pw = findViewById(R.id.et_login_pw);
        btn_login = findViewById(R.id.bt_login_login);



        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //에딧 텍스트에 현재 입력 되어 있는 값을 가져온다.
                String userID = et_login_id.getText().toString();
                String userPW = et_login_pw.getText().toString();


                // [ 첫 로그인 이후, 로그인 된 id를 갖고 서버에 요청에 대한 리스폰 ]
                Response.Listener<String> responseListener_server = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("=================================4");
                        System.out.println(response);
                        System.out.println("=================================4");

                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if(success){
//                                Toast.makeText(getApplicationContext(), "서버 연동에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                check = "1";

                            }else{
                                Toast.makeText(getApplicationContext(), "서버 연동에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                check = "0";
                                return;
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };



                // [ 첫 번째 로그인 후, 로그인 성공 여부 파악 리스폰 ]
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); // 이 부분에서 php와 같은 키 값을 사용해야 함
                            System.out.println("=================================3");
                            System.out.println(response);
                            System.out.println("=================================3");

                            if(success) { // 로그인에 성공한 경우
                                userL = 1;          // 로그인 상태 on : 1
                                userL2 = userID;    // 로그인에 성공한 user_id 값을 담는다.
                                userNick = jsonObject.getString("user_nickName");
                                System.out.println("=================================555555555555");
                                System.out.println(userNick);
                                System.out.println("=================================555555555555");
                                check = "1";        // 서벼 연동 on : "1"
                                String userID = jsonObject.getString("user_id"); // 이 부분에서 php와 같은 키 값을 사용해야 함
                                String userPW = jsonObject.getString("user_pw"); // 이 부분에서 php와 같은 키 값을 사용해야 함
                                Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, MainLogin.class); // 로그인에 성공하면 로그인 페이지로 이동
                                intent.putExtra("user_id", userID);
                                intent.putExtra("user_pw", userPW);


                                // [ 첫 로그인 이후, 로그인 된 id를 갖고 서버에 요청 ] - 한번만 요청한다. 반복할 땐, check 값만 보냄.
                                ServerRequest serverRequest = new ServerRequest(userL2, responseListener_server);
                                RequestQueue queue = Volley.newRequestQueue(Login.this);
                                queue.add(serverRequest);
                                startActivity(intent);


                            }else { //로그인에 실패했을 경우
                                Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                check = "0";    // 서버 연동 off : "0"
                                return;

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };


                // [ 첫 로그인 요청 ]
                // 서버로 Volley를 이용해서 요청을 함
                LoginRequest loginRequest = new LoginRequest(userID, userPW, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Login.this);
                queue.add(loginRequest);


                // -------------------- [ 서버 연동 반복 관련 코드 ] ----------------------
                TimerTask TT = new TimerTask() {
                    @Override
                    public void run() { // 반복실행할 구문 넣고 실행
                        // 계속 연동한다. [ 반복 요청에 대한 반복 리스폰 ]
                        Response.Listener<String> responseListener_server2 = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                System.out.println("=========================5");
                                System.out.print("서버 연동 반복 요청 결과: ");
                                System.out.println(response);
                                System.out.println("=========================5");

                                try{
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                    String chat_count = jsonObject.getString("chat_count");
                                    JSONArray max_countList = jsonObject.getJSONArray("max_countList");

                                    for(int i=0; i<max_countList.length(); i++){
                                        max_countMap = new HashMap<>();
                                        // max_countList 인덱스별 jsonObject
                                        JSONObject jso1 = new JSONObject(String.valueOf(max_countList.get(i)));
                                        String j_chat_id = jso1.getString("chat_id");
                                        String j_max_count = jso1.getString("max_count");
//                                        sum += Integer.parseInt(j_max_count);
//                                        keyList.add(j_chat_id);

                                        max_countMap.put(j_chat_id, j_max_count);
                                        System.out.println(max_countMap);
                                    }


                                    if(success){
                                        Log.d("check", "------------- 서버연동 성공 -------------");
//                                        Toast.makeText(getApplicationContext(), "서버 연동에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                        check = "1";



//                                        int cnt = 0;
//                                        for(int i=0; i< max_countMap.size(); i++){
//                                            cnt += Integer.parseInt(max_countMap.get(keyList.get(i)));
//                                        }


                                        // sender에게 온 채팅 갯수
//                                        int cnt = Integer.valueOf(chat_count)+1;
//                                        chat_lengthList.add(cnt);
//                                        System.out.println("=========================1");
//                                        System.out.println(chat_lengthList.size());
//                                        System.out.println("=========================1");
//                                        if(chat_lengthList.size() >= 3){
//                                            chat_lengthList.remove(0);
//                                        }


                                        chat_lengthList.add(Integer.parseInt(chat_count));
                                        if(chat_lengthList.size() >= 3){
                                            chat_lengthList.remove(0);
                                        }


                                        for(int i=0; i<chat_lengthList.size(); i++) {
                                            System.out.println(chat_lengthList.get(i));
                                        }



                                        // [ 알람 기능을 위한 if 문 ]
                                        // 첫 번째 인덱스와 두 번째 인덱스가 같지 않다면
                                        if(chat_lengthList.size() > 1 && chat_lengthList.get(0) > 0 && chat_lengthList.get(0) != chat_lengthList.get(1)){
                                            // 이벤트 처리(상단 바에 "메시지가 도착했습니다" 알림이 뜬다.)
                                            // (확인 시, sender에게 온 총 메시지 갯수를 확인하는 기능을 넣을 수 있음.)
                                            beforeCnt = chat_lengthList.get(0);
                                            afterCnt = chat_lengthList.get(1);
                                            change_check = true;
                                        }else{
                                            change_check = false;
                                        }




                                        System.out.println("확인된 닉네임: " + SingleChat.check_nickName);
                                        System.out.println("확인된 value: " + SingleChat.check_value);
                                        if(change_check == true){
                                            if(userNick != SingleChat.check_nickName && SingleChat.check_value == true){
                                                int resultCnt = afterCnt - beforeCnt;
                                                System.out.println("안 본 메세지 갯수: " + resultCnt);
                                                Toast toast = Toast.makeText(getApplicationContext(), resultCnt + "개 의 메세지가 도착했습니다.", Toast.LENGTH_LONG);
                                                toast.setGravity(Gravity.TOP, 40, 200);
                                                toast.show();
                                                SingleChat.check_value = false;
                                                change_check = false;
                                            }

                                        }
                                        // chat_lengthList.add(SingleChat.chat_length);

//                                        // 각 채팅방 id를 기준으로,
//                                        // 만약 채팅 갯수 맵의 value값이 변하게 된다면,
//                                        // 알람을 누구에게 보내라?
//                                        if(SingleChat.chat_lengthMap.get("check") == 1 && 서버에서 받아온 값 != SingleChat.chat_lengthMap.get(SingleChat.ch_check_id)){
////                                            SingleChat.chat_lengthMap.
//                                        }



                                    }else{
                                        Log.d("check", "------------- 서버연동 실패 -------------");
//                                        Toast.makeText(getApplicationContext(), "서버 연동에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                        check = "0";
                                        return;
                                    }
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        };


                        // [ 로그인 된 id를 갖고 서버에 요청한 후, check 값을 갖고 서버에 요청] - 반복 요청
                        ServerRequest_repeat serverRequest2 = new ServerRequest_repeat(userL2, responseListener_server2);
                        queue.add(serverRequest2);
                    }
                };

                // 반복 타이머
                timer = new Timer();
                // 반복 타이머 설정
                timer.schedule(TT, 0, 2000);
            }
        });











        // ---------------------------- 뒤돌아가기 및 하단바 ----------------------------
        btn_move = findViewById(R.id.bt_login_back);
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
                Intent intent2 = new Intent(Login.this, Main.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_auction);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Login.this, Auction.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_scrap);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                dialog=builder.setMessage("로그인 후 입장 가능합니다.").setPositiveButton("확인", null).create();
                dialog.show();
                return;
            }
        });

        btn_move = findViewById(R.id.bt_chat);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                dialog=builder.setMessage("로그인 후 입장 가능합니다.").setPositiveButton("확인", null).create();
                dialog.show();
                return;
            }
        });

        btn_move = findViewById(R.id.bt_myPage);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                dialog=builder.setMessage("로그인 후 입장 가능합니다.").setPositiveButton("확인", null).create();
                dialog.show();
                return;
            }
        });
    }
}