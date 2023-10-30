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

import java.util.ArrayList;
import java.util.List;

public class MyScrap extends AppCompatActivity {

    private Button btn_move;
    private List<String> data;
    public static String post_nick2;
    public static String post_choose2;
    public static String post_end2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_scrap);

        btn_move = findViewById(R.id.bt_myscrap_back);
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
                Intent intent2 = new Intent(MyScrap.this, Main.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_auction);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MyScrap.this, Auction.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_scrap);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MyScrap.this, MyScrap.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_chat);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MyScrap.this, Chat.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_myPage);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MyScrap.this, MyPage.class);
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
        for(int i=1; i<=10; i++) {
            // 아이템 추가.
            adapter.addItem("IT", "22/08/09", "22/08/11","35000원", "재능기부","세부사항은 여기에 기록바랍니다~",
                    "post_id" , "S", "0");
            adapter.notifyDataSetChanged();
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
                Intent intent2 = new Intent(MyScrap.this, PostCheck.class);

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