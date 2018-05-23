package com.zack.lib.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * 描述：文件相关工具类
 * 作者：Zack
 * 创建时间;2018/5/23
 * 修改时间：2018/5/23
 **/
public class FileUtils {


    /**
     * 获得文件存储路径
     *
     * @return
     */
    private static String getFilePath(Context context) {

        if (Environment.MEDIA_MOUNTED.equals(Environment.MEDIA_MOUNTED) || !Environment.isExternalStorageRemovable()) {//如果外部储存可用
            return context.getExternalFilesDir(null).getPath();//获得外部存储路径,默认路径为
        } else {
            return context.getFilesDir().getPath();//直接存在/data/data里，非root手机是看不到的
        }
    }

    /**
     * 判断sdcrad是否已经安装
     * @return boolean true安装 false 未安装
     */
    public static boolean isSDCardMounted(){
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 得到sdcard的路径
     * @return
     */
    public static String getSDCardRoot(){
        System.out.println(isSDCardMounted()+Environment.getExternalStorageState());
        if(isSDCardMounted()){
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return "";
    }
    /**
     * 创建文件的路径及文件
     * @param path 路径，方法中以默认包含了sdcard的路径，path格式是"/path...."
     * @param filename 文件的名称
     * @return 返回文件的路径，创建失败的话返回为空
     */
    public static String createMkdirsAndFiles(String path, String filename) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        path = getSDCardRoot()+path;
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.mkdirs();
            } catch (Exception e) {
                return null;
            }
        }
        File f = new File(file, filename);
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                return null;
            }
        }
        return f.getAbsolutePath();
    }


    /**
     * 获取文件,如果不存在，则自动创建文件
     * @param path 路径，方法中以默认包含了sdcard的路径，path格式是"/path...."
     * @param filename 文件的名称
     * @return 返回文件的路径，创建失败的话返回为空
     */
    public static File getFile(String path, String filename) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        path = getSDCardRoot()+path;
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.mkdirs();
            } catch (Exception e) {
                return null;
            }
        }
        File f = new File(file, filename);
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                return null;
            }
        }
        return f;
    }

    /**
     * 把内容写入文件
     * @param path 文件路径
     * @param text 内容
     */
    public static void write2File(String path,String text,boolean append){
        BufferedWriter bw = null;
        try {
            //1.创建流对象
            bw = new BufferedWriter(new FileWriter(path,append));
            //2.写入文件
            bw.write(text);
            //换行刷新
            bw.newLine();
            bw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //4.关闭流资源
            if(bw!= null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 删除文件
     * @param path
     * @return
     */
    public static boolean deleteFile(String path){
        if(TextUtils.isEmpty(path)){
            throw new RuntimeException("路径为空");
        }
        File file = new File(path);
        if(file.exists()){
            try {
                file.delete();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    /**
     * 获取文件夹大小
     * @param file File实例
     * @return long 单位为KB
     * @throws Exception
     */
    public static long getFolderSize(java.io.File file){
        long size = 0;
        java.io.File[] fileList = file.listFiles();
        for (int i = 0; i < fileList.length; i++)
        {
            if (fileList[i].isDirectory())
            {
                size = size + getFolderSize(fileList[i]);
            } else
            {
                size = size + fileList[i].length();
            }
        }
        return size/1024;
    }
}
