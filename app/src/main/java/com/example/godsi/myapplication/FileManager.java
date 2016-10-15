package com.example.godsi.myapplication;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by godsi on 10/12/2016.
 */

public class FileManager {

    Context applicationContext;
    MainInterpreter interpreter;
    GUIUpdater guiUpdater;

    public FileManager(Context appContext, MainInterpreter activityInterpreter, GUIUpdater activityguiUpdater){
        applicationContext = appContext;
        interpreter = activityInterpreter;
        guiUpdater = activityguiUpdater;
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
    public void loadFile (String fileName){
        fileName = fileName.concat(".pl");
        File file = new File(applicationContext.getExternalFilesDir(null),fileName);
        try{
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            // read every line of the file
            String line;
            int i = 0;
            do {
                line = bufferedReader.readLine();
                parseLine(line, i);
                i ++;

            } while (line != null);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public void parseLine(String line, int lineNumber){
        switch (lineNumber){
            case 1:
                interpreter.metaInfo.authorName = line.split(":")[1];
                break;
            case 2:
                interpreter.metaInfo.email = line.split(":")[1];
                break;
            case 3:
                interpreter.metaInfo.description = line.split(":")[1];
                break;
            default:
                String[] lineInformation = line.split("\\(");
                int id = guiUpdater.generateUI("predicate");
                Predicate predicate = interpreter.getPredicate(id);
                predicate.name = lineInformation[0];
                String[] parameters = lineInformation[1].substring(0,lineInformation[1].length()-2).split(",");
                int i = 0;
                int existingParameters = predicate.parametersArray.size();
                for (String parameter: parameters
                     ) {
                    if (i >= existingParameters){
                        int paramId = guiUpdater.generateUI(id, "Predicate");
                        Constant constant = (Constant) predicate.parametersArray.get(i);
                        constant.value = parameter;
                        guiUpdater.updateUIValue(paramId,parameter);
                    }
                    else{
                        Constant constant = (Constant) predicate.parametersArray.get(i);
                        constant.value = parameter;
                        guiUpdater.updateUIValue(constant.getId(),parameter);
                    }
                    i++;
                }
        }

    }
}
