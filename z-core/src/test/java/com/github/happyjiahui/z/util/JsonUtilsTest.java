package com.github.happyjiahui.z.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.github.happyjiahui.z.exception.UtilException;

public class JsonUtilsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void exceptionTest() {
        String json = "{'name': '小明'}";
        thrown.expect(UtilException.class);
        JsonUtils.parseObj(json, User.class);
    }

    @Test
    public void toStringTest() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName("小黄" + i);
            user.setAge(20);
            user.setDate(new Date());
            users.add(user);
        }
        String jsonStr = JsonUtils.toString(users);
        List<User> list = JsonUtils.parseArray(jsonStr, User.class);
        Assert.assertEquals(list.size(), 10);
        Assert.assertEquals(list.get(0).getName(), "小黄0");
        Assert.assertEquals(list.get(9).getName(), "小黄9");
    }

    @Test
    public void parseTest() {
        String json = "{\"name\": \"小明\", \"age\": 20}";
        User user = JsonUtils.parseObj(json, User.class);
        Assert.assertEquals(user.getName(), "小明");
        Assert.assertEquals(user.getAge(), 20);
    }

    public static class User {
        private String name;
        private int age;
        private Date date;
        private String des;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        @Override
        public String toString() {
            return "User{" + "name='" + name + '\'' + ", age=" + age + ", date=" + DateTimeUtils.formatDate(date)
                + ", des='" + des + '\'' + '}';
        }
    }

}