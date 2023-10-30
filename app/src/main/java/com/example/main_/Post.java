package com.example.main_;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class Post extends AppCompatActivity {

    private Spinner spinner, spinner2;
    private DatePickerDialog datePickerDialog;
    private DatePickerDialog datePickerDialog2;
    private androidx.appcompat.app.AlertDialog dialog;
    private EditText et_post_title, et_post_in;
    private Button dateButton;
    private Button dateButton2;
    private Button btn_move;
    private Button btn_post;
    String startYear, startMonth, startDay, endYear, endMonth, endDay;
    Spinner spinner01;
    Spinner spinner02;

    private void populateSpinners() {
        ArrayAdapter<CharSequence> fAdapter;
        fAdapter = ArrayAdapter.createFromResource(this,
                R.array.카테고리,
                android.R.layout.simple_spinner_item);
        fAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner01.setAdapter(fAdapter);

    }

    private void populateSubSpinners(int itemNum) {
        ArrayAdapter<CharSequence> fAdapter;
        fAdapter = ArrayAdapter.createFromResource(this,
                itemNum,
                android.R.layout.simple_spinner_item);
        fAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner02.setAdapter(fAdapter);
    }

    private OnItemSelectedListener spinSelectedlistener =
            new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    switch(position){
                        case (0):
                            populateSubSpinners(R.array.IT);

                            break;
                        case (1):
                            populateSubSpinners(R.array.디자인);
                            break;
                        case (2):
                            populateSubSpinners(R.array.영상);
                            break;
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }


            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        initDatePicker();
        initDatePicker2();
        dateButton = findViewById(R.id.dp_post_startDay);
        dateButton.setText(getTodaysDate());
        dateButton2 = findViewById(R.id.dp_post_endDay);
        dateButton2.setText(getTodaysDate2());

        et_post_in = findViewById(R.id.et_post_in);
        et_post_title = findViewById(R.id.et_post_title);

        spinner01 = (Spinner)findViewById(R.id.dd_post_category);
        populateSpinners();
        spinner02 = (Spinner)findViewById(R.id.dd_post_category2);

        spinner01.setOnItemSelectedListener(spinSelectedlistener);


        btn_move = findViewById(R.id.bt_post_back);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_move = findViewById(R.id.bt_post_cancel);
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
                Intent intent2 = new Intent(Post.this, Main.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_auction);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Post.this, Auction.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_scrap);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Post.this, MyScrap.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_chat);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Post.this, Chat.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_myPage);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Post.this, MyPage.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_post = findViewById(R.id.bt_post_save);
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String poster_title  = et_post_title.getText().toString();
                String poster_content  = et_post_in.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); // 이 부분에서 php와 같은 키 값을 사용해야 함

                            if(success) {
                                Toast.makeText(getApplicationContext(), "게시물이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Post.this, MainLogin.class); // 회원가입에 성공하면 오그인 페이지로 이동
                                startActivity(intent);
                                finish();
                            }else { //회원가입에 실패했을 경우
                                Toast.makeText(getApplicationContext(), "게시물 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                PostRequest postRequest = new PostRequest(Login.userNick, (String) spinner01.getSelectedItem(),
                        (String) spinner02.getSelectedItem(), poster_title , poster_content ,
                        startYear, startMonth, startDay, endYear, endMonth, endDay,responseListener);
                RequestQueue queue = Volley.newRequestQueue(Post.this);
                queue.add(postRequest);

            }
        });


    }


    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year =cal.get(Calendar.YEAR);
        int month =cal.get(Calendar.MONTH);
        month = month + 1;
        int day =cal.get(Calendar.DAY_OF_MONTH);

        return makeDateString(day,month, year);

    }

    private String getTodaysDate2() {
        Calendar cal = Calendar.getInstance();
        int year =cal.get(Calendar.YEAR);
        int month =cal.get(Calendar.MONTH);
        month = month + 1;
        int day =cal.get(Calendar.DAY_OF_MONTH);

        return makeDateString2(day,month, year);

    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month+1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };



        Calendar cal = Calendar.getInstance();
        int year =cal.get(Calendar.YEAR);
        startYear = Integer.toString(year);
        int month =cal.get(Calendar.MONTH);
        startMonth = Integer.toString(month);
        int day =cal.get(Calendar.DAY_OF_MONTH);
        startDay = Integer.toString(day);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog( this, style, dateSetListener, year, month, day);

    }

    private void initDatePicker2()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month+1;
                String date = makeDateString2(day, month, year);
                dateButton2.setText(date);
            }
        };



        Calendar cal = Calendar.getInstance();
        int year =cal.get(Calendar.YEAR);
        endYear = Integer.toString(year);
        int month =cal.get(Calendar.MONTH);
        endMonth = Integer.toString(month);
        int day =cal.get(Calendar.DAY_OF_MONTH);
        endDay = Integer.toString(day);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog2 = new DatePickerDialog( this, style, dateSetListener, year, month, day);

    }

    private String makeDateString(int day, int month, int year) {
        startYear = Integer.toString(year);
        startMonth = Integer.toString(month);
        startDay = Integer.toString(day);
        return year+"년 "+getMonthFormat(month) + " " + getDayFormat(day);
    }

    private String makeDateString2(int day, int month, int year) {
        endYear = Integer.toString(year);
        endMonth = Integer.toString(month);
        endDay = Integer.toString(day);
        return year+"년 "+getMonthFormat(month) + " " + getDayFormat(day);
    }

    private String getDayFormat(int day) {
        if(day == 1)
            return "1일";
        if(day == 2)
            return "2일";
        if(day == 3)
            return "3일";
        if(day == 4)
            return "4일";
        if(day == 5)
            return "5일";
        if(day == 6)
            return "6일";
        if(day == 7)
            return "7일";
        if(day == 8)
            return "8일";
        if(day == 9)
            return "9일";
        if(day == 10)
            return "10일";
        if(day == 11)
            return "11일";
        if(day == 12)
            return "12일";
        if(day == 13)
            return "13일";
        if(day == 14)
            return "14일";
        if(day == 15)
            return "15일";
        if(day == 16)
            return "16일";
        if(day == 17)
            return "17일";
        if(day == 18)
            return "18일";
        if(day == 19)
            return "19일";
        if(day == 20)
            return "20일";
        if(day == 21)
            return "21일";
        if(day == 22)
            return "22일";
        if(day == 23)
            return "23일";
        if(day == 24)
            return "24일";
        if(day == 25)
            return "25일";
        if(day == 26)
            return "26일";
        if(day == 27)
            return "27일";
        if(day == 28)
            return "28일";
        if(day == 29)
            return "29일";
        if(day == 30)
            return "30일";
        if(day == 31)
            return "31일";

        return "1일";

    }

    private String getMonthFormat(int month) {
        if(month == 1)
            return "1월";
        if(month == 2)
            return "2월";
        if(month == 3)
            return "3월";
        if(month == 4)
            return "4월";
        if(month == 5)
            return "5월";
        if(month == 6)
            return "6월";
        if(month == 7)
            return "7월";
        if(month == 8)
            return "8월";
        if(month == 9)
            return "9월";
        if(month == 10)
            return "10월";
        if(month == 11)
            return "11월";
        if(month == 12)
            return "12월";

        return "1월";

    }


    public void openDatePicker(View view) {
        datePickerDialog2.show();
        datePickerDialog.show();
    }




}