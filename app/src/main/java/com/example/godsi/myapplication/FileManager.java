package com.example.godsi.myapplication;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
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
        return applicationContext.getExternalFilesDir(null).listFiles();
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

    public void createFile(String fileName, ArrayList<Writable> classFiles) {
        fileName = fileName.concat(".pl");
        File file = new File(applicationContext.getExternalFilesDir(null),fileName);
        try{
            if (file != null){
                file.delete();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            for (Writable classObject: classFiles) {
                for (String line: classObject.serialize()) {
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
            }
            bufferedWriter.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


}
