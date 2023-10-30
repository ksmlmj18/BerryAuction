package com.example.main_;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import android.os.Bundle;
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

public class Auction extends AppCompatActivity {

    //뷰의 주소값을 담을 참조 변수
    private Button btn_move;
    private List<String> data;
    private AlertDialog dialog;
    static String parentCategory;
    static String poster_id;
    static String title;
    static String startTime;
    static String endTime;
    static String content;
    static String r_poster_id;
    static String reply_id;
    static String reply_content;
    static String post_id2 = "";
    static String post_nick2 = "";
    static String registrant;
    static String end;
    String nickName;
    String user_nickName;
    String user_rating;
    String user_successNum;
    String user_career;
    ArrayList<String> user_nickName_list = new ArrayList<>();
    ArrayList<String> user_rating_list = new ArrayList<>();
    ArrayList<String> user_successNum_list = new ArrayList<>();
    ArrayList<String> user_career_list = new ArrayList<>();

    static public JSONArray category_infoList; //영상
    static public int jsonSize; //영상
    static public JSONArray category_infoList2; //it
    static public int jsonSize2;//it
    static public JSONArray category_infoList3; //디자인
    static public int jsonSize3; //디자인
    static int itC = 0; //it 카테고리의 개수
    static int diC = 0; // 디자인 카테로리의 개수
    static int edC = 0; // 편집 카테고리의 개수
    static String check;
    String total;
    public static String post_choose2;
    public static String post_end2;
    public static JSONArray search_infolist; // 검색 게시글 정보
    public static int jsonSize5; // 검색 정보 게시글 수
    String user_search;
    private EditText et_main_search;

    //    String price_list;
    static ArrayList<String>poster_id_List = new ArrayList<>();
//    public ArrayList<String>title_list;
//    public ArrayList<String>startTime_list;
//    public ArrayList<String>endTime_list;
//    public ArrayList<String>content_list;
//    public ArrayList<String>reply_id_list;
//    public ArrayList<String>r_poster_id_list;
//    public ArrayList<String>reply_content_list;
//    public ArrayList<String>registrant_list;
//    //    public ArrayList<String>price_list_list;
//    public ArrayList<String>parentCategory_list;

    static ArrayList<String> nickName_list = new ArrayList<>();
    static ArrayList<String> r_poster_id_list = new ArrayList<>();
    static ArrayList<String> reply_id_list = new ArrayList<>();
    static ArrayList<String> content_list = new ArrayList<>();
    static ArrayList<String> avg_list = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction);



        poster_id_List = new ArrayList<>();
//        title_list = new ArrayList<>();
//        startTime_list = new ArrayList<>();
//        endTime_list = new ArrayList<>();
//        r_poster_id_list = new ArrayList<>();
//        reply_id_list = new ArrayList<>();
//        reply_content_list = new ArrayList<>();
////        price_list_list = new ArrayList<>();
//        parentCategory_list = new ArrayList<>(
//
//        );

        ListView listview ;
        ListViewAdapter adapter;
        // Adapter 생성
        adapter = new ListViewAdapter() ;
        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listView1);
        listview.setAdapter(adapter);


        MyAuction.check2 = "0";



        btn_move = findViewById(R.id.bt_auction_back);
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
                    Intent intent2 = new Intent(Auction.this, Main.class);
                    startActivity(intent2); // 액티비티 이동
                }else if(Login.userL == 1) {
                    Intent intent2 = new Intent(Auction.this, MainLogin.class);
                    startActivity(intent2); // 액티비티 이동
                }
            }
        });



        btn_move = findViewById(R.id.bt_scrap);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Login.userL == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Auction.this);
                    dialog=builder.setMessage("로그인 후 사용할 수 있습니다..").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }else if(Login.userL == 1) {
                    Intent intent2 = new Intent(Auction.this, MyScrap.class);
                    startActivity(intent2); // 액티비티 이동
                }
            }
        });

        btn_move = findViewById(R.id.bt_chat);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Login.userL == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Auction.this);
                    dialog=builder.setMessage("로그인 후 사용할 수 있습니다..").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }else if(Login.userL == 1) {
                    Intent intent2 = new Intent(Auction.this, Chat.class);
                    startActivity(intent2); // 액티비티 이동
                }
            }
        });

        btn_move = findViewById(R.id.bt_myPage);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Login.userL == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Auction.this);
                    dialog=builder.setMessage("로그인 후 사용할 수 있습니다.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }else if(Login.userL == 1) {
                    Intent intent2 = new Intent(Auction.this, MyPage.class);
                    startActivity(intent2); // 액티비티 이동
                }
            }
        });

        btn_move = findViewById(R.id.bt_auction_it);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent(Auction.this, AuctionCategory2.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_auction_video);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent(Auction.this, AuctionCategory.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_auction_disign);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent2 = new Intent(Auction.this, AuctionCategory3.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        btn_move = findViewById(R.id.bt_refresh);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent(Auction.this, Refresh.class);
                startActivity(intent2); // 액티비티 이동
            }
        });

        et_main_search = (EditText)findViewById(R.id.et_auction_search);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(Auction.this);
                    dialog=builder.setMessage("2글자 이상 검색해주세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return false;
                }



                if(user_search.isEmpty()) {
                    Toast.makeText(Auction.this, "빈칸입니다.", Toast.LENGTH_SHORT).show();
                    v.clearFocus();
                    v.setFocusable(false);
                    v.setFocusableInTouchMode(true);
                    v.setFocusable(true);

                    return true;
                }
                Main.search_num = 3;

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
                        RequestQueue queue5 = Volley.newRequestQueue(Auction.this);
                        queue5.add(searchRequest);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent2 = new Intent(Auction.this, Search.class);
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



        if(Login.userL == 1) {
            for (int i = 0; i < MainLogin.jsonSize; i++) {
                try {
                    JSONObject jsonObject = MainLogin.poster_infoList.getJSONObject(i);
                    poster_id = jsonObject.getString("poster_id");

                    poster_id_List.add(poster_id);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }else if(Login.userL == 0) {
            for (int i = 0; i < Main.jsonSize; i++) {
                try {
                    JSONObject jsonObject = Main.poster_infoList.getJSONObject(i);
                    poster_id = jsonObject.getString("poster_id");

                    poster_id_List.add(poster_id);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

//        for(int i = 0; i < poster_id_List.size(); i++) {
//            System.out.println(poster_id_List.get(i));
//        }

        //유저 정보 저장
        for(int j = 0; j < Main.jsonSize4; j++) {
            try {
                JSONObject jsonObject3 = Main.rating_array.getJSONObject(j);
                user_nickName = jsonObject3.getString("user_nickName");
                user_rating = jsonObject3.getString("user_rating");
                user_successNum = jsonObject3.getString("user_successNum");
                user_career = jsonObject3.getString("user_career");

                user_nickName_list.add(user_nickName);
                user_rating_list.add(user_rating);
                user_successNum_list.add(user_successNum);
                user_career_list.add(user_career);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(Login.userL == 0) {
            // 전체 댓글 리스트에 저장
            for (int i = 0; i < Main.jsonSize3; i++) {
                try {
                    JSONObject jsonObject2 = Main.reply_infoList.getJSONObject(i);
                    nickName = jsonObject2.getString("nickName");
                    r_poster_id = jsonObject2.getString("r_poster_id");
                    reply_id = jsonObject2.getString("reply_id");
                    content = jsonObject2.getString("r_content");
//                    System.out.println(content);

                    nickName_list.add(nickName);
                    r_poster_id_list.add(r_poster_id);
                    reply_id_list.add(reply_id);
                    content_list.add(content);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Main.jsonSize:" +Main.jsonSize);
            System.out.println("poster_id_List.size(): "+poster_id_List.size());


            //평균 가격가를 구하기 위한 연산
            for (int i = 0; i < poster_id_List.size(); i++) {
                int cnt = 0;
                int save = 0;
                int avg = 0;
                for (int j = 0; j < r_poster_id_list.size(); j++) {
                    if (poster_id_List.get(i).equals(r_poster_id_list.get(j))) {
                        save += Integer.parseInt(content_list.get(j));
//                        System.out.println("j: "+j);
//                        System.out.println(save);
                        cnt++;
                    }
                }
                if(save > 0) {
                    avg = save / cnt;
//                    System.out.println("i: "+avg);
//                    System.out.println("save: "+save+",  "+i);
//                    System.out.println("cnt: "+cnt+",  "+i);
//                    System.out.println("avg: "+avg+",  "+i);
                    avg_list.add(Integer.toString(avg));
                }else if(save == 0) {
//                    System.out.println("i: "+0);
                    avg_list.add("0");
                }
            }
        }else if(Login.userL == 1) {
            // 전체 댓글 리스트에 저장
            for (int i = 0; i < MainLogin.jsonSize3; i++) {
                try {
                    JSONObject jsonObject2 = MainLogin.reply_infoList.getJSONObject(i);
                    nickName = jsonObject2.getString("nickName");
                    r_poster_id = jsonObject2.getString("r_poster_id");
                    reply_id = jsonObject2.getString("reply_id");
                    content = jsonObject2.getString("r_content");

                    nickName_list.add(nickName);
                    r_poster_id_list.add(r_poster_id);
                    reply_id_list.add(reply_id);
                    content_list.add(content);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //평균 가격가를 구하기 위한 연산
            for (int i = 0; i < poster_id_List.size(); i++) {
                int cnt = 0;
                int save = 0;
                int avg = 0;
                for (int j = 0; j < r_poster_id_list.size(); j++) {
                    if (poster_id_List.get(i).equals(r_poster_id_list.get(j))) {
                        save += Integer.parseInt(content_list.get(j));
//                        System.out.println("j: "+j);
//                        System.out.println(save);
                        cnt++;
                    }
                }
                if(save > 0) {
                    avg = save / cnt;
//                    System.out.println("i: "+avg);
//                    System.out.println("save: "+save+",  "+i);
//                    System.out.println("cnt: "+cnt+",  "+i);
//                    System.out.println("avg: "+avg+",  "+i);
                    avg_list.add(Integer.toString(avg));
                }else if(save == 0) {
//                    System.out.println("i: "+0);
                    avg_list.add("0");
                }
            }
        }

//        for(int i = 0; i < Main.jsonSize-1; i++) {
//            System.out.println("---------------------------------aaaaaaaaaaaaa");
//            System.out.println(r_poster_id_list.get(i));
//            System.out.println(avg_list.get(i));
//            System.out.println("개수: "+i);
//            System.out.println("---------------------------------bbbbbbbbbbbbbbb");
//        }


        if(Login.userL == 0) {
            for (int i = 0; i < Main.jsonSize; i++) {
                try {
                    JSONObject jsonObject = Main.poster_infoList.getJSONObject(i);
//                    JSONObject jsonObject2 = Main.price_list.getJSONObject(i);
                    poster_id = jsonObject.getString("poster_id");
                    parentCategory = jsonObject.getString("parentCategory");
                    title = jsonObject.getString("title");
                    content = jsonObject.getString("content");
                    startTime = jsonObject.getString("startTime");
                    endTime = jsonObject.getString("endTime");
                    registrant = jsonObject.getString("registrant");
                    String ch = jsonObject.getString("choose");
                    end = jsonObject.getString("finish");

//                    System.out.println("=================================");
//                    System.out.println(poster_id);
//                    poster_id_List.add(poster_id);

                    total = "평균가: "+avg_list.get(i)+"원";



                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // 아이템 추가.
                adapter.addItem(
                        parentCategory, startTime, endTime, title, total, content, poster_id, registrant, end);
                adapter.notifyDataSetChanged();
            }
        }else if(Login.userL == 1) {
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
                    String ch = jsonObject.getString("choose");
                    end = jsonObject.getString("finish");

//                    poster_id_List.add(poster_id);

                    total = "평균가: "+avg_list.get(i)+"원";


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // 아이템 추가.
                adapter.addItem(
                        parentCategory, startTime, endTime, title, total, content, poster_id, registrant, end);
                adapter.notifyDataSetChanged();
            }
        }

//        System.out.println("-----------------------------------------333333");
//        System.out.println(r_poster_id);
//        System.out.println("-----------------------------------------444444");



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
                post_id2 = post_id; // 내가 선택한 게시글의 키값
//                System.out.println("###################################3");
//                System.out.println(post_id);
//                System.out.println(post_id2);
                String post_nick = ((ListViewItem)adapter.getItem(i)).getNick();
                post_nick2 = post_nick; // 내가 선택한 게시글의 작성자의 닉네임
                String post_choose = ((ListViewItem)adapter.getItem(i)).getPick();
                post_choose2 = post_choose;
                System.out.println(post_choose);
                System.out.println(post_choose2);
                String post_end = ((ListViewItem)adapter.getItem(i)).getEnd();
                post_end2 = post_end;
//                System.out.println(post_nick);
//                System.out.println(post_nick2);
//                System.out.println(Login.userNick);


//                String post_Price = ((ListViewItem)adapter.getItem(i)).getPrice();

                // 이동할 액티비티 지정
                Intent intent2 = new Intent(Auction.this, PostCheck.class);

                // 액티비티 이동과 함께 값 넘겨주기
                intent2.putExtra("title",post_Title);
                intent2.putExtra("category",post_Category);
                intent2.putExtra("startDate",post_Date);
                intent2.putExtra("endDate",post_Date2);
                intent2.putExtra("price", total);
                intent2.putExtra("detail", post_Detail);
                intent2.putExtra("nickName", post_nick);

                startActivity(intent2); // 액티비티 이동
            }
        });

        //영상
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    category_infoList = jsonObject.getJSONArray("poster_infoList");
                    jsonSize = category_infoList.length();

                    JSONObject jsonObject2 = category_infoList.getJSONObject(0);

                    System.out.println(category_infoList.get(0));
                    String c = jsonObject2.getString("startTime");
                    System.out.println(c);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        CategoryRequest categoryRequest = new CategoryRequest("영상",responseListener);
        RequestQueue queue = Volley.newRequestQueue(Auction.this);
        queue.add(categoryRequest);

        //it
        Response.Listener<String> responseListener2 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    category_infoList2 = jsonObject.getJSONArray("poster_infoList");
                    jsonSize2 = category_infoList2.length();

                    JSONObject jsonObject2 = category_infoList2.getJSONObject(0);

                    System.out.println(category_infoList2.get(0));
                    String c = jsonObject2.getString("content");
                    System.out.println(c);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        CategoryRequest2 categoryRequest2 = new CategoryRequest2("ITㆍ프로그래밍",responseListener2);
        RequestQueue queue2 = Volley.newRequestQueue(Auction.this);
        queue2.add(categoryRequest2);




        // 디자인
        Response.Listener<String> responseListener3 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
//                    System.out.println(response);
                    JSONObject jsonObject = new JSONObject(response);
                    category_infoList3 = jsonObject.getJSONArray("poster_infoList");
                    jsonSize3 = category_infoList3.length();

                    JSONObject jsonObject2 = category_infoList3.getJSONObject(0);

//                    System.out.println(category_infoList3.get(0));
//                    String c = jsonObject2.getString("title");
//                    System.out.println(c);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        CategoryRequest3 categoryRequest3 = new CategoryRequest3("디자인",responseListener3);
        RequestQueue queue3 = Volley.newRequestQueue(Auction.this);
        queue3.add(categoryRequest3);



    }
}