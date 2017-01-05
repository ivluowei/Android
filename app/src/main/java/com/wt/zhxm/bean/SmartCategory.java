package com.wt.zhxm.bean;

import java.util.List;

/**
 * @author wtt 资讯分类
 */
public class SmartCategory {
	private boolean status;

	private List<Tngou> tngou;

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean getStatus() {
		return this.status;
	}

	public void setTngou(List<Tngou> tngou) {
		this.tngou = tngou;
	}

	public List<Tngou> getTngou() {
		return this.tngou;
	}

	public class Tngou {
		private String description;

		private int id;

		private String keywords;

		private String name;

		private int seq;

		private String title;

		public void setDescription(String description) {
			this.description = description;
		}

		public String getDescription() {
			return this.description;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getId() {
			return this.id;
		}

		public void setKeywords(String keywords) {
			this.keywords = keywords;
		}

		public String getKeywords() {
			return this.keywords;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public void setSeq(int seq) {
			this.seq = seq;
		}

		public int getSeq() {
			return this.seq;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getTitle() {
			return this.title;
		}

	}

}
