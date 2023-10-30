package com.example.main_;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListViewAdapter4 extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewItem> listViewItemList4 = new ArrayList<ListViewItem>() ;
    private AlertDialog dialog;
    public static String nickName;

    // ListViewAdapter의 생성자
    public ListViewAdapter4() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList4.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item4, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView titleTextView = (TextView) convertView.findViewById(R.id.tv_item_category) ;
        TextView dateTextView = (TextView) convertView.findViewById(R.id.tv_item_date) ;
        TextView date2TextView = (TextView) convertView.findViewById(R.id.tv_item_date2) ;
        TextView priceTextView = (TextView) convertView.findViewById(R.id.tv_item_price) ;
        TextView pickTextView = (TextView) convertView.findViewById(R.id.tv_item_pick) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItemList4.get(position);

        Button btn = convertView.findViewById(R.id.bt_item_finish);

        if(MyAuction.porter_end_list.get(pos).equals("1")) {
            btn.setBackgroundColor(Color.GRAY);
        }



        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // 버튼 입력시 이벤트 할당


                for(int i = 0; i < MyAuction.nickName_list.size(); i++) {
                    System.out.println(MyAuction.nickName_list.get(i));
                }

                for(int i = 0; i < MyAuction.nickName_list.size(); i++) {
                    if(MyAuction.choose_list.get(pos).equals(MyAuction.reply_id_list.get(i))) {
                        nickName = MyAuction.nickName_list.get(i);
                        System.out.println("nickName: "+nickName);
                        break;
                    }
                }


                System.out.println("end????: "+MyAuction.porter_end_list.get(pos));
                if(MyAuction.porter_end_list.get(pos).equals("1")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    dialog=builder.setMessage("이미 종료되었습니다.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }

                // 종료 재확인 다이얼로그
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("확인"); //다이얼로그 제목
                builder.setMessage("정말 게시물을 마감 하시겠습니까?"); //다이얼로그 내용

                // 다이얼로그 (예)
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
//                            System.out.println(response);
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success"); // 이 부분에서 php와 같은 키 값을 사용해야 함

                                    if (success) {
                                        //채택된경우 m
                                        if (!MyAuction.choose_list.get(pos).equals("0")) {
                                            Intent intent2 = new Intent(context, Point.class);
                                            intent2.putExtra("nickName", nickName);
                                            context.startActivity(intent2); // 액티비티 이동
                                        }
                                        //채택안된경우
                                        Toast.makeText(context, "종료되었습니다.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, "실패.", Toast.LENGTH_SHORT).show();
                                    }
                                    btn.setBackgroundColor(Color.GRAY);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        };


                        EndRequest endRequest = new EndRequest(MyAuction.poster_id_List.get(pos), responseListener);
                        RequestQueue queue = Volley.newRequestQueue(context);
                        queue.add(endRequest);
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
//                Toast.makeText(context,pos+"정말 완료하시겠습니까?",Toast.LENGTH_SHORT).show();
            }

        });

        // 아이템 내 각 위젯에 데이터 반영
        titleTextView.setText(listViewItem.getTitle());
        dateTextView.setText(listViewItem.getDate());
        date2TextView.setText(listViewItem.getDate2());
        priceTextView.setText(listViewItem.getPrice());
        pickTextView.setText(listViewItem.getPick());

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
        return listViewItemList4.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String category,  String date,String date2, String title, String price, String detail, String post_id, String nick, String pick, String end) {
        ListViewItem item = new ListViewItem();

//        item.setIcon(icon);
        item.setCategory(category);
        item.setTitle(title);
        item.setDetail(detail);
        item.setDate(date);
        item.setDate2(date2);
        item.setPrice(price);
        item.setPostId(post_id);
        item.setNick(nick);
        item.setPick(pick);
        item.setEnd(end);


        listViewItemList4.add(item);
    }
}