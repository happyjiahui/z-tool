package com.thtf.zhhcxpt.kxzlb.common.web.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaojinbing
 * @Classname RTest
 * @Description TODO
 * @Date 2019/12/2 14:41
 */
public class RTest {

    @org.junit.Test
    public void getSuccessTest() {
        R r = R.ok("result", "hello world");
        assertTrue(r.success());
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

    public class User {
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