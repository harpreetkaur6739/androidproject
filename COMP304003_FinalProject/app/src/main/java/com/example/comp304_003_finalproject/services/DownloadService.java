package com.example.comp304_003_finalproject.services;

import android.Manifest;
import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.example.comp304_003_finalproject.database.DatabaseHandler;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;

public class DownloadService extends IntentService {

    private Thread thread = new Thread();
    ArrayList data;
    String userId;
    public static final int PERMISSION_REQUEST_CODE = 1;
    String[] header = new String[]{"Schedule Date","Site","Start Time","End Time"};

    public DownloadService() {
        super("Download");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        thread.start();
        try {
            data = (ArrayList) intent.getSerializableExtra("data");
            userId = intent.getStringExtra("userId");
            DownloadFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void DownloadFile() {
        try {
            generateCsv();
         } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateCsv(){
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        String csv = path + "/Schedule" + "_" + userId + ".csv";
        int num = 1;
        CSVWriter writer = null;
        try{
            File file = new File(csv);
            while(file.exists()){
              csv = path + "/Schedule" + "_" + userId + "_" + (num++) + ".csv";
              file = new File(csv);
            }

            writer = new CSVWriter(new FileWriter(file));

            //Write Header
            writer.writeNext(header);

            //Write Data
            writer.writeAll(data);

            writer.close();

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try{
                writer.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }

        }

    }

    @Override
    public void onDestroy(){
        Toast.makeText(getBaseContext(), "Download Complete!", Toast.LENGTH_SHORT).show();
    }
}
