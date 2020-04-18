package com.qh.wanandroid.bean;

import java.util.List;

public class GankIoDataBean {
    /**
     * "data":Array[10],
     *     "page":1,
     *     "page_count":6,
     *     "status":100,
     *     "total_counts":59
     */
    private List<ResultBean> data;
    private int page;
    private int page_count;
    private int status;
    private int total_counts;

    public List<ResultBean> getData() {
        return data;
    }

    public void setData(List<ResultBean> data) {
        this.data = data;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotal_counts() {
        return total_counts;
    }

    public void setTotal_counts(int total_counts) {
        this.total_counts = total_counts;
    }

    public static class ResultBean {
        /**
         * "_id":"5e958f1eee6ba981da2af339",
         *             "author":"鸢媛",
         *             "category":"Girl",
         *             "createdAt":"2020-04-18 08:00:00",
         *             "desc":"每一个爱情故事的开始总是灿烂如花，
         * 而结尾却又总是沉默如土。",
         *             "images":Array[1],
         *             "likeCounts":0,
         *             "publishedAt":"2020-04-18 08:00:00",
         *             "stars":1,
         *             "title":"第59期",
         *             "type":"Girl",
         *             "url":"http://gank.io/images/fae111696a8b418297833324ff93bd1a",
         *             "views":23
         */
        private String _id;
        private String author;
        private String category;
        private String createdAt;
        private String desc;
        private List<String> images;
        private int likeCounts;
        private String publishedAt;
        private int stars;
        private String title;
        private String type;
        private String url;
        private int views;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public int getLikeCounts() {
            return likeCounts;
        }

        public void setLikeCounts(int likeCounts) {
            this.likeCounts = likeCounts;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public int getStars() {
            return stars;
        }

        public void setStars(int stars) {
            this.stars = stars;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getViews() {
            return views;
        }

        public void setViews(int views) {
            this.views = views;
        }
    }

}
