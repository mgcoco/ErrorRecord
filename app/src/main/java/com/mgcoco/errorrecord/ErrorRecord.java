package com.mgcoco.errorrecord;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ErrorRecord {

    private static ErrorRecord sInstance;
    private Context mContext;
    private boolean mIsWriteExternal;

    public static ErrorRecord getInstance(Context context){
        if(sInstance == null){
            sInstance = new ErrorRecord();
        }
        sInstance.mContext = context;
        return sInstance;
    }

    public void init(boolean isExternal){
        mIsWriteExternal = isExternal;
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                Gson gson = new Gson();
                List<ErrorLog> error = gson.fromJson(PrefUtil.getStringPreference(mContext, "ERROR_RECORD"), new TypeToken<List<ErrorLog>>(){}.getType());

                System.out.println("setDefaultUncaughtExceptionHandler:" + PrefUtil.getStringPreference(mContext, "ERROR_RECORD"));
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateStr = dateformat.format(System.currentTimeMillis());
                if(error == null)
                    error = new ArrayList<>();
                error.add(new ErrorLog(dateStr, paramThrowable.getMessage()));

                if(error.size() > 100){
                    error = error.subList(error.size() - 100, error.size());
                }

                PrefUtil.setStringPreference(mContext, "ERROR_RECORD", gson.toJson(error));

                if(mIsWriteExternal){
                    if(ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                        writeToSDFile(gson.toJson(error));
                    }
                }
            }
        });
    }

    private void writeToSDFile(String error){
        File dir = new File (Environment.getExternalStorageDirectory().getAbsolutePath() + "/download");
        dir.mkdirs();
        File file = new File(dir, "Error_Record.txt");

        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);
            pw.println(error);
            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showErrorRecordList(){
        Intent i = new Intent(mContext, ErrorRecordActivity.class);
        mContext.startActivity(i);
    }
}
