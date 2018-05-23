package com.zack.lib.utils;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.Permission;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 描述：日志相关工具类
 * 作者：Zack
 * 创建时间;2018/5/23
 * 修改时间：2018/5/23
 **/
public class LogUtils {

    public static int LOG_LEVEL = 5;//开发模式为5，上线模式为0
    public final static int ERROR = 1;
    public final static int WARN = 2;
    public final static int INFO = 3;
    public final static int DEBUG = 4;
    public final static int VERBOS = 5;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS  :", Locale.CHINA);//日期格式;

    private static SimpleDateFormat fileNameFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);//日期格式;


    //日志文件路径
    public static String LOG_PATH;



    public static void setLevel(int logLevel)
    {
        LOG_LEVEL=logLevel;
    }

    public static void setLogPath(String logPath)
    {
        LOG_PATH=logPath;
    }

    public static void e(String tag,String message)
    {
        if(LOG_LEVEL>=ERROR)
        {
            Log.e(tag,dateFormat.format(new Date()) +message);
            if(!TextUtils.isEmpty(LOG_PATH))
            {
                writeToFile(message);
            }
        }

    }

    public static void w(String tag,String message)
    {
        if(LOG_LEVEL>=WARN)
        {
            Log.w(tag,dateFormat.format(new Date()) +message);
            if(!TextUtils.isEmpty(LOG_PATH))
            {
                writeToFile(message);
            }
        }
    }

    public static void i(String tag,String message)
    {
        if(LOG_LEVEL>=INFO)
        {
            Log.i(tag,dateFormat.format(new Date()) +message);
            if(!TextUtils.isEmpty(LOG_PATH))
            {
                writeToFile(message);
            }
        }
    }

    public static void d(String tag,String message)
    {
        if(LOG_LEVEL>=DEBUG)
        {
            Log.d(tag,dateFormat.format(new Date()) +message);
            if(!TextUtils.isEmpty(LOG_PATH))
            {
                writeToFile(message);
            }
        }
    }

    public static void v(String tag,String message)
    {
        if(LOG_LEVEL>=VERBOS)
        {
            Log.v(tag,dateFormat.format(new Date()) +message);
            {
                if(!TextUtils.isEmpty(LOG_PATH))
                {
                    writeToFile(message);
                }
            }
        }
    }

    private static void writeToFile(String message)
    {

        if(FileUtils.isSDCardMounted())
        {
            //log日志名
            File logFile=FileUtils.getFile(LOG_PATH,"log.txt");
            if (logFile==null) {
                return;
            }
            //文件大于2M，则重命名文件，并生成一个新的日志文件
            if(logFile.length()>=2* 1024 * 1024 * 1024L)
            {
                logFile.renameTo(FileUtils.getFile(LOG_PATH,"log"+dateFormat.format(new Date())+".txt"));

                logFile=FileUtils.getFile(LOG_PATH,"log.txt");
            }

            FileUtils.write2File(logFile.getPath(), message, true);
        }




    }
}
