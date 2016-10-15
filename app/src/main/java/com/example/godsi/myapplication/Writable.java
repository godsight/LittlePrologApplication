package com.example.godsi.myapplication;

import java.util.ArrayList;

/**
 * This interface allows a class to be represented in a string format to be written into a file
 */

public interface Writable {
    //converts data in the class into a string value
    public ArrayList<String> serialize();
}
