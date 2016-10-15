package com.example.godsi.myapplication;

import java.util.ArrayList;

/**
 * This class stores the metadata for the program
 */

public class MetaInfo implements Writable{
    public String fileName; //name of the file
    public String authorName; //name of the author
    public String email; //email address of the author
    public String description; //description of the program

    //initializes the class variables
    public MetaInfo() {
        fileName = "";
        authorName = "";
        email = "";
        description = "";
    }

    /**
     * Returns a string to represent data stored in this class
     */
    public ArrayList<String> serialize(){
        ArrayList<String> serializedArray = new ArrayList<>();
        serializedArray.add("% File name:" + fileName);
        serializedArray.add("% Author name:" + authorName);
        serializedArray.add("% Email:" + email);
        serializedArray.add("% Description:" + description);
        return serializedArray;
    }
}
