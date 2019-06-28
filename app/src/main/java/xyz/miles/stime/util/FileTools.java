package xyz.miles.stime.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.GetDataCallback;
import com.avos.avoscloud.ProgressCallback;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import xyz.miles.stime.activity.ImageActivity;

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
            //判断父目录是否存在
            if (!file.getParentFile().exists()) {
                //父目录不存在 创建父目录
                if (!file.getParentFile().mkdirs()) {
                    Log.d("创建父目录失败", file.toString());
                    return;
                }
            }
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
            Log.d("创建文件成功", file.toString());
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

    // TODO 下载图片到绝对路径（待完善）
    public static void downloadPicture(final Context context, AVFile picture, final String absFilePath) {
        picture.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] data, AVException e) {
                if (e == null) {
                    createFileWithByte(data, absFilePath);
                    Toast.makeText(context, "下载完毕",
                            Toast.LENGTH_SHORT).show();
                } else {

                }
            }
        }, new ProgressCallback() {
            @Override
            public void done(Integer percentDone) {

            }
        });
    }

    // 获取sd卡路径
    public static String getSdCardPath() {
        File sdDir = null;
        boolean sdCardisExit = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);
        if (sdCardisExit) {
            // sdcard存在
            sdDir = Environment.getExternalStorageDirectory();	// 根目录获取
        }

        return sdDir.toString();
    }

}
