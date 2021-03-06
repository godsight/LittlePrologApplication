package com.example.godsi.myapplication;

import android.content.Context;
import android.graphics.Path;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * This class manages the file save and load of the application
 * @author Hong Chin Choong
 * @version 0.1
 */

public class FileManager {

    Context applicationContext; //context of the application
    MainInterpreter interpreter; //interpreter for the application
    GUIUpdater guiUpdater; //GUI update for the application

    /**
     * Initializes the class variables
     * @param appContext context of the application
     * @param activityInterpreter interpreter for the application
     * @param activityguiUpdater GUI update for the application
     */
    public FileManager(Context appContext, MainInterpreter activityInterpreter, GUIUpdater activityguiUpdater){
        applicationContext = appContext;
        interpreter = activityInterpreter;
        guiUpdater = activityguiUpdater;
    }

    /**
     * Gets a list of files available in the application's external storage
     */
    public File [] getProgramFiles (){
        return applicationContext.getExternalFilesDir(null).listFiles();
    }

    /**
     * Gets a list of file names available in the application's external storage
     */
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

    /**
     * Creates a file in the device's storage and save the current data into the file
     * @param fileName name of file to be created
     * @param classFiles files to be saved
     */
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
            guiUpdater.createConsoleLog(applicationContext.getString(R.string.file_save_success));
        }
        catch (Exception e){
            e.printStackTrace();
            guiUpdater.createConsoleLog(applicationContext.getString(R.string.file_save_fail));
        }
    }

    /**
     * Loads the file from storage and updates the application with data from it
     * @param fileName name of file to be loaded
     */
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

    /**
     * Parses information retrieved from the file and updates activity accordingly
     * @param line line of code from the file
     * @param lineNumber number of the line in the file
     */
    public void parseLine(String line, int lineNumber){
        switch (lineNumber){
            case 0:
                break;
            case 1: //update author info
                interpreter.metaInfo.authorName = line.split(":")[1];
                guiUpdater.updateUIValue(R.id.authorName, line.split(":")[1]);
                break;
            case 2: //update email info
                interpreter.metaInfo.email = line.split(":")[1];
                guiUpdater.updateUIValue(R.id.email, line.split(":")[1]);
                break;
            case 3: //update description info
                interpreter.metaInfo.description = line.split(":")[1];
                guiUpdater.updateUIValue(R.id.description, line.split(":")[1]);
                break;
            default:
                //loading information for rule
                if (line.contains(":-")){
                    String[] lineInformation = line.split(":-");
                    int id = guiUpdater.generateUI("MathematicalRule");
                    MathematicalComputation mathComp = interpreter.getMathComp(id);
                    mathComp.name = lineInformation[0];
                    guiUpdater.updateUIValue(mathComp.mathCompEditid,mathComp.name);
                    String[] parameters = lineInformation[1].substring(0,lineInformation[1].length()-1).split(",");
                    int parameterNumber = 0;
                    for(String parameter : parameters){
                        int parentViewId = guiUpdater.getParentId(mathComp.mathCompEditid);
                        if (parameter.startsWith("r")){
                            String paramValue = parameter.split("\\(")[1];
                            paramValue = paramValue.substring(0,paramValue.length()-1);
                            int paramId = guiUpdater.generateUIForMathComp(parentViewId,"Read");
                            Attribute param = mathComp.parametersArray.get(parameterNumber);
                            param.value = paramValue;
                            guiUpdater.updateUIValue(paramId,paramValue);
                            parameterNumber ++;
                        }
                        else if (parameter.startsWith("w")){
                            String paramValue = parameter.split("\\(")[1];
                            paramValue = paramValue.substring(0,paramValue.length()-1);
                            int paramId = guiUpdater.generateUIForMathComp(parentViewId,"Write");
                            Attribute param = mathComp.parametersArray.get(parameterNumber);
                            param.value = paramValue;
                            guiUpdater.updateUIValue(paramId,paramValue);
                            parameterNumber ++;
                        }
                        else {
                            String[] operationParams = parameter.split(" ");
                            int paramId = guiUpdater.generateUIForMathComp(parentViewId,"Operator");
                            Operator operator = (Operator) mathComp.parametersArray.get(parameterNumber);
                            int numberOfOperators = operator.parametersArray.size();
                            int i = numberOfOperators;
                            while (i < operationParams.length){
                                guiUpdater.generateUI(operator.getId(),"Operator");
                                i += 2;
                            }
                            int operatorNumber = 0;
                            for (String operatorParam : operationParams){
                                if (operatorParam.equals("+") || operatorParam.equals("-") || operatorParam.equals("*") || operatorParam.equals("/") || operatorParam.equals("==") || operatorParam.equals("=")
                                        || operatorParam.equals("<") || operatorParam.equals(">") || operatorParam.equals("<=") || operatorParam.equals(">=")){
                                    OperatorType operatorType = (OperatorType) operator.parametersArray.get(operatorNumber);
                                    operatorType.value = operatorParam;
                                    guiUpdater.replaceUIValue(operatorType.getId(),operatorType.value);
                                }
                                else{
                                    Constant constant = (Constant) operator.parametersArray.get(operatorNumber);
                                    constant.value = operatorParam;
                                    guiUpdater.updateUIValue(constant.getId(),constant.value);
                                }
                                operatorNumber ++;
                            }
                            parameterNumber ++;
                        }
                    }
                }
                //loading information for predicate
                else{
                    String[] lineInformation = line.split("\\(");
                    int id = guiUpdater.generateUI("predicate");
                    Predicate predicate = interpreter.getPredicate(id);
                    predicate.name = lineInformation[0];
                    guiUpdater.updateUIValue(predicate.nameId,predicate.name);
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
                    predicate.setValidity();
                }
        }

    }
}
