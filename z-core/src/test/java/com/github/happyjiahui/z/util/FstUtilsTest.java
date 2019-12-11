package com.github.happyjiahui.z.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class FstUtilsTest {

    @Test
    public void deserializerNullTest() {
        User user = FstUtils.deserializer(null);
        Assert.assertNull(user);
    }

    @Test
    public void deserializerNullTest2() {
        User user = FstUtils.deserializer(new byte[0]);
        Assert.assertNull(user);
    }

    @Test
    public void deserializerNullTest3() {
        byte[] serializer = FstUtils.serializer(null);
        User user = FstUtils.deserializer(serializer);
        Assert.assertNull(user);
    }

    @Test
    public void serializerTest() {
        BigDecimal decimal = BigDecimal.valueOf(20.12);
        byte[] bytes = new byte[] {1, 2, 3, 5, 6, 1, 2, 5};
        User user = new User();
        user.setName("小明");
        user.setAge(20);
        user.setBigDecimal(decimal);
        user.setBytes(bytes);

        byte[] serializer = FstUtils.serializer(user);
        User user2 = FstUtils.deserializer(serializer);
        Assert.assertEquals(user, user2);
    }

    public static class User implements Serializable {
        private String name;
        private int age;
        private byte[] bytes;
        private BigDecimal bigDecimal;

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

        public byte[] getBytes() {
            return bytes;
        }

        public void setBytes(byte[] bytes) {
            this.bytes = bytes;
        }

        public BigDecimal getBigDecimal() {
            return bigDecimal;
        }

        public void setBigDecimal(BigDecimal bigDecimal) {
            this.bigDecimal = bigDecimal;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            User user = (User)o;

            if (age != user.age)
                return false;
            if (name != null ? !name.equals(user.name) : user.name != null)
                return false;
            if (!Arrays.equals(bytes, user.bytes))
                return false;
            return bigDecimal != null ? bigDecimal.equals(user.bigDecimal) : user.bigDecimal == null;
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + age;
            result = 31 * result + Arrays.hashCode(bytes);
            result = 31 * result + (bigDecimal != null ? bigDecimal.hashCode() : 0);
            return result;
        }
    }
}