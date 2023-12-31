package com.example.main_;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.main_.ListViewItem;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;

    // ListViewAdapter의 생성자
    public ListViewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView categoryTextView = (TextView) convertView.findViewById(R.id.tv_item_category);
        TextView titleTextView = (TextView) convertView.findViewById(R.id.tv_item_title) ;
        TextView dateTextView = (TextView) convertView.findViewById(R.id.tv_item_date) ;
        TextView date2TextView = (TextView) convertView.findViewById(R.id.tv_item_date2) ;
        TextView priceTextView = (TextView) convertView.findViewById(R.id.tv_item_price) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItemList.get(position);

        Button btn = convertView.findViewById(R.id.bt_item_finish);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // 버튼 입력시 이벤트 할당

                Toast.makeText(context,pos+"번째게시판이 스크랩되었습니다.",Toast.LENGTH_SHORT).show();
            }

        });

        // 아이템 내 각 위젯에 데이터 반영
        titleTextView.setText(listViewItem.getTitle());
        dateTextView.setText(listViewItem.getDate());
        priceTextView.setText(listViewItem.getPrice());
        categoryTextView.setText((listViewItem.getCategory()));
        date2TextView.setText(listViewItem.getDate2());

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
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String category,  String date,String date2, String title, String price, String detail, String post_id, String nick, String end) {
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
        item.setEnd(end);


        listViewItemList.add(item);
    }
}