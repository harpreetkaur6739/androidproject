/**
 * Author
 * Harpreet Kaur
 * 300910377
 */
package com.example.comp304_003_finalproject.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.widget.Toast;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class DownloadService extends IntentService {

    private Thread thread = new Thread();
    ArrayList data;
    String userId;
    public static final int PERMISSION_REQUEST_CODE = 1;
    String[] header = new String[]{"Schedule Date","Site","Start Time","End Time", "Check In Time", "Check Out Time"};

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
