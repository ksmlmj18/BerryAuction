package com.example.main_;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListViewAdapter2 extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewItem> listViewItemList2 = new ArrayList<ListViewItem>() ;
    private AlertDialog dialog;

    ArrayList<String> reply_id_list = new ArrayList<>();
    // ListViewAdapter의 생성자
    public ListViewAdapter2() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList2.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();



        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item2, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView nameTextView = (TextView) convertView.findViewById(R.id.tv_item_category);
        TextView dateTextView = (TextView) convertView.findViewById(R.id.tv_item_date) ;
        TextView priceTextView = (TextView) convertView.findViewById(R.id.tv_item_price) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItemList2.get(position);



        Button btn = convertView.findViewById(R.id.bt_item_click);

//        if(MyAuction.post_choose2.equals("채택완료")) {
//            btn.setBackgroundColor(Color.GRAY);
//        }


        if(Login.userNick.equals(Auction.post_nick2) || Login.userNick.equals(MyAuction.post_nick2) ){
            MyAuction.check2 = "1";
        }
        if(!MyAuction.check2.equals("1")){
            btn.setVisibility(View.INVISIBLE);
        }
        View finalConvertView = convertView;
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (Login.userL == 1) {
                    // 버튼 입력시 이벤트 할당
                    if (MyAuction.post_choose2.equals("채택완료")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        dialog = builder.setMessage("이미 채택이 완료되었습니다.").setPositiveButton("확인", null).create();
                        dialog.show();
                        return;
                    }

//                    if(Login.userNick.equals(Auction.post_nick2) || Login.userNick.equals(MyAuction.post_nick2) ||
//                            Login.userNick.equals(AuctionCategory.post_nick2) || Login.userNick.equals(AuctionCategory2.post_nick2) ||
//                            Login.userNick.equals(AuctionCategory3.post_nick2) || Login.userNick.equals(MyComment.post_nick2)) {

                    // 채택 재확인 다이얼로그
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("확인"); //다이얼로그 제목
                    builder.setMessage("정말 채택하시겠습니까?"); //다이얼로그 내용

                    // 다이얼로그 (예)
                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        System.out.println(response);
                                        JSONObject jsonObject = new JSONObject(response);
                                        boolean success = jsonObject.getBoolean("success"); // 이 부분에서 php와 같은 키 값을 사용해야 함

                                        if (success) {
                                            Toast.makeText(context, PostCheck.reply_id_list2.get(pos) + "채택되었습니다.", Toast.LENGTH_SHORT).show();

                                            btn.setBackgroundColor(Color.GRAY);
                                            Intent intent2 = new Intent(context, MainLogin.class);
                                            context.startActivity(intent2); // 액티비티 이동

                                        } else {
                                            Toast.makeText(context, "실패.", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            };

                            AdoptRequest adoptRequest = new AdoptRequest(PostCheck.reply_id_list2.get(pos), responseListener);
                            RequestQueue queue = Volley.newRequestQueue(context);
                            queue.add(adoptRequest);
                        }

                    });

                    // 다이얼로그 (아니오)
                    builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    });
                    builder.create().show();
                }
            }
        });

        // 아이템 내 각 위젯에 데이터 반영
        nameTextView.setText(listViewItem.getName());
        dateTextView.setText(listViewItem.getDate());
        priceTextView.setText(listViewItem.getPrice());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList2.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String name, String date, String price, String point ,String num, String detail, String replyId) {
        ListViewItem item = new ListViewItem();

        item.setName(name);
        item.setDate(date);
        item.setPrice(price);
        item.setPoint(point);
        item.setNum(num);
        item.setDetail(detail);
        item.setReplyId(replyId);
        listViewItemList2.add(item);
    }
}