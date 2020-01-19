package com.github.happyjiahui.z.util;

import com.github.happyjiahui.z.exception.UtilException;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardOpenOption.APPEND;

/**
 * @author zhaojinbing
 */
public class FileUtils {

    private static final String DEFAULT_CHARSET = "UTF-8";

    private FileUtils() {

    }

    /**
     * 删除文件
     * 
     * @param filename
     *            文件名称
     * @return 是否成功
     */
    public static boolean delete(String filename) {
        File file = new File(filename);
        return file.delete();
    }

    /**
     * 删除文件
     *
     * @param filename
     *            文件名称
     */
    public static void deleteOnExit(String filename) {
        File file = new File(filename);
        file.deleteOnExit();
    }

    /**
     * 当前文件的所属目录不存在则创建该目录
     * 
     * @param filename
     *            文件名称
     */
    public static void mkdirPDirIfNoExists(String filename) {
        File file = new File(filename);
        mkdirPDirIfNoExists(file);
    }

    /**
     * 当前文件的所属目录不存在则创建该目录
     * 
     * @param file
     *            文件
     */
    public static void mkdirPDirIfNoExists(File file) {
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdir();
        }
    }

    /**
     * 如果不存在则创建目录
     * 
     * @param dirname
     *            目录名称
     */
    public static void mkdirIfNoExists(String dirname) {
        File file = new File(dirname);
        mkdirIfNoExists(file);
    }

    /**
     * 如果不存在则创建目录
     * 
     * @param file
     *            目录
     */
    public static void mkdirIfNoExists(File file) {
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 当前文件不存在则创建新文件
     * 
     * @param filename
     *            文件名称
     */
    public static void createNewFileIfNotExists(String filename) {
        File file = new File(filename);
        createNewFileIfNotExists(file);
    }

    /**
     * 当前文件不存在则创建新文件
     * 
     * @param file
     *            文件
     */
    public static void createNewFileIfNotExists(File file) {
        if (file.exists()) {
            return;
        }
        mkdirPDirIfNoExists(file);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new UtilException("创建新文件失败", e);
        }
    }

    /**
     * 使用字节流写入文件
     * 
     * @param filename
     *            文件名
     * @param content
     *            内容
     */
    public static void writeByByte(String filename, String content) {
        File file = new File(filename);
        FileUtils.writeByByte(file, content);
    }

    /**
     * 使用字节流写入文件
     * 
     * @param file
     *            文件
     * @param content
     *            内容
     */
    public static void writeByByte(File file, String content) {
        byte[] bytes;
        try {
            bytes = content.getBytes(DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new UtilException("不支持的编码", e);
        }
        writeByByte(file, bytes);
    }

    /**
     * 使用字节流写入文件
     * 
     * @param file
     *            文件
     * @param bytes
     *            字节数组
     */
    public static void writeByByte(File file, byte[] bytes) {
        mkdirPDirIfNoExists(file);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(bytes);
        } catch (IOException e) {
            throw new UtilException("写入文件失败", e);
        }
    }

    /**
     * 从文本中一次读取所有内容
     * 
     * @param filename
     *            文件名称
     * @return 文本内容
     */
    public static String readStr(String filename) {
        File file = new File(filename);
        return readStr(file);
    }

    /**
     * 从文本中一次读取所有内容
     * 
     * @param file
     *            文件
     * @return 文本内容
     */
    public static String readStr(File file) {
        byte[] bytes = readByte(file);
        return new String(bytes);
    }

    /**
     * 从文本中一次读取所有内容
     * 
     * @param file
     *            文件
     * @return 二进制数据
     */
    public static byte[] readByte(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
            return bytes;
        } catch (IOException e) {
            throw new UtilException("读取文件失败", e);
        }
    }

    /**
     * 使用字符流写入文件
     * 
     * @param filename
     *            文件名
     * @param content
     *            内容
     */
    public static void writeByChar(String filename, String content) {
        File file = new File(filename);
        FileUtils.writeByChar(file, content);
    }

    /**
     * 使用字符流写入文件
     * 
     * @param file
     *            文件
     * @param content
     *            内容
     */
    public static void writeByChar(File file, String content) {
        mkdirPDirIfNoExists(file);
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(content);
        } catch (IOException e) {
            throw new UtilException("写入文件失败", e);
        }
    }

    /**
     * 从文本中逐行读取内容，并存储载list中
     *
     * @param filename
     *            文件名
     * @return list
     */
    public static List<String> readLines(String filename) {
        File file = new File(filename);
        return readLines(file);
    }

    /**
     * 从文本中逐行读取内容，并存储载list中
     *
     * @param file
     *            文件
     * @return list
     */
    public static List<String> readLines(File file) {
        List<String> lines = new ArrayList<>();
        readLines(file, lines::add);
        return lines;
    }

    /**
     * 从文本中逐行读取内容
     *
     * @param file
     *            文件
     * @param lineHandler
     *            行处理
     */
    public static void readLines(File file, ILineHandler lineHandler) {
        try (FileReader fileReader = new FileReader(file)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lineHandler.handle(line);
            }
        } catch (IOException e) {
            throw new UtilException("读取文件失败", e);
        }
    }

    /**
     * 从文本中逐行读取内容
     * 
     * @param filename
     *            文件名称
     * @param lineHandler
     *            行处理
     */
    public static void readLines(String filename, ILineHandler lineHandler) {
        File file = new File(filename);
        readLines(file, lineHandler);
    }

    /**
     * 从文本中读取一行内容
     *
     * @param filename
     *            文件名
     * @return 内容
     */
    public static String readLine(String filename) {
        File file = new File(filename);
        return readLine(file);
    }

    /**
     * 从文本中读取一行内容
     * 
     * @param file
     *            文件
     * @return 内容
     */
    public static String readLine(File file) {
        try (FileReader fileReader = new FileReader(file)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new UtilException("读取文件失败", e);
        }
    }

    public static void appendLine(String filename, List<String> lines) {
        createNewFileIfNotExists(filename);
        Path path = Paths.get(filename);
        try (FileChannel fileChannel = FileChannel.open(path, APPEND)) {
            lines.forEach(line -> {
                try {
                    writeBuffer(fileChannel, line + "\r\n");
                } catch (IOException e) {
                    throw new UtilException("追加文件失败", e);
                }
            });
        } catch (IOException e) {
            throw new UtilException("打开文件失败", e);
        }
    }

    private static void writeBuffer(FileChannel fileChannel, String line) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.wrap(line.getBytes(DEFAULT_CHARSET));
        fileChannel.write(byteBuffer);
    }

}
