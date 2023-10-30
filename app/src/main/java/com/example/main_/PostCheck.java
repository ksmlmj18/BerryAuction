package com.example.main_;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class PostCheck extends AppCompatActivity {

    private Button btn_move;
    private Button btn_comment;
    private List<String> data;
    private AlertDialog dialog;
    private boolean validate=false;
    private EditText et_post_check_input;
    int userComment = 0;
    int poster_idList;
    int cnt = 0;
    int save = 0;
    String userComment2;
    String year, month, day;
    public static String nickName;
    String r_poster_id;
    String reply_id;
    String r_content;
    String regdate;
    String post_id2;
    public static String user_name2;
    public static String reply_id2;
    public static ArrayList<String> nickName_list;
    public static ArrayList<String> reply_id_list; // 현재 게시글의 댓글 아이디값만 저장

    public static ArrayList<String> nickName_list2 = new ArrayList<>();
    public static ArrayList<String> reply_id_list2 = new ArrayList<>();

    String user_nickName;
    String user_rating;
    String user_successNum;
    String user_career;
    ArrayList<String> user_nickName_list = new ArrayList<>();
    ArrayList<String> user_rating_list = new ArrayList<>();
    ArrayList<String> user_successNum_list = new ArrayList<>();
    ArrayList<String> user_career_list = new ArrayList<>();

    Date time = Calendar.getInstance().getTime();
    SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

    //뷰의 주소값을 담을 참조 변수
    TextView text1;


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight/3;// + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_check);
        et_post_check_input = findViewById(R.id.et_post_check_input);

        //id 속성이 text id값의 뷰의 주소 값을 얻어온다.
        TextView post_title = (TextView)findViewById(R.id.tv_post_check_title);
        TextView post_category = (TextView)findViewById(R.id.tv_post_check_category);
        TextView post_startDate = (TextView)findViewById(R.id.tv_post_check_startDate);
        TextView post_endDate = (TextView)findViewById(R.id.tv_post_check_endDate);
        TextView post_detail = (TextView)findViewById(R.id.tv_post_check_detail);
        TextView post_nick = (TextView)findViewById(R.id.tv_post_check_nick);

        // 보내온 Intent를 얻는다.
        Intent intent = getIntent();

        //새로운 문자열을 설정한다.
        post_title.setText(intent.getStringExtra("title"));
        post_category.setText(intent.getStringExtra("category"));
        post_startDate.setText(intent.getStringExtra("startDate"));
        post_endDate.setText(intent.getStringExtra("endDate"));
        post_detail.setText(intent.getStringExtra("detail"));
        post_nick.setText(intent.getStringExtra("nickName"));


        // 각각 년, 월, 일을 받는다.
//        String year = yearFormat.format(time);
//        String month = monthFormat.format(time);
//        String day = dayFormat.format(time);


        btn_comment = findViewById(R.id.bt_post_check_input);
        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Login.userL == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PostCheck.this);
                    dialog=builder.setMessage("로그인 후 사용할 수 있습니다..").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }

//                if(Auction.post_end2.equals("1") || MyAuction.post_end2.equals("1") || AuctionCategory.post_end2.equals("1") ||
//                AuctionCategory2.post_end2.equals("1") || AuctionCategory3.post_end2.equals("1") || MyComment.post_end2.equals("1")) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(PostCheck.this);
//                    dialog=builder.setMessage("종료된 게시물입니다.").setPositiveButton("확인", null).create();
//                    dialog.show();
//                    return;
//                }

                SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
                SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
                year = yearFormat.format(time);
                month = monthFormat.format(time);
                day = dayFormat.format(time);

                userComment2 = et_post_check_input.getText().toString();
                String[] check = userComment2.split("");

                if(validate)
                {
                    return;
                }

                if(userComment2.equals("") ){
                    AlertDialog.Builder builder = new AlertDialog.Builder(PostCheck.this);
                    dialog=builder.setMessage("공백입니다. 숫자를 입력해주세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }



                // 숫자만 입력 가능하게 하는 반복문
                String s= "";
                cnt = 0;
                for(int i = 0; i < check.length; i++) {
                    for(int k = 0; k < 10; k++) {
                        s = Integer.toString(k);
                        if(check[i].equals(s)) {
                            System.out.println(check[i]);
                            System.out.println(s);
                            cnt += 1;
                        }
                    }
                }

                if(cnt < check.length) {
                    System.out.println("----------------------------------------------");
                    System.out.println(cnt);
                    System.out.println(check.length);
                    AlertDialog.Builder builder = new AlertDialog.Builder(PostCheck.this);
                    dialog=builder.setMessage("숫자만 입력해주세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
//                            System.out.println("--------------------------------------------------------------------------11111");
                            JSONObject jsonObject = new JSONObject(response);
//                            System.out.println("--------------------------------------------------------------------------2222");
                            boolean success = jsonObject.getBoolean("success"); // 이 부분에서 php와 같은 키 값을 사용해야 함

                            if(success) {
                                Toast.makeText(getApplicationContext(), "댓글이 등록되었습니다..", Toast.LENGTH_SHORT).show();
                                finish();//인텐트 종료
                                overridePendingTransition(0, 0);//인텐트 효과 없애기
                                Intent intent = getIntent(); //인텐트
                                startActivity(intent); //액티비티 열기
                                overridePendingTransition(0, 0);//인텐트 효과 없애기

                            }else { //댓글 등록에 실패했을 경우
                                Toast.makeText(getApplicationContext(), "댓글 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };


                // 서버로 Volley를 이용해서 요청을 함
//                System.out.println(Auction.post_id2);
//                System.out.println(userComment2);
//                System.out.println(Login.userNick);
//                System.out.println(year);
                CommentRequest commentRequest = new CommentRequest(Auction.post_id2,userComment2,Login.userNick,year, month, day,responseListener);
                RequestQueue queue = Volley.newRequestQueue(PostCheck.this);
                queue.add(commentRequest);

//                System.out.println("-------------------------------------------------");
//                System.out.println(Auction.post_id2);
//                System.out.println(Auction.check);
//                System.out.println("-------------------------------------------------");
//                int tem2 = Integer.parseInt(Auction.post_id2);
//                System.out.println(tem2);
            }
        });



        btn_move = findViewById(R.id.bt_postCheck_back);
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
                    Intent intent2 = new Intent(PostCheck.this, Main.class);
                    startActivity(intent2); // 액티비티 이동
                }else if(Login.userL == 1) {
                    Intent intent2 = new Intent(PostCheck.this, MainLogin.class);
                    startActivity(intent2); // 액티비티 이동
                }
            }
        });

        btn_move = findViewById(R.id.bt_auction);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Login.userL == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PostCheck.this);
                    dialog=builder.setMessage("로그인 후 사용할 수 있습니다..").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }else if(Login.userL == 1) {
                    Intent intent2 = new Intent(PostCheck.this, Auction.class);
                    startActivity(intent2); // 액티비티 이동
                }
            }
        });

        btn_move = findViewById(R.id.bt_scrap);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Login.userL == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PostCheck.this);
                    dialog=builder.setMessage("로그인 후 사용할 수 있습니다..").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }else if(Login.userL == 1) {
                    Intent intent2 = new Intent(PostCheck.this, MyScrap.class);
                    startActivity(intent2); // 액티비티 이동
                }
            }
        });

        btn_move = findViewById(R.id.bt_chat);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Login.userL == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PostCheck.this);
                    dialog=builder.setMessage("로그인 후 사용할 수 있습니다..").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }else if(Login.userL == 1) {
                    Intent intent2 = new Intent(PostCheck.this, Chat.class);
                    startActivity(intent2); // 액티비티 이동
                }
            }
        });

        btn_move = findViewById(R.id.bt_myPage);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Login.userL == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PostCheck.this);
                    dialog=builder.setMessage("로그인 후 사용할 수 있습니다..").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }else if(Login.userL == 1) {
                    Intent intent2 = new Intent(PostCheck.this, MyPage.class);
                    startActivity(intent2); // 액티비티 이동
                }
            }
        });

        ListView listview ;
        ListViewAdapter2 adapter;
        // Adapter 생성
        adapter = new ListViewAdapter2() ;
        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listView1);
        listview.setAdapter(adapter);


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







        // 댓글
        if(Login.userL == 0) {
            for (int i = 0; i < Main.jsonSize3; i++) {
                try {
                    save = 0;
                    JSONObject jsonObject = Main.reply_infoList.getJSONObject(i);
                    nickName = jsonObject.getString("nickName");
                    r_poster_id = jsonObject.getString("r_poster_id");
                    reply_id = jsonObject.getString("reply_id");
                    r_content = jsonObject.getString("r_content");
                    regdate = jsonObject.getString("regdate");

//                    System.out.println("-------------------------88888888");
//                    System.out.println(r_poster_id);
//                    System.out.println(nickName);
//                    System.out.println(reply_id);
//                    System.out.println(regdate);
//                    System.out.println("-------------------------99999999");

                    for(int j = 0; j < Main.jsonSize4; j++) {
                        if(nickName.equals(user_nickName_list.get(j))) {
                            save = j;
                            break;
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // 아이템 추가.

                if (Auction.post_id2.equals(r_poster_id)) {
                    adapter.addItem(nickName, regdate, r_content, user_rating_list.get(save), user_successNum_list.get(save),
                            user_career_list.get(save), reply_id);
                    adapter.notifyDataSetChanged();
                }
            }
        }else if(Login.userL == 1) {
            nickName_list = new ArrayList<>();
            reply_id_list = new ArrayList<>();
            for (int i = 0; i < MainLogin.jsonSize3; i++) {
                try {
                    save = 0;
                    JSONObject jsonObject = MainLogin.reply_infoList.getJSONObject(i);
                    nickName = jsonObject.getString("nickName");
                    r_poster_id = jsonObject.getString("r_poster_id");
                    reply_id = jsonObject.getString("reply_id");
                    r_content = jsonObject.getString("r_content");
                    regdate = jsonObject.getString("regdate");

//                    System.out.println("-------------------------88888888");
//                    System.out.println(r_poster_id);
//                    System.out.println(nickName);
//                    System.out.println(reply_id);
//                    System.out.println(regdate);
//                    System.out.println("-------------------------99999999");


                    for(int j = 0; j < MainLogin.jsonSize4; j++) {
                        if(nickName.equals(user_nickName_list.get(j))) {
                            save = j;
                            break;
                        }
                    }
//                    reply_id_list.add(reply_id);

                    if(Auction.post_id2.equals(r_poster_id)) {
                        nickName_list2.add(nickName);
                        reply_id_list2.add(reply_id); // 현재 게시글의 댓글 아이디값만 저장


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // 아이템 추가.
                if (Auction.post_id2.equals(r_poster_id)) {
                    adapter.addItem(nickName, regdate, r_content,  user_rating_list.get(save), user_successNum_list.get(save),
                            user_career_list.get(save), reply_id);
                    adapter.notifyDataSetChanged();
                }
            }
        }


//        for(int i = 0; i < reply_id_list.size(); i++) {
//            System.out.println(reply_id_list.get(i));
//        }

        if(MyAuction.check2.equals("1")) {
            for (int i = 0; i < PostCheck.reply_id_list2.size(); i++) {
                if (MyAuction.post_choose2.equals(PostCheck.reply_id_list2.get(i))) {
                    nickName = PostCheck.nickName_list2.get(i);
                    System.out.println("nickName: " + nickName);
                    break;
                }
            }
        }



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                // 해당 아이템의 포지션 값 저장하기
                String user_Name = ((ListViewItem)adapter.getItem(i)).getName();
                user_name2 = user_Name; // 현재 들어간 게시물의 작성자 닉네임 저장
                System.out.println(user_Name);
                String user_Point = ((ListViewItem)adapter.getItem(i)).getPoint();
                String user_Num = ((ListViewItem)adapter.getItem(i)).getNum();
                String user_Detail = ((ListViewItem)adapter.getItem(i)).getDetail();
                String post_Id = ((ListViewItem)adapter.getItem(i)).getPostId();

                String reply_id = ((ListViewItem)adapter.getItem(i)).getReplyId();
//                reply_id2 = reply_id;

                // 이동할 액티비티 지정
                Intent intent2 = new Intent(PostCheck.this, UserInfo.class);

                // 액티비티 이동과 함께 값 넘겨주기
                intent2.putExtra("name",user_Name);
                intent2.putExtra("point",user_Point);
                intent2.putExtra("num",user_Num);
                intent2.putExtra("detail",user_Detail);

                startActivity(intent2); // 액티비티 이동
            }
        });

    }
}