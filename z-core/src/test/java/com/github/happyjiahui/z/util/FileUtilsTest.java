package com.github.happyjiahui.z.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class FileUtilsTest {

    String content1 = "小明";
    String content2 = "小红";

    @Test
    public void testWriteByByte() {
        String content = content1 + "\r\n" + content2;
        String filename = "out/testWriteByByte.txt";
        FileUtils.writeByByte(filename, content);
        String s = FileUtils.readStr(filename);
        Assert.assertEquals(content, s);
    }

    @Test
    public void testWriteByChar() {
        String content = content1 + "\r\n" + content2;
        String filename = "out/testWriteByChar.txt";
        FileUtils.writeByChar(filename, content);
        List<String> lines = FileUtils.readLines(filename);
        Assert.assertEquals(lines.size(), 2);
        String firstLine = FileUtils.readLine(filename);
        Assert.assertEquals(firstLine, content1);
    }

}