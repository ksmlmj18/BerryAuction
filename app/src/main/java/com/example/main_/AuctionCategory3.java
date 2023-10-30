package com.example.main_;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
// 디자인 카테고리
public class AuctionCategory3 extends AppCompatActivity {

    Auction auction = new Auction();

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
    String total;
    String end;
    public static String post_nick2;
    public static String post_choose2;
    public static String post_end2;
    private EditText et_search;
    String user_search;
    private AlertDialog dialog;
    public static JSONArray search_infolist; // 검색 게시글 정보
    public static int jsonSize5; // 검색 정보 게시글 수
    private EditText et_main_search;

    int save = 0; // 가격 합계
    int cnt = 0;

    public ArrayList<String>poster_id_List;
    public ArrayList<String>title_list;
    public ArrayList<String>startTime_list;
    public ArrayList<String>endTime_list;
    public ArrayList<String>content_list;
    public ArrayList<String>reply_id_list;
    public ArrayList<String>r_poster_id_list;
    public ArrayList<String>reply_content_list;
    //    public ArrayList<String>price_list_list;
    public ArrayList<String>parentCategory_list;
    public ArrayList<String>registrant_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_category3);

        MyAuction.check2 = "0";



        btn_move = findViewById(R.id.bt_auction_category_back);
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
                    Intent intent2 = new Intent(AuctionCategory3.this, Main.class);
                    startActivity(intent2); // 액티비티 이동
                }else if(Login.userL == 1) {
                    Intent intent2 = new Intent(AuctionCategory3.this, MainLogin.class);
                    startActivity(intent2); // 액티비티 이동
                }
            }
        });



        btn_move = findViewById(R.id.bt_scrap);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Login.userL == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AuctionCategory3.this);
                    dialog=builder.setMessage("로그인 후 사용할 수 있습니다..").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }else if(Login.userL == 1) {
                    Intent intent2 = new Intent(AuctionCategory3.this, MyScrap.class);
                    startActivity(intent2); // 액티비티 이동
                }
            }
        });

        btn_move = findViewById(R.id.bt_chat);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Login.userL == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AuctionCategory3.this);
                    dialog=builder.setMessage("로그인 후 사용할 수 있습니다..").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }else if(Login.userL == 1) {
                    Intent intent2 = new Intent(AuctionCategory3.this, Chat.class);
                    startActivity(intent2); // 액티비티 이동
                }
            }
        });

        btn_move = findViewById(R.id.bt_myPage);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Login.userL == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AuctionCategory3.this);
                    dialog=builder.setMessage("로그인 후 사용할 수 있습니다.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }else if(Login.userL == 1) {
                    Intent intent2 = new Intent(AuctionCategory3.this, MyPage.class);
                    startActivity(intent2); // 액티비티 이동
                }
            }
        });

        btn_move = findViewById(R.id.bt_auction);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Login.userL == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AuctionCategory3.this);
                    dialog=builder.setMessage("로그인 후 사용할 수 있습니다.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }else if(Login.userL == 1) {
                    Intent intent2 = new Intent(AuctionCategory3.this, Auction.class);
                    startActivity(intent2); // 액티비티 이동
                }
            }
        });

        btn_move = findViewById(R.id.bt_refresh);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent(AuctionCategory3.this, Refresh.class);
                startActivity(intent2); // 액티비티 이동
            }
        });


        btn_move = findViewById(R.id.bt_auction_it);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent(AuctionCategory3.this, AuctionCategory2.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_auction_video);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent(AuctionCategory3.this, AuctionCategory.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_auction_disign);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent2 = new Intent(AuctionCategory3.this, AuctionCategory3.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        et_main_search = (EditText)findViewById(R.id.et_ca3_search);
        et_main_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                user_search = et_main_search.getText().toString();
                String[] check = user_search.split("");
                int cnt = 0;
                for(int i = 0; i < check.length; i++) {
                    cnt++;
                }
                if(cnt < 2)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AuctionCategory3.this);
                    dialog=builder.setMessage("2글자 이상 검색해주세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return false;
                }



                if(user_search.isEmpty()) {
                    Toast.makeText(AuctionCategory3.this, "빈칸입니다.", Toast.LENGTH_SHORT).show();
                    v.clearFocus();
                    v.setFocusable(false);
                    v.setFocusableInTouchMode(true);
                    v.setFocusable(true);

                    return true;
                }

                Main.search_num = 6;

                switch (actionId) {
                    default:
                        Response.Listener<String> responseListener5 = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    System.out.println("검색결과~:"+response);
                                    JSONObject jsonObject = new JSONObject(response);
                                    search_infolist = jsonObject.getJSONArray("title_array");
                                    jsonSize5 = search_infolist.length();


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        SearchRequest searchRequest = new SearchRequest(user_search,responseListener5);
                        RequestQueue queue5 = Volley.newRequestQueue(AuctionCategory3.this);
                        queue5.add(searchRequest);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent2 = new Intent(AuctionCategory3.this, Search.class);
                                startActivity(intent2); // 액티비티 이동
                            }
                        }, 200);
                }

                v.clearFocus();
                v.setFocusable(false);
                v.setText("");
                v.setFocusableInTouchMode(true);
                v.setFocusable(true);

                return true;


            }
        });





        ListView listview ;
        ListViewAdapter adapter;
        // Adapter 생성
        adapter = new ListViewAdapter() ;
        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listView1);
        listview.setAdapter(adapter);


        if(Login.userL == 0) {
            for (int i = 0; i < Auction.jsonSize3; i++) {
                try {
                    JSONObject jsonObject = Auction.category_infoList3.getJSONObject(i);
//                    JSONObject jsonObject2 = Main.price_list.getJSONObject(i);
                    poster_id = jsonObject.getString("poster_id");
                    parentCategory = jsonObject.getString("parentCategory");
                    title = jsonObject.getString("title");
                    for(int j = 0; j < Auction.poster_id_List.size(); j++) {
                        if(poster_id.equals(Auction.poster_id_List.get(j))) {
                            total = "평균가: "+Auction.avg_list.get(j)+"원";
                            break;
                        }
                    }
                    content = jsonObject.getString("content");
                    startTime = jsonObject.getString("startTime");
                    endTime = jsonObject.getString("endTime");
                    registrant = jsonObject.getString("registrant");
                    end = jsonObject.getString("finish");

//                    System.out.println("----------111111111111111111111111111111111111111111111111111");
//                    System.out.println(parentCategory);





                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // 아이템 추가.
                adapter.addItem(
                        parentCategory, startTime, endTime, title, total, content, poster_id, registrant, end);
                adapter.notifyDataSetChanged();
            }
        }else if(Login.userL == 1) {
            for (int i = 0; i < Auction.jsonSize3; i++) {
                try {
                    JSONObject jsonObject = Auction.category_infoList3.getJSONObject(i);
//                    JSONObject jsonObject2 = Main.price_list.getJSONObject(i);
                    poster_id = jsonObject.getString("poster_id");
                    parentCategory = jsonObject.getString("parentCategory");
                    title = jsonObject.getString("title");
                    for(int j = 0; j < Auction.poster_id_List.size(); j++) {
                        if(poster_id.equals(Auction.poster_id_List.get(j))) {
                            total = "평균가: "+Auction.avg_list.get(j)+"원";
                            break;
                        }
                    }
                    content = jsonObject.getString("content");
                    startTime = jsonObject.getString("startTime");
                    endTime = jsonObject.getString("endTime");
                    registrant = jsonObject.getString("registrant");
                    end = jsonObject.getString("finish");




                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // 아이템 추가.
                adapter.addItem(
                        parentCategory, startTime, endTime, title, total, content, poster_id, registrant, end);
                adapter.notifyDataSetChanged();
            }
        }

        System.out.println("-----------------------------------------333333");
        System.out.println(r_poster_id);
        System.out.println("-----------------------------------------444444");



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
                Intent intent2 = new Intent(AuctionCategory3.this, PostCheck.class);

                // 액티비티 이동과 함께 값 넘겨주기
                intent2.putExtra("title",post_Title);
                intent2.putExtra("category",post_Category);
                intent2.putExtra("startDate",post_Date);
                intent2.putExtra("endDate",post_Date2);
//                intent2.putExtra("price", total);
                intent2.putExtra("detail", post_Detail);
                intent2.putExtra("nickName", post_nick);
//                intent2.putExtra("pick", post_choose);

                startActivity(intent2); // 액티비티 이동
            }
        });




    }
}