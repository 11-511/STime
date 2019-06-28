package xyz.miles.stime.util;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.GetDataCallback;
import com.avos.avoscloud.ProgressCallback;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileTools {

    // 判断文件是否存在
    public static Boolean fileIsExits(String fileName) {
        try {
            File target = new File(fileName);
            if (!target.exists())
            {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    // 根据byte[]创建文件，需要提供文件绝对路径
    public static void createFileWithByte(byte[] bytes, String absFilePath) {

        File file = new File(absFilePath);
        // 创建FileOutputStream对象
        FileOutputStream outputStream = null;
        // 创建BufferedOutputStream对象
        BufferedOutputStream bufferedOutputStream = null;
        try {
            // 如果文件存在则删除
            if (!file.exists()) {
                file.delete();
            }
            // 在文件系统中根据路径创建一个新的空文件
            file.createNewFile();
            // 获取FileOutputStream对象
            outputStream = new FileOutputStream(file);
            // 获取BufferedOutputStream对象
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            // 往文件所在的缓冲输出流中写byte数据
            bufferedOutputStream.write(bytes);
            // 刷出缓冲输出流，该步很关键，要是不执行flush()方法，那么文件的内容是空的。
            bufferedOutputStream.flush();
        } catch (Exception e) {
            // 打印异常信息
            e.printStackTrace();
        } finally {
            // 关闭创建的流对象
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    // 下载图片到绝对路径
    public static void downloadPicture(AVFile picture, final String absFilePath) {
        picture.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] data, AVException e) {
                if (e == null) {
                    createFileWithByte(data, absFilePath);
                } else {

                }
            }
        }, new ProgressCallback() {
            @Override
            public void done(Integer percentDone) {

            }
        });
    }
}
