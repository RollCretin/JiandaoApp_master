package com.cretin.data.api.model;

import java.util.List;

/**
 * Created by cretin on 2017/10/20.
 */

public class JokesImgModel {

    /**
     * page : 8
     * totalCount : 380
     * totalPage : 38
     * limit : 10
     * jokesList : [{"orignUser":{"userId":"cc8a4a5c25cd4669a5378ed8c293d95c","username":"cretin","avatar":null,"nickname":"好莱坞弟弟","age":null,"sex":null},"jokeId":"2e4ae452d10a4c4d9bd5853640b85eeb","likeCount":0,"updateTime":1508471404000,"content":"皇帝的新装？","imageUrl":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201702/11/056C3F7BBD2460309223F0DC91CF5C4A.jpg","showTime":"5小时前"},{"orignUser":{"userId":"cc8a4a5c25cd4669a5378ed8c293d95c","username":"cretin","avatar":null,"nickname":"好莱坞弟弟","age":null,"sex":null},"jokeId":"2ebe9b060866445f96760cb6efe36c91","likeCount":0,"updateTime":1508471404000,"content":"干活好卖力的番茄酱机器人","imageUrl":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201702/18/6620C8C79F54242E4F796ECCE3DF2541.gif","showTime":"5小时前"},{"orignUser":{"userId":"cc8a4a5c25cd4669a5378ed8c293d95c","username":"cretin","avatar":null,"nickname":"好莱坞弟弟","age":null,"sex":null},"jokeId":"2f3c857307ab4da586b9b76955b53d6a","likeCount":0,"updateTime":1508471404000,"content":"一个宅在厨房的玩法","imageUrl":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201701/24/FADD953B46A8BB26FCE838055C3FCDDE.gif","showTime":"5小时前"},{"orignUser":{"userId":"cc8a4a5c25cd4669a5378ed8c293d95c","username":"cretin","avatar":null,"nickname":"好莱坞弟弟","age":null,"sex":null},"jokeId":"305c32fd58344cfbb6212e0dd8bf0ff4","likeCount":0,"updateTime":1508471404000,"content":"现实的重量...","imageUrl":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201702/27/0F7DA498CC94D84FCBBC0DCBB1209C12.jpg","showTime":"5小时前"},{"orignUser":{"userId":"cc8a4a5c25cd4669a5378ed8c293d95c","username":"cretin","avatar":null,"nickname":"好莱坞弟弟","age":null,"sex":null},"jokeId":"30c426d2eea24c79afba6a9022126af3","likeCount":0,"updateTime":1508471404000,"content":"爱是一道光","imageUrl":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201702/20/841FE6132379B3E29D5463B17F505BDC.gif","showTime":"5小时前"},{"orignUser":{"userId":"cc8a4a5c25cd4669a5378ed8c293d95c","username":"cretin","avatar":null,"nickname":"好莱坞弟弟","age":null,"sex":null},"jokeId":"318c33bcc0414095ac3d730a6e51708c","likeCount":0,"updateTime":1508471404000,"content":"这叫做不战而胜","imageUrl":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201702/17/9234E4799DF7148F89453D9C9A0C0000.gif","showTime":"5小时前"},{"orignUser":{"userId":"cc8a4a5c25cd4669a5378ed8c293d95c","username":"cretin","avatar":null,"nickname":"好莱坞弟弟","age":null,"sex":null},"jokeId":"31e39ee2d6844ee8aed829befee6d51f","likeCount":0,"updateTime":1508471404000,"content":"为什么要在家里挂个荡妇啊","imageUrl":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201702/13/EC8A472BB7558FAD63EF20908446CB00.jpg","showTime":"5小时前"},{"orignUser":{"userId":"cc8a4a5c25cd4669a5378ed8c293d95c","username":"cretin","avatar":null,"nickname":"好莱坞弟弟","age":null,"sex":null},"jokeId":"31f25ab876334c4cb78f83b07b1879ac","likeCount":0,"updateTime":1508471404000,"content":"哈尔滨某供热企业到居民家中测温，居民非常配合","imageUrl":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201701/22/5FC68811B2568B5A8CC0D134F3026591.jpg","showTime":"5小时前"},{"orignUser":{"userId":"cc8a4a5c25cd4669a5378ed8c293d95c","username":"cretin","avatar":null,"nickname":"好莱坞弟弟","age":null,"sex":null},"jokeId":"322c6042213548c7b50dd5c3159e2e89","likeCount":0,"updateTime":1508471404000,"content":"磨砂材质，不容易刮花","imageUrl":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201702/16/BB45F96588132DA3D1568C9BDE217AFC.png","showTime":"5小时前"},{"orignUser":{"userId":"cc8a4a5c25cd4669a5378ed8c293d95c","username":"cretin","avatar":null,"nickname":"好莱坞弟弟","age":null,"sex":null},"jokeId":"3249d7c9f8404aa6b678760bf513cb2b","likeCount":0,"updateTime":1508471404000,"content":"日本某协会会刊送了某位大公鸡一个鸡年大礼","imageUrl":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201701/24/F27F496A0746C249DC5DEF0530FE1387.jpg","showTime":"5小时前"}]
     */

    private int page;
    private int totalCount;
    private int totalPage;
    private int limit;
    private List<JokesListBean> jokesList;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<JokesListBean> getJokesList() {
        return jokesList;
    }

    public void setJokesList(List<JokesListBean> jokesList) {
        this.jokesList = jokesList;
    }

    public static class JokesListBean {
        /**
         * orignUser : {"userId":"cc8a4a5c25cd4669a5378ed8c293d95c","username":"cretin","avatar":null,"nickname":"好莱坞弟弟","age":null,"sex":null}
         * jokeId : 2e4ae452d10a4c4d9bd5853640b85eeb
         * likeCount : 0
         * updateTime : 1508471404000
         * content : 皇帝的新装？
         * imageUrl : http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201702/11/056C3F7BBD2460309223F0DC91CF5C4A.jpg
         * showTime : 5小时前
         */

        private OrignUserBean orignUser;
        private String jokeId;
        private int likeCount;
        private long updateTime;
        private String content;
        private String imageUrl;
        private String showTime;

        public OrignUserBean getOrignUser() {
            return orignUser;
        }

        public void setOrignUser(OrignUserBean orignUser) {
            this.orignUser = orignUser;
        }

        public String getJokeId() {
            return jokeId;
        }

        public void setJokeId(String jokeId) {
            this.jokeId = jokeId;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getShowTime() {
            return showTime;
        }

        public void setShowTime(String showTime) {
            this.showTime = showTime;
        }

        public static class OrignUserBean {
            /**
             * userId : cc8a4a5c25cd4669a5378ed8c293d95c
             * username : cretin
             * avatar : null
             * nickname : 好莱坞弟弟
             * age : null
             * sex : null
             */

            private String userId;
            private String username;
            private Object avatar;
            private String nickname;
            private Object age;
            private Object sex;

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public Object getAvatar() {
                return avatar;
            }

            public void setAvatar(Object avatar) {
                this.avatar = avatar;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public Object getAge() {
                return age;
            }

            public void setAge(Object age) {
                this.age = age;
            }

            public Object getSex() {
                return sex;
            }

            public void setSex(Object sex) {
                this.sex = sex;
            }
        }
    }
}
