package com.example.main_;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter5 extends BaseAdapter {
    private static final int ITEM_VIEW_TYPE_SENDER = 0 ;
    private static final int ITEM_VIEW_TYPE_GETTER = 1 ;
    private static final int ITEM_VIEW_TYPE_MAX = 2 ;
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    public static ArrayList<ListViewItem> items = new ArrayList<>();
    private int listCnt = 0;



    // ListViewAdapter5의 생성자
    public ListViewAdapter5() {

    }


    public ArrayList<ListViewItem> getList(){
        return items;
    }

//    public void clearList(ArrayList<ListViewItem> listData){
//        listData.clear();
//    }
//
//    public void updateList(ArrayList<ListViewItem> listData){
//        listViewItemList5 = listData;
//        listCnt = listViewItemList5.size();
//        this.notifyDataSetChanged();
//    }

    public void refresh(){
        Intent intent = new Intent(Chat.CONTEXT, Chat.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Chat.CONTEXT.startActivity(intent);
    }


    @Override
    public int getViewTypeCount() {
        return ITEM_VIEW_TYPE_MAX;
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }



    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return items.size() ;
    }


    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        int viewType = getItemViewType(position) ;

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
            ListViewItem listViewItem = items.get(position);

            switch (viewType) {
                case ITEM_VIEW_TYPE_SENDER:
                    convertView = inflater.inflate(R.layout.listview_item5, parent, false);
                    // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
                    TextView senderTextView = (TextView) convertView.findViewById(R.id.tv_item_sender);
                    TextView contentTextView1 = (TextView) convertView.findViewById(R.id.tv_item_senderContent);

                    // 아이템 내 각 위젯에 데이터 반영
                    senderTextView.setText(listViewItem.getCh_sender());
                    contentTextView1.setText(listViewItem.getCh_content1());
                    break;

                case ITEM_VIEW_TYPE_GETTER:
                    convertView = inflater.inflate(R.layout.listview_item6, parent, false);
                    // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
                    TextView getterTextView = (TextView) convertView.findViewById(R.id.tv_item_getter2);
                    TextView contentTextView2 = (TextView) convertView.findViewById(R.id.tv_item_getterContent);

                    getterTextView.setText(listViewItem.getCh_getter());
                    contentTextView2.setText(listViewItem.getCh_content2());
                    break;
            }
        }
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
        return items.get(position) ;
    }

//    public void clear(){
//        for(int i=0; i<getCount(); i++){
//            listViewItemList5.remove(i);
//        }
//    }

    public void clearitems(){
        items.clear();
    }


    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem_sender(String ch_sender, String ch_content1) {
        ListViewItem item = new ListViewItem();

        item.setType(ITEM_VIEW_TYPE_SENDER);
        item.setCh_sender(ch_sender);
        item.setCh_content1(ch_content1);

        items.add(item);
    }

    public void addItem_getter(String ch_getter, String ch_content2) {
        ListViewItem item = new ListViewItem();

        item.setType(ITEM_VIEW_TYPE_GETTER);
        item.setCh_getter(ch_getter);
        item.setCh_content2(ch_content2);

        items.add(item);
    }
}