package com.example.godsi.myapplication;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by godsi on 10/12/2016.
 */

public class FileManager {

    Context applicationContext;

    public FileManager(Context appContext){
        applicationContext = appContext;
    }

    public File [] getProgramFiles (){
        return applicationContext.getFilesDir().listFiles();
    }

    public String [] getFileNames () {
        File[] fileList = getProgramFiles();
        String[] nameList = new String[fileList.length];
        int i = 0;
        for (File file: fileList) {
            nameList[i] = file.getName();
            i++;
        }
        return nameList;
    }

    public void createFile(String fileName, Writable[] classFiles) {
        ArrayList<String> stringToWrite = new ArrayList<>();
        fileName = fileName.concat(".pl");
        for (Writable classObject: classFiles) {
            stringToWrite.add(classObject.serialize());
        }
        try{
            FileOutputStream fileOutputStream = applicationContext.openFileOutput(fileName, Context.MODE_PRIVATE);
            for (String string: stringToWrite) {
                fileOutputStream.write(string.getBytes());
            }
            fileOutputStream.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
