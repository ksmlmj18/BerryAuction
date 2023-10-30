package com.example.main_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class UserinfoEdit extends AppCompatActivity {

    private Button btn_move, btn_save;
    EditText text1;
    TextView text2;
    private EditText et_userInfoEdit_phoneNum1, et_userInfoEdit_email1, et_userInfoEdit_careertxt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo_edit);

        et_userInfoEdit_phoneNum1 = findViewById(R.id.et_userInfoEdit_phoneNum);
        et_userInfoEdit_email1 = findViewById(R.id.et_userInfoEdit_email);
        et_userInfoEdit_careertxt1 = findViewById(R.id.et_userInfoEdit_careertxt);

        //id 속성이 text id값의 뷰의 주소 값을 얻어온다.
        TextView name = (TextView)findViewById(R.id.tv_userInfoEdit_realName);
        EditText phonNum = (EditText)findViewById(R.id.et_userInfoEdit_phoneNum);
        EditText mail = (EditText)findViewById(R.id.et_userInfoEdit_email);
        EditText career = (EditText)findViewById(R.id.et_userInfoEdit_careertxt);

        // 보내온 Intent를 얻는다.
        Intent intent = getIntent();

        //새로운 문자열을 설정한다.
        name.setText(intent.getStringExtra("name"));
        phonNum.setText(intent.getStringExtra("phonNum"));
        mail.setText(intent.getStringExtra("mail"));
        career.setText(intent.getStringExtra("career"));


        btn_move = findViewById(R.id.bt_userInfoEdit_back);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_move = findViewById(R.id.bt_userinfoEdit_cancle);
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
                    Intent intent2 = new Intent(UserinfoEdit.this, Main.class);
                    startActivity(intent2); // 액티비티 이동
                }else if(Login.userL == 1) {
                    Intent intent2 = new Intent(UserinfoEdit.this, MainLogin.class);
                    startActivity(intent2); // 액티비티 이동
                }
            }
        });

        btn_move = findViewById(R.id.bt_auction);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(UserinfoEdit.this, Auction.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_scrap);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(UserinfoEdit.this, MyScrap.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_chat);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(UserinfoEdit.this, Chat.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_myPage);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(UserinfoEdit.this, MyPage.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_save = findViewById(R.id.bt_userinfoEdit_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_email = et_userInfoEdit_email1.getText().toString();
                String user_phon = et_userInfoEdit_phoneNum1.getText().toString();
                String user_career = et_userInfoEdit_careertxt1.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); // 이 부분에서 php와 같은 키 값을 사용해야 함

                            if(success) {
                                Toast.makeText(getApplicationContext(), "개인 정보가 수정되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UserinfoEdit.this, MainLogin.class); // 회원가입에 성공하면 오그인 페이지로 이동
                                startActivity(intent);
                                finish();
                            }else { //회원가입에 실패했을 경우
                                Toast.makeText(getApplicationContext(), "개인정보 수정에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                System.out.println(Login.userNick);
                System.out.println(user_phon);
                System.out.println(user_email);
                System.out.println(user_career);
                UserInfoEditRequest userInfoEditRequest = new UserInfoEditRequest(Login.userNick,user_career,user_phon,user_email,responseListener);
                RequestQueue queue = Volley.newRequestQueue(UserinfoEdit.this);
                queue.add(userInfoEditRequest);


            }
        });


    }
}