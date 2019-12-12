package com.github.happyjiahui.z.web.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author zhaojinbing
 * @Classname RTest
 * @Description TODO
 * @Date 2019/12/2 14:41
 */
public class RTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void exceptionTest() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("传入的参数必须是偶数");
        R.ok("a");
    }

    @Test
    public void exception2Test() {
        R r = R.ok(2, "a");
        Assert.assertNull(r.get(2));

    }

    @org.junit.Test
    public void getSuccessTest() {
        R r = R.ok("result", "hello world");
        assertTrue(r.success());
    }

    @Test
    public void okMapTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        R r = R.ok(map);

        int a = r.getValue("a", Integer.class);
        int b = r.getValue("b", Integer.class);

        Assert.assertTrue(r.success());
        Assert.assertEquals(a, 1);
        Assert.assertEquals(b, 2);
    }

    @Test
    public void errorTest() {
        R r = R.error("某值为null");
        String errorMessage = r.getErrorMessage();
        Assert.assertEquals(errorMessage, "某值为null");
    }

    @Test
    public void error2Test() {
        R r = R.error("某值为null", "a", 2);
        String errorMessage = r.getErrorMessage();
        Assert.assertEquals(errorMessage, "某值为null");

        int a = r.getValue("a", Integer.class);
        Assert.assertEquals(a, 2);
    }

    @org.junit.Test
    public void getErrorTest() {
        R r = R.error();
        assertFalse(r.success());
    }

    @org.junit.Test
    public void getBeanValueTest() {
        User user = new User();
        user.setName("xiaoming");
        user.setAge(12);

        R r = R.ok("user", user);
        User userBean = r.getValue("user", User.class);
        assertEquals("xiaoming", userBean.getName());
        assertEquals(12, userBean.getAge().intValue());
    }

    @org.junit.Test
    public void getMapValueTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "xiaoming");
        map.put("age", 12);

        R r = R.ok("map", map);
        HashMap<String, Object> userMap = r.getValue("map", HashMap.class);
        assertEquals(2, userMap.size());
    }

    @org.junit.Test
    public void getListBeanValueTest() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName("xiaoming_" + i);
            user.setAge(12 + i);
            list.add(user);
        }

        R r = R.ok("list", list);
        List<User> listValue = r.getValue("list", List.class);
        assertEquals(12, listValue.get(0).getAge().intValue());
    }

    @org.junit.Test
    public void getListMapValueTest() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", "xiaoming_" + i);
            map.put("age", 12 + i);
            list.add(map);
        }

        R r = R.ok("list", list);
        List<Map<String, Object>> listValue = r.getValue("list", List.class);
        assertEquals(12, listValue.get(0).get("age"));
    }

    @Test
    public void getNullValueTest() {
        R r = R.ok("a", 2);
        Integer b = r.getValue("b", Integer.class);
        Assert.assertNull(b);
    }

    public static class User {
        private String name;
        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}