package com.example.main_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

public class MyPage extends AppCompatActivity {

    private Button btn_move;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);



        btn_move = findViewById(R.id.bt_myPage_back);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_move = findViewById(R.id.bt_myPage_edit);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 클릭 시 마이페이지에서 개인 정보 수정으로 이동
                try {
                    JSONObject jsonObject2 = MainLogin.info_array.getJSONObject(0);
                    String name = jsonObject2.getString("user_name");
                    String phonNum = jsonObject2.getString("user_phoneNum");
                    String mail = jsonObject2.getString("user_email");
                    String career = jsonObject2.getString("user_career");

                    Intent intent2 = new Intent(MyPage.this, UserinfoEdit.class);
                    intent2.putExtra("name",name);
                    intent2.putExtra("phonNum", phonNum);
                    intent2.putExtra("mail", mail);
                    intent2.putExtra("career", career);

                    MyPage.this.startActivity(intent2); // 액티비티 이동
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_move = findViewById(R.id.bt_myPage_auctionCheck);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 클릭 시 마이페이지에서 내 게시물로 이동
                Intent intent = new Intent(MyPage.this, MyAuction.class);
                startActivity(intent); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_myPage_commentCheck);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 클릭 시 마이페이지에서 내 댓글로 이동
                Intent intent = new Intent(MyPage.this, MyComment.class);
                startActivity(intent); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_myPage_interCheck);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 클릭 시 마이페이지에서 내 관심으로 이동
                Intent intent = new Intent(MyPage.this, MyScrap.class);
                startActivity(intent); // 액티비티 이동
            }
        });

//        btn_move = findViewById(R.id.bt_home)

        
        // 이 아래로 하단바 이동 버튼 
        btn_move = findViewById(R.id.bt_home);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Login.userL == 0) {
                    Intent intent2 = new Intent(MyPage.this, Main.class);
                    startActivity(intent2); // 액티비티 이동
                }else if(Login.userL == 1) {
                    Intent intent2 = new Intent(MyPage.this, MainLogin.class);
                    startActivity(intent2); // 액티비티 이동
                }
            }
        });

        btn_move = findViewById(R.id.bt_auction);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MyPage.this, Auction.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_scrap);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MyPage.this, MyScrap.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_chat);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MyPage.this, Chat.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_myPage);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MyPage.this, MyPage.class);
                startActivity(intent2); // 액티비티 이동
            }
        });









    }
}