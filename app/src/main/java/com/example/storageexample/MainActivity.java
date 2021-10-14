package com.example.storageexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private static final String FILE_PATH_TAG = "file_path";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //INTERNAL STORAGE --------------------------------------------------------

        //create file in directory
        //the problem with this, is that it's saving to internal storage, which you can't actually
        //locate in your android operating system
        //https://stackoverflow.com/questions/59719189/xamarin-forms-how-to-access-data-user-0-com-companyname-notes-files-local-sh
        File file = new File(this.getFilesDir(), "nameOfFile.txt");
        Log.d(FILE_PATH_TAG, file.getAbsolutePath());
        Log.d(FILE_PATH_TAG, file.getName());
        Log.d(FILE_PATH_TAG, file.getPath());

        //write to this file:
        String hello = "Hello World";

        //this is used to write to files
        FileOutputStream fileOutputStream;

        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(hello.getBytes());
            fileOutputStream.close();
            Log.d("file_output_stream", "File has been written to");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //to read from file
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            Log.d("file_read", sb.toString());
        } catch (FileNotFoundException f) {
            f.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
            Log.e("exception", "input ouput exception");
        }

        //if you want to dlete internal file
//        this.deleteFile(file.getName());


        //------------------------------------------------------------DONE WITH INTERNAL STORAGE

        //EXTERNAL STORAGE EXAMPLE:
        //First check for availability:
        if (isExternalStorageWritable()) {
            File path = Environment.getStorageDirectory();
            File externalFile = new File(path, "ExternalFileExample.txt");
            Log.d(FILE_PATH_TAG, externalFile.getPath());
            Log.d(FILE_PATH_TAG, externalFile.getAbsolutePath());
            Log.d(FILE_PATH_TAG, externalFile.getName());



            //IF you want to delete file:
            //externalFile.delete();
        }

    }



    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}