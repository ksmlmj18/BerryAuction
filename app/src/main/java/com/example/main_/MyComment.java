package com.example.main_;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
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

public class MyComment extends AppCompatActivity {

    private Button btn_move;
    private List<String> data;
    static String parentCategory;
    static String poster_id;
    static String title;
    static String startTime;
    static String endTime;
    static String content;
    static String r_poster_id;
    static String reply_id;
    static String reply_content;
    static String registrant;
    static String check;
    String nickName;
    boolean check2 = false;
    String total;
    static String end;
    public static String post_nick2;
    public static String post_choose2;
    public static String post_end2;

    ArrayList<String> nickName_list = new ArrayList<>();
    ArrayList<String> r_poster_id_list = new ArrayList<>();
    ArrayList<String> content_list = new ArrayList<>();
    ArrayList<String> avg_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comment);



        btn_move = findViewById(R.id.bt_mycomment_back);
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
                    Intent intent2 = new Intent(MyComment.this, Main.class);
                    startActivity(intent2); // 액티비티 이동
                }else if(Login.userL == 1) {
                    Intent intent2 = new Intent(MyComment.this, MainLogin.class);
                    startActivity(intent2); // 액티비티 이동
                }
            }
        });

        btn_move = findViewById(R.id.bt_auction);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MyComment.this, Auction.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_scrap);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MyComment.this, MyScrap.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_chat);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MyComment.this, Chat.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_myPage);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MyComment.this, MyPage.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_refresh);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent(MyComment.this, Refresh.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        ListView listview ;
        ListViewAdapter adapter;
        // Adapter 생성
        adapter = new ListViewAdapter() ;
        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listView1);
        listview.setAdapter(adapter);


        // 전체 댓글 리스트에 저장
        for(int i = 1; i < MainLogin.jsonSize3; i++) {
            try {
                JSONObject jsonObject2 = MainLogin.reply_infoList.getJSONObject(i);
                nickName = jsonObject2.getString("nickName");
                r_poster_id = jsonObject2.getString("r_poster_id");
                reply_id = jsonObject2.getString("reply_id");
                content = jsonObject2.getString("r_content");

                nickName_list.add(nickName);
                r_poster_id_list.add(r_poster_id);
                content_list.add(content);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

//        //평균 가격가를 구하기 위한 연산
//        for(int i = 1; i < MainLogin.jsonSize3; i++) {
//            int cnt = 0;
//            int save = 0;
//            for(int j = 1; j < MainLogin.jsonSize3; j++) {
//                if(r_poster_id_list.get(i).equals(r_poster_id_list.get(j))) {
//                    save += Integer.parseInt(content_list.get(j));
//                    cnt++;
//                }
//                int avg = save/cnt;
//                avg_list.add(Integer.toString(avg));
//            }
//        }



        // 게시글 갯수만큼 반복
        for (int i = 1; i < MainLogin.jsonSize; i++) {
            try {
                check2 = false;
                JSONObject jsonObject = MainLogin.poster_infoList.getJSONObject(i);

                poster_id = jsonObject.getString("poster_id");
                parentCategory = jsonObject.getString("parentCategory");
                title = jsonObject.getString("title");
                content = jsonObject.getString("content");
                startTime = jsonObject.getString("startTime");
                endTime = jsonObject.getString("endTime");
                registrant = jsonObject.getString("registrant");
                String ch = jsonObject.getString("choose");
                end = jsonObject.getString("finish");

                // 현재 게시글과 모든 댓글 대조
                for(int j = 1; j < nickName_list.size(); j++) {
                    if(Login.userNick.equals(nickName_list.get(j)) && poster_id.equals(r_poster_id_list.get(j))) {
                        check2 = true; // 현재 게시글에 내 댓글이 존재한다면 check2값 true로 변경
                        break;
                    }
                }




            } catch (JSONException e) {
                e.printStackTrace();
            }
            // 아이템 추가.
            //  내가 현재 로그인한 닉네임이 댓글에 내 닉네임이 있을 때, 그 댓글이 달린 키값과 현재 내가 보는 게시글의 키값이 같다면
            if(check2) { // 현재 게시글에 내 댓글이 존재하면 추가
                adapter.addItem(
                        parentCategory, startTime, endTime, title, " ", content, poster_id, registrant, end);
                adapter.notifyDataSetChanged();
            }
        }




        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // 해당 아이템의 포지션 값 저장하기
                String post_Title = ((ListViewItem)adapter.getItem(i)).getTitle();
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
                System.out.println(post_choose);
                System.out.println(post_choose2);
                String post_end = ((ListViewItem)adapter.getItem(i)).getEnd();
                post_end2 = post_end;
//                System.out.println(post_end);
//                System.out.println(post_end2);
//                String post_Price = ((ListViewItem)adapter.getItem(i)).getPrice();

                // 이동할 액티비티 지정
                Intent intent2 = new Intent(MyComment.this, PostCheck.class);

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





    }
}