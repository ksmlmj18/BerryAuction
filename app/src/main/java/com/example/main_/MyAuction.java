package com.example.main_;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.InstrumentationInfo;
import android.icu.lang.UScript;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyAuction extends AppCompatActivity {

    Auction auction = new Auction();



    private Button btn_move;
    private List<String> data;
    private AlertDialog dialog;
    private String parentCategory;
    private String poster_id;
    private String title;
    private String startTime;
    private String endTime;
    private String content;
    private String r_poster_id;
    private String reply_id;
    private String reply_content;
    private String registrant;
    private String check;
    private String choose;
    private String nickName;
    private String r_content;
    private String regdate;
    public static String nickName2;
    public static String end;
    public static String post_nick2;
    public static String post_choose2;
    public static String post_end2;
    public static ArrayList<String>poster_id_List = new ArrayList<>();
    public static ArrayList<String>porter_end_list = new ArrayList<>();
    public ArrayList<String>title_list;
    public ArrayList<String>startTime_list;
    public ArrayList<String>endTime_list;
    public ArrayList<String>content_list;
    public ArrayList<String>r_poster_id_list;
    public ArrayList<String>reply_content_list;
    public static ArrayList<String>nickName_list = new ArrayList<>();
    public static ArrayList<String>reply_id_list = new ArrayList<>();
    //    public static ArrayList<String>registrant_list = new ArrayList<>(); //m
    public static ArrayList<String>choose_list = new ArrayList<>();
    //    public ArrayList<String>price_list_list;
    public ArrayList<String>parentCategory_list;
    public static String check2 = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_auction);

        btn_move = findViewById(R.id.bt_myauction_back);
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
                    Intent intent2 = new Intent(MyAuction.this, Main.class);
                    startActivity(intent2); // 액티비티 이동
                }else if(Login.userL == 1) {
                    Intent intent2 = new Intent(MyAuction.this, MainLogin.class);
                    startActivity(intent2); // 액티비티 이동
                }
            }
        });

        btn_move = findViewById(R.id.bt_auction);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MyAuction.this, Auction.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_scrap);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MyAuction.this, MyScrap.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_chat);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MyAuction.this, Chat.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_myPage);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MyAuction.this, MyPage.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_refresh);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent(MyAuction.this, Refresh.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        ListView listview ;
        ListViewAdapter4 adapter;
        // Adapter 생성
        adapter = new ListViewAdapter4() ;
        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listView1);
        listview.setAdapter(adapter);


        // 댓글 뜯는 애
        for (int i = 0; i < MainLogin.jsonSize3; i++) {
            try {

                JSONObject jsonObject = MainLogin.reply_infoList.getJSONObject(i);
                nickName = jsonObject.getString("nickName");
                r_poster_id = jsonObject.getString("r_poster_id");
                reply_id = jsonObject.getString("reply_id");
                r_content = jsonObject.getString("r_content");
                regdate = jsonObject.getString("regdate");


                nickName_list.add(nickName);
                reply_id_list.add(reply_id);
//                    System.out.println("nicK: "+nickName);
//                    System.out.println("reply: "+reply_id);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }






        for (int i = 0; i < MainLogin.jsonSize; i++) {
            try {
                JSONObject jsonObject = MainLogin.poster_infoList.getJSONObject(i);
//                    JSONObject jsonObject2 = Main.price_list.getJSONObject(i);
                poster_id = jsonObject.getString("poster_id");
                parentCategory = jsonObject.getString("parentCategory");
                title = jsonObject.getString("title");
                content = jsonObject.getString("content");
                startTime = jsonObject.getString("startTime");
                endTime = jsonObject.getString("endTime");
                registrant = jsonObject.getString("registrant");
                choose = jsonObject.getString("choose");
//                System.out.println("choose: "+choose);
                end = jsonObject.getString("finish");
//                System.out.println(end);

                if(Login.userNick.equals(registrant)) {
                    poster_id_List.add(poster_id); // 현재 작성자가 작성한 글의 게시글 키값만 저장 //m
                    porter_end_list.add(end);
                    choose_list.add(choose);
                    System.out.println("post_id: "+poster_id);
                    System.out.println("choose: "+ choose);
                    System.out.println("end: "+end);
                }





            } catch (JSONException e) {
                e.printStackTrace();
            }
            // 아이템 추가.
            if(Login.userNick.equals(registrant)) {
                if(choose.equals("0")) { // 채택 안 했을 때
                    adapter.addItem(
                            parentCategory, startTime, endTime, title, Login.userNick, content, poster_id, registrant, "채택미정", end);
                    adapter.notifyDataSetChanged();
                }else if(!choose.equals("0")){ // 채택 했을 때
                    adapter.addItem(
                            parentCategory, startTime, endTime, title, Login.userNick, content, poster_id, registrant, "채택완료", end);
                    adapter.notifyDataSetChanged();
                }
            }
        }

//        for(int i = 0; i < MyAuction.nickName_list.size(); i++) {
//            if(MyAuction.post_choose2.equals(MyAuction.reply_id_list.get(i))) {
//                nickName2 = MyAuction.nickName_list.get(i);
//                System.out.println("nickName: "+nickName);
//                break;
//            }
//        }




        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // 해당 아이템의 포지션 값 저장하기
                String post_Title = ((ListViewItem)adapter.getItem(i)).getTitle();
                check = post_Title;
                String post_Category = ((ListViewItem)adapter.getItem(i)).getCategory();
                String post_Date = ((ListViewItem)adapter.getItem(i)).getDate();
                String post_Date2 = ((ListViewItem)adapter.getItem(i)).getDate2();
                String post_Detail = ((ListViewItem)adapter.getItem(i)).getDetail();
                String post_id = ((ListViewItem)adapter.getItem(i)).getPostId();
                Auction.post_id2 = post_id; // 내가 선택한 게시글의 키값
                System.out.println("Auction.post_id2: "+Auction.post_id2);
                System.out.println("post_id: "+post_id);
                String post_nick = ((ListViewItem)adapter.getItem(i)).getNick();
                post_nick2 = post_nick;
                System.out.println(post_nick);
                System.out.println(post_nick2);
                String post_choose = ((ListViewItem)adapter.getItem(i)).getPick();
                post_choose2 = post_choose;
                System.out.println("cho!!!!!"+post_choose);
                System.out.println(post_choose2);
                String post_end = ((ListViewItem)adapter.getItem(i)).getEnd();
                post_end2 = post_end;
                System.out.println("!!"+post_end);
                System.out.println(post_end2);
//                String post_Price = ((ListViewItem)adapter.getItem(i)).getPrice();

                // 이동할 액티비티 지정
                Intent intent2 = new Intent(MyAuction.this, PostCheck.class);

                // 액티비티 이동과 함께 값 넘겨주기
                intent2.putExtra("title",post_Title);
                intent2.putExtra("category",post_Category);
                intent2.putExtra("startDate",post_Date);
                intent2.putExtra("endDate",post_Date2);
//                intent2.putExtra("price", post_Price);
                intent2.putExtra("detail", post_Detail);
                intent2.putExtra("nickName", post_nick);
//                intent2.putExtra("pick", post_choose);

                startActivity(intent2); // 액티비티 이동
            }
        });

        check2 = "1";




    }
}