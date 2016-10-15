package com.example.godsi.myapplication;

import java.util.ArrayList;

/**
 * Created by godsi on 10/15/2016.
 */

public class MetaInfo implements Writable{
    public String fileName;
    public String authorName;
    public String email;
    public String description;

    public MetaInfo() {
        fileName = "";
        authorName = "";
        email = "";
        description = "";
    }
    public ArrayList<String> serialize(){
        ArrayList<String> serializedArray = new ArrayList<>();
        serializedArray.add("% File name: " + fileName);
        serializedArray.add("% Author name: " + authorName);
        serializedArray.add("% Email: " + email);
        serializedArray.add("% Description: " + description);
        return  serializedArray;
    }
}
