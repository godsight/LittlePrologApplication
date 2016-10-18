package com.example.godsi.myapplication;

import android.graphics.Path;
import android.text.Editable;

import java.util.ArrayList;

/**
 * @author: Chan Kai Ying
 */

public class MathematicalComputation implements Writable {

    private int id;
    public int mathCompEditid;
    String name;
    ArrayList<Attribute> parametersArray;
    private String comment;

    public MathematicalComputation(int identifier){
        id = identifier;
        name = "";
        parametersArray = new ArrayList<>();
    }

    public int getId(){
        return id;
    }

    public void setId(int i){
        id = i;
    }

    public boolean updateMathComp(String s){
        if(!name.equalsIgnoreCase(s)){
            name = s;
            return true;
        }
        return false;
    }

    public boolean updateMathComp(String s, String argType, int viewId, int parentId){
        if(argType.equalsIgnoreCase("readArg")){
            Read read = (Read) getAttribute(viewId);
            if(read != null && !read.value.equalsIgnoreCase(s)){
                read.value = s;
                return true;
            }
            return false;
        }
        else if(argType.equalsIgnoreCase("writeArg")){
            Write write = (Write) getAttribute(viewId);
            if(write != null && !write.value.equalsIgnoreCase(s)){
                write.value = s;
                return true;
            }
            return false;
        }
        else if(argType.equalsIgnoreCase("val")){
            Operator parentOp = (Operator) getAttribute(parentId);
            Constant constant = (Constant) parentOp.getOpAttribute(viewId);
            if(constant != null && !constant.value.equalsIgnoreCase(s)){
                constant.value = s;
                return true;
            }
            return false;
        }
        else if(argType.equalsIgnoreCase("OP")){
            Operator parentOp = (Operator) getAttribute(parentId);
            OperatorType operatorType = (OperatorType) parentOp.getOpAttribute(viewId);
            if(operatorType != null){
                operatorType.value = s;
                return true;
            }
            return false;
        }
        return false;
    }

    public void addParam(Attribute arg){
        parametersArray.add(arg);
    }

    public Attribute getAttribute(int id){
        int index = parametersArray.indexOf(new Attribute(id));
        if(index >= 0){
            return parametersArray.get(index);
        }
        return null;
    }

    //Removes the attribute from the parameter array based on id
    public void deleteAttribute(int id){
        int index = parametersArray.indexOf(new Attribute(id));
        if(index >= 0) {
            parametersArray.remove(index);
        }
    }

    public ArrayList<String> serialize () {
        ArrayList<String> serializedArray = new ArrayList<>();
        String line = name + ":-";
        for (Attribute parameter: parametersArray
                ) {
            if (parameter instanceof Read){
                line += "read(" + parameter.value + "),";
            }
            else if (parameter instanceof Write){
                line += "write(" + parameter.value + "),";
            }
            else if (parameter instanceof Operator){
                ArrayList<String> stringArrayList = ((Operator) parameter).serialize();
                line += stringArrayList.get(0) + ",";
            }
        }
        line = line.substring(0,line.length()-1);
        line += ".";
        serializedArray.add(line);
        return  serializedArray;
    }
}
