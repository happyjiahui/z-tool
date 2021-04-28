package com.github.happyjiahui.z.util;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtilsTest {

    String content1 = "xiaoming";
    String content2 = "xiaohong";

    @Test
    public void testWriteByByte() {
        String content = content1 + "\r\n" + content2;
        String filename = "out/testWriteByByte.txt";
        FileUtils.writeByByte(filename, content);
        String s = FileUtils.readStr(filename);
        FileUtils.deleteOnExit(filename);
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
        FileUtils.deleteOnExit(filename);
        Assert.assertEquals(firstLine, content1);
    }

    @Test
    public void testAppendLine() {
        List<String> list = new ArrayList<>();
        list.add("hello 1");
        list.add("hello 2");
        list.add("hello 3");
        String filename = "out/testAppendLine.txt";
        FileUtils.deleteOnExit(filename);
        FileUtils.appendLine(filename, list);
        List<String> lines = FileUtils.readLines(filename);
        Assert.assertArrayEquals(lines.toArray(), list.toArray());
    }

    @Test
    public void testMkdirIfNoExists() {
        String dirname = "out/test_dir";
        FileUtils.mkdirIfNoExists(dirname);
        File file = new File(dirname);
        Assert.assertTrue(file.exists());
    }

}