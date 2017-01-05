package com.wt.zhxm.bean;

import java.util.List;

/**
 * @author wtt 资讯详情
 */
public class SmartContent {
    private boolean status;

    private int total;

    private List<Tngou> tngou;

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTngou(List<Tngou> tngou) {
        this.tngou = tngou;
    }

    public List<Tngou> getTngou() {
        return this.tngou;
    }

    public class Tngou {
        private String count;

        private String description;

        private int fcount;

        private int id;

        private String img;

        private int infoclass;

        private String keywords;

        private int rcount;

        private long time;

        private String title;

        public void setCount(String count) {
            this.count = count;
        }

        public String getCount() {
            return this.count;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }

        public void setFcount(int fcount) {
            this.fcount = fcount;
        }

        public int getFcount() {
            return this.fcount;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getImg() {
            return this.img;
        }

        public void setInfoclass(int infoclass) {
            this.infoclass = infoclass;
        }

        public int getInfoclass() {
            return this.infoclass;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getKeywords() {
            return this.keywords;
        }

        public void setRcount(int rcount) {
            this.rcount = rcount;
        }

        public int getRcount() {
            return this.rcount;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public long getTime() {
            return this.time;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return this.title;
        }

    }

}
