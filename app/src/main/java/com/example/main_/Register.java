package com.example.main_;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {

    private Button btn_move, btn_register, btn_check, btn_check2;
    private EditText et_register_id, et_register_pw,et_register_pw2, et_register_nick, et_register_name, et_register_phone, et_register_email, et_register_career;
    private AlertDialog dialog;
    private boolean validate=false;
    private boolean validate2=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // 액티비티 시작 시 처음으로 실행되는 생명주기
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //각각의 값 찾아주기
        et_register_id = (EditText)findViewById(R.id.et_register_id);
        et_register_pw = (EditText)findViewById(R.id.et_register_pw);
        et_register_pw2 = (EditText)findViewById(R.id.et_register_pw2);
        et_register_nick = (EditText)findViewById(R.id.et_register_nick);
        et_register_name = (EditText)findViewById(R.id.et_register_name);
        et_register_phone = (EditText)findViewById(R.id.et_register_phone);
        et_register_email = (EditText)findViewById(R.id.et_register_email);
        et_register_career = (EditText)findViewById(R.id.et_register_career);



        //아이디 중복 체크
        btn_check = findViewById(R.id.bt_register_id);
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = et_register_id.getText().toString();
                if(validate)
                {
                    return;
                }
                if(userID.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                    dialog=builder.setMessage("아이디는 빈 칸일 수 없습니다").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse=new JSONObject(response);
                            boolean success=jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder=new AlertDialog.Builder( Register.this );
                                dialog=builder.setMessage("사용할 수 있는 아이디입니다.")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                                et_register_id.setEnabled(false);
                                validate=true;
                                btn_check.setText("확인");
                            }
                            else{
                                AlertDialog.Builder builder=new AlertDialog.Builder( Register.this );
                                dialog=builder.setMessage("사용할 수 없는 아이디입니다.")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                // 서버로 Volley를 이용해서 요청을 함
                RegisterRequest3 registerRequest = new RegisterRequest3(userID,responseListener);
                RequestQueue queue = Volley.newRequestQueue(Register.this);
                queue.add(registerRequest);

            }
        });

        //닉네임 중복 체크
        btn_check2 = findViewById(R.id.bt_register_nick);
        btn_check2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userNick = et_register_nick.getText().toString();
                if(validate2)
                {
                    return;
                }
                if(userNick.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                    dialog=builder.setMessage("닉네임은 빈 칸일 수 없습니다.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse=new JSONObject(response);
                            boolean success=jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder=new AlertDialog.Builder( Register.this );
                                dialog=builder.setMessage("사용할 수 있는 닉네임입니다.")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                                et_register_nick.setEnabled(false);
                                validate2=true;
                                btn_check2.setText("확인");
                            }
                            else{
                                AlertDialog.Builder builder=new AlertDialog.Builder( Register.this );
                                dialog=builder.setMessage("사용할 수 없는 닉네임입니다.")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };


                // 서버로 Volley를 이용해서 요청을 함
                RegisterRequest2 registerRequest = new RegisterRequest2(userNick,responseListener);
                RequestQueue queue = Volley.newRequestQueue(Register.this);
                queue.add(registerRequest);

            }
        });


        //회원가입 버튼 클릭 시 수행
        btn_register = findViewById(R.id.bt_register_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //에딧 텍스트에 현재 입력 되어 있는 값을 가져온다.
                String userID = et_register_id.getText().toString();
                String userPW = et_register_pw.getText().toString();
                String userPW2 = et_register_pw2.getText().toString();
                String userNick = et_register_nick.getText().toString();
                String userName = et_register_name.getText().toString();
                String userPhone = et_register_phone.getText().toString();
                String userEmail = et_register_email.getText().toString();
                String userCareer = et_register_career.getText().toString();

                if(userID.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                    dialog=builder.setMessage("아이디는 빈 칸일 수 없습니다").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }else if(userPW.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                    dialog=builder.setMessage("비밀번호는 빈 칸일 수 없습니다").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }else if(userNick.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                    dialog=builder.setMessage("닉네임은 빈 칸일 수 없습니다").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }else if(userName.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                    dialog=builder.setMessage("이름은 빈 칸일 수 없습니다").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }else if(userPhone.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                    dialog=builder.setMessage("전화번호는 빈 칸일 수 없습니다").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }else if(userEmail.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                    dialog=builder.setMessage("이메일은 빈 칸일 수 없습니다").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }

                if(!userPW.equals(userPW2)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                    dialog=builder.setMessage("비밀번호가 동일하지 않습니다.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }

                if(validate == false) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                    dialog=builder.setMessage("아이디 중복체크를 해주세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }else if(validate2 == false) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                    dialog=builder.setMessage("닉네임 중복체크를 해주세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() { //응답을 문자열로 받아서 여기다 넣어달라는 뜻(응답을 성공적으로 받았을 떄 이 메소드가 자동으로 호출)
                    @Override
                    public void onResponse(String response) { //회원가입을 요청 후 결과값을 JSON오브젝트로 받는다.
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); // 이 부분에서 php와 같은 키 값을 사용해야 함

                            if(success) {
                                Toast.makeText(getApplicationContext(), "회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Register.this, Login.class); // 회원가입에 성공하면 오그인 페이지로 이동
                                startActivity(intent);
                                finish();
                            }else { //회원가입에 실패했을 경우
                                Toast.makeText(getApplicationContext(), "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };

                // 서버로 Volley를 이용해서 요청을 함
                RegisterRequest registerRequest = new RegisterRequest(userID, userPW,userName, userPhone, userNick, userEmail, userCareer, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Register.this);
                queue.add(registerRequest);


            }
        });


        btn_move = findViewById(R.id.bt_register_back); // 뒤로 가기 버튼 클릭 시 이전 화면으로 이동
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_move = findViewById(R.id.bt_register_cancle); // 취소 버튼 클릭 시 이전 화면으로 이동
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
                Intent intent2 = new Intent(Register.this, Main.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_auction);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Register.this, Auction.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_scrap);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                dialog=builder.setMessage("로그인 후 입장 가능합니다.").setPositiveButton("확인", null).create();
                dialog.show();
                return;
            }
        });

        btn_move = findViewById(R.id.bt_chat);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                dialog=builder.setMessage("로그인 후 입장 가능합니다.").setPositiveButton("확인", null).create();
                dialog.show();
                return;
            }
        });

        btn_move = findViewById(R.id.bt_myPage);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                dialog=builder.setMessage("로그인 후 입장 가능합니다.").setPositiveButton("확인", null).create();
                dialog.show();
                return;
            }
        });
    }
}