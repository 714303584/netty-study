package io.netty.example.uptime;

import com.alibaba.fastjson.JSON;

public class User {

    private String userId;

    private String userName;

    private String desc;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }




    public static void main(String[] args) {
        User user = new User();
        user.setUserId("1");
        user.setUserName("user");
        user.setDesc("useri");
        System.out.print(JSON.toJSONString(user));


    }
}
