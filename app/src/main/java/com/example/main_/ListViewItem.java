package com.example.main_;

import android.graphics.drawable.Drawable;

public class ListViewItem {
    private Drawable iconDrawable ;
    private String titleStr ;
    private String dateStr ;
    private String date2Str ;
    private String priceStr ;
    private String detailStr ;
    private String pickStr ;
    private String categoryStr ;
    private String nameStr;
    private String pointStr;
    private String numStr;
    private String postIdStr;
    private String nickStr;

    private String ReplyIdStr;
    private String EndStr;

    // -----------------------------------
    private String chat_id;
    private String sender;
    private String getter;
    private String chat_content;
    private String chat_day;
    private String chat_time;

    // 채팅방 내역 정보 set(ch_sender, ch_getter, ch_content)
    private String ch_sender;
    private String ch_getter;
    private String ch_content1;
    private String ch_content2;

    private int type ;

    public void setType(int type) {
        this.type = type ;
    }
    public int getType() {
        return this.type ;
    }





    public void setIcon(Drawable icon) {iconDrawable = icon ;}
    public void setTitle(String title) {titleStr = title ;}
    public void setDate(String date) {dateStr = date ;}
    public void setDate2(String date2) {date2Str = date2 ;}
    public void setDetail(String detail) {detailStr = detail;}
    public void setPrice(String price) {priceStr = price ;}
    public void setPick(String pick) {pickStr = pick ;}
    public void setCategory(String category) {categoryStr = category;}
    public void setName(String name) {nameStr = name;}
    public void setPoint(String point) {pointStr = point;}
    public void setNum(String num) {numStr = num;}
    public void setPostId(String postId) {postIdStr = postId;}
    public void setNick(String nick) {nickStr = nick;}
    public void setReplyId(String replyId) {ReplyIdStr = replyId;}
    public void setEnd(String end) {EndStr = end;}

    public Drawable getIcon() {return this.iconDrawable ;}
    public String getTitle() {return this.titleStr;}
    public String getName() {return this.nameStr;}
    public String getDate() {return this.dateStr;}
    public String getDate2() {return this.date2Str;}
    public String getDetail() {return this.detailStr;}
    public String getPrice() {return this.priceStr;}
    public String getPick() {return this.pickStr;}
    public String getCategory() {return this.categoryStr;}
    public String getPoint() {return this.pointStr;}
    public String getNum() {return this.numStr;}
    public String getPostId() {return this.postIdStr;}
    public String getNick() {return this.nickStr;}
    public String getReplyId(){return this.ReplyIdStr;}
    public  String getEnd() {return  this.EndStr;}






    // < 채팅방 목록 item >
    // 채팅방 정보 set(chat_id, sender, getter, chat_content, chat_day, chat_time)
    public void setChat_id(String chat_id) {this.chat_id = chat_id;}
    public void setChat_sender(String sender) {this.sender = sender;}
    public void setChat_getter(String getter) {this.getter = getter;}
    public void setChat_content(String chat_content) {this.chat_content = chat_content;}
    public void setChat_day(String chat_day) {this.chat_day = chat_day;}
    public void setChat_time(String chat_time) {this.chat_time = chat_time;}

    // 채팅방 정보 get(chat_id, sender, getter, chat_content, chat_day, chat_time)
    public String getChat_id() {return this.chat_id;}
    public String getChat_sender() {return this.sender;}
    public String getChat_getter() {return this.getter;}
    public String getChat_content() {return this.chat_content;}
    public String getChat_day() {return this.chat_day;}
    public String getChat_time() {return this.chat_time;}


    // < 채팅방 안에 채팅 내역 item >
    // 채팅방 내역 정보 set(ch_sender, ch_getter, ch_content)
    public void setCh_sender(String ch_sender) {this.ch_sender = ch_sender;}
    public void setCh_getter(String ch_getter) {this.ch_getter = ch_getter;}
    public void setCh_content1(String ch_content1) {this.ch_content1 = ch_content1;}
    public void setCh_content2(String ch_content2) {this.ch_content2 = ch_content2;}

    // 채팅방 내역 정보 get(ch_sender, ch_getter, ch_content)
    public String getCh_sender() {return this.ch_sender;}
    public String getCh_getter() {return this.ch_getter;}
    public String getCh_content1() {return this.ch_content1;}
    public String getCh_content2() {return this.ch_content2;}
}