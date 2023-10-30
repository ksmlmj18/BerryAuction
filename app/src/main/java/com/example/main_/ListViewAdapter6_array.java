//package com.example.main_;
//
//import java.util.List;
//
//import android.content.Context;
//import android.util.SparseBooleanArray;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//public class ListViewAdapter6_array extends ArrayAdapter<ListViewItem> {
//    private static final int ITEM_VIEW_TYPE_SENDER = 0 ;
//    private static final int ITEM_VIEW_TYPE_GETTER = 1 ;
//    private static final int ITEM_VIEW_TYPE_MAX = 2 ;
//
//    Context myContext;
//    LayoutInflater inflater;
//    List<ListViewItem> listViewItemList5;
//    private SparseBooleanArray mSelectedItemsIds;
//
//    //context 와 arrayList를 얻기위한 생성자
//    public ListViewAdapter6_array(Context context, int resourceId, List<ListViewItem> lists) {
//        super(context, resourceId, lists);
//        mSelectedItemsIds = new SparseBooleanArray();
//        myContext = context;
//        listViewItemList5 = lists;
//        inflater = LayoutInflater.from(context);
//    }
//
//    //아이템 컨테이너 클래스
//    private class ViewHolder {
//        TextView senderTextView;
//        TextView contentTextView1;
//        TextView getterTextView;
//        TextView contentTextView2;
////        ImageView imageView;
//    }
//
//    public View getView(int position, View view, ViewGroup parent) {
//        final ViewHolder holder;
//        int viewType = getItemViewType(position) ;
//
//        if (view == null) {
//            holder = new ViewHolder();
//
//
//            switch(viewType){
//                case ITEM_VIEW_TYPE_SENDER:
//                    view = inflater.inflate(R.layout.listview_item5, null);
//
//                    //item.xml에서 textview를 선택.
//                    holder.senderTextView = (TextView) view.findViewById(R.id.tv_item_sender);
//                    holder.contentTextView1 = (TextView) view.findViewById(R.id.tv_item_senderContent);
//
//                    //배열중에 해당 position값의 정보를 textView에 출력
//                    holder.senderTextView.setText(listViewItemList5.get(position).toString());
//                    holder.contentTextView1.setText(listViewItemList5.get(position).toString());
//                    break;
//
//                case ITEM_VIEW_TYPE_GETTER:
//                    view = inflater.inflate(R.layout.listview_item6, null);
//
//                    //배열중에 해당 position값의 정보를 textView에 출력
//                    holder.getterTextView.setText(listViewItemList5.get(position).toString());
//                    holder.contentTextView2.setText(listViewItemList5.get(position).toString());
//                    break;
//            }
//
//
//            //item.xml에서 imageView를 선택.
////            holder.imageView = (ImageView) view.findViewById(R.id.imageView);
//
//            view.setTag(holder);
//        } else {
//            holder = (ViewHolder) view.getTag();
//        }
//
//
//
//        //배열중에 해당 position값의 정보를 imageView에 출력
////        holder.imageView.setImageResource(R.mipmap.ic_launcher);
//        return view;
//    }
//
//    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
//    public void addItem_sender(String ch_sender, String ch_content1) {
//        ListViewItem item = new ListViewItem();
//
//        item.setType(ITEM_VIEW_TYPE_SENDER);
//        item.setCh_sender(ch_sender);
//        item.setCh_content1(ch_content1);
//
//        listViewItemList5.add(item);
//    }
//
//    public void addItem_getter(String ch_getter, String ch_content2) {
//        ListViewItem item = new ListViewItem();
//
//        item.setType(ITEM_VIEW_TYPE_GETTER);
//        item.setCh_getter(ch_getter);
//        item.setCh_content2(ch_content2);
//
//        listViewItemList5.add(item);
//    }
//
//
//    public void remove(String object) {
//        listViewItemList5.remove(object);
//        notifyDataSetChanged();
//    }
//
//    //업데이트 또는 삭제 후 목록 가져 오기
//    public List<String> getMyList() {
//        return listViewItemList5;
//    }
//
//    public void toggleSelection(int position) {
//        selectView(position, !mSelectedItemsIds.get(position));
//    }
//
//    //선택 해제 후 선택 제거
//    public void removeSelection() {
//        mSelectedItemsIds = new SparseBooleanArray();
//        notifyDataSetChanged();
//    }
//
//    //선택시 체크된 된 항목
//    public void selectView(int position, boolean value) {
//        if (value)
//            mSelectedItemsIds.put(position, value);
//        else
//            mSelectedItemsIds.delete(position);
//        notifyDataSetChanged();
//    }
//
//    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
//    @Override
//    public int getCount() {
//        return listViewItemList5.size() ;
//    }
//
//    public SparseBooleanArray getSelectedIds() {
//        return mSelectedItemsIds;
//    }
//}