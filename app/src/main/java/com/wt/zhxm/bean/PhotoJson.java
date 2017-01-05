package com.wt.zhxm.bean;

import java.util.List;

/**
 * @author wtt
 * 组图
 */
public class PhotoJson {
	
	private boolean ret;

	private int errcode;

	private String errmsg;

	private int ver;

	private Data data;

	public void setRet(boolean ret){
	this.ret = ret;
	}
	public boolean getRet(){
	return this.ret;
	}
	public void setErrcode(int errcode){
	this.errcode = errcode;
	}
	public int getErrcode(){
	return this.errcode;
	}
	public void setErrmsg(String errmsg){
	this.errmsg = errmsg;
	}
	public String getErrmsg(){
	return this.errmsg;
	}
	public void setVer(int ver){
	this.ver = ver;
	}
	public int getVer(){
	return this.ver;
	}
	public void setData(Data data){
	this.data = data;
	}
	public Data getData(){
	return this.data;
	}

	public class Books {
		private String bookUrl;

		private String title;

		private String headImage;

		private String userName;

		private String userHeadImg;

		private String startTime;

		private int routeDays;

		private int bookImgNum;

		private int viewCount;

		private int likeCount;

		private int commentCount;

		private String text;

		private boolean elite;

		public void setBookUrl(String bookUrl){
		this.bookUrl = bookUrl;
		}
		public String getBookUrl(){
		return this.bookUrl;
		}
		public void setTitle(String title){
		this.title = title;
		}
		public String getTitle(){
		return this.title;
		}
		public void setHeadImage(String headImage){
		this.headImage = headImage;
		}
		public String getHeadImage(){
		return this.headImage;
		}
		public void setUserName(String userName){
		this.userName = userName;
		}
		public String getUserName(){
		return this.userName;
		}
		public void setUserHeadImg(String userHeadImg){
		this.userHeadImg = userHeadImg;
		}
		public String getUserHeadImg(){
		return this.userHeadImg;
		}
		public void setStartTime(String startTime){
		this.startTime = startTime;
		}
		public String getStartTime(){
		return this.startTime;
		}
		public void setRouteDays(int routeDays){
		this.routeDays = routeDays;
		}
		public int getRouteDays(){
		return this.routeDays;
		}
		public void setBookImgNum(int bookImgNum){
		this.bookImgNum = bookImgNum;
		}
		public int getBookImgNum(){
		return this.bookImgNum;
		}
		public void setViewCount(int viewCount){
		this.viewCount = viewCount;
		}
		public int getViewCount(){
		return this.viewCount;
		}
		public void setLikeCount(int likeCount){
		this.likeCount = likeCount;
		}
		public int getLikeCount(){
		return this.likeCount;
		}
		public void setCommentCount(int commentCount){
		this.commentCount = commentCount;
		}
		public int getCommentCount(){
		return this.commentCount;
		}
		public void setText(String text){
		this.text = text;
		}
		public String getText(){
		return this.text;
		}
		public void setElite(boolean elite){
		this.elite = elite;
		}
		public boolean getElite(){
		return this.elite;
		}

		}
 
		public class Data {
		private List<Books> books ;

		private int count;

		public void setBooks(List<Books> books){
		this.books = books;
		}
		public List<Books> getBooks(){
		return this.books;
		}
		public void setCount(int count){
		this.count = count;
		}
		public int getCount(){
		return this.count;
		}

		}
		 
}
