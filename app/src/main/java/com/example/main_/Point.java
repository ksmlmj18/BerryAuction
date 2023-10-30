package com.example.main_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Point extends AppCompatActivity {

    private Button btn_move;

    String q1,q2,q3;
    int sum = 0;
    float avg = 0;
    String avg2;

    Spinner p1;
    Spinner p2;
    Spinner p3;


    //뷰의 주소값을 담을 참조 변수
    TextView text1;

    private void populateSpinners() {
        ArrayAdapter<CharSequence> fAdapter;
        fAdapter = ArrayAdapter.createFromResource(this,
                R.array.평점,
                android.R.layout.simple_spinner_item);
        fAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        p1.setAdapter(fAdapter);

    }
    private void populateSpinners2() {
        ArrayAdapter<CharSequence> fAdapter;
        fAdapter = ArrayAdapter.createFromResource(this,
                R.array.평점,
                android.R.layout.simple_spinner_item);
        fAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        p2.setAdapter(fAdapter);

    }
    private void populateSpinners3() {
        ArrayAdapter<CharSequence> fAdapter;
        fAdapter = ArrayAdapter.createFromResource(this,
                R.array.평점,
                android.R.layout.simple_spinner_item);
        fAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        p3.setAdapter(fAdapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);

        TextView tv_point_nick = (TextView)findViewById(R.id.tv_point_name);
        Intent intent = getIntent();
        tv_point_nick.setText(intent.getStringExtra("nickName"));

//        //id 속성이 text id값의 뷰의 주소 값을 얻어온다.
//        TextView point_name = (TextView)findViewById(R.id.tv_point_name);
//
//        // 보내온 Intent를 얻는다.
//        Intent intent = getIntent();
//
//        //새로운 문자열을 설정한다.
//        point_name.setText(intent.getStringExtra("nickName"));


        // 스피너 값 저장

        Spinner p1 = (Spinner)findViewById(R.id.dd_point_p1);
        Spinner p2 = (Spinner)findViewById(R.id.dd_point_p2);;
        Spinner p3 = (Spinner)findViewById(R.id.dd_point_p3);





        btn_move = findViewById(R.id.bt_point_back);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_move = findViewById(R.id.bt_point_cancel);
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
                    Intent intent2 = new Intent(Point.this, Main.class);
                    startActivity(intent2); // 액티비티 이동
                }else if(Login.userL == 1) {
                    Intent intent2 = new Intent(Point.this, MainLogin.class);
                    startActivity(intent2); // 액티비티 이동
                }
            }
        });

        btn_move = findViewById(R.id.bt_auction);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Point.this, Auction.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_scrap);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Point.this, MyScrap.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_chat);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Point.this, Chat.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_myPage);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Point.this, MyPage.class);
                startActivity(intent2); // 액티비티 이동
            }
        });









        // 저장 버튼을 클릭했을 때.
        Button btn= findViewById(R.id.bt_point_save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                q1 = p1.getSelectedItem().toString();
                q2 = p2.getSelectedItem().toString();
                q3 = p3.getSelectedItem().toString();
                sum = Integer.parseInt(q1) + Integer.parseInt(q2) + Integer.parseInt(q3);
                avg = sum/3.0f;
                avg2 = Float.toString(avg);
                System.out.println("AVG: "+avg2);
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); // 이 부분에서 php와 같은 키 값을 사용해야 함

                            if(success) {
                                Toast.makeText(getApplicationContext(), "평점이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Point.this, MyAuction.class); // 회원가입에 성공하면 오그인 페이지로 이동
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(), "평점 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                StarRequest starRequest = new StarRequest(ListViewAdapter4.nickName, avg2, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Point.this);
                queue.add(starRequest);
                //       point_name;
                //       (String)score;

            }
        });
    }
}