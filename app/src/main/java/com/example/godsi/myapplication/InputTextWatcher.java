package com.example.godsi.myapplication;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * This class watches the changes in text content of the view it is assigned to and handles it
 * @author Hong Chin Choong
 * @version 0.1v
 */
public class InputTextWatcher implements TextWatcher {

    String consoleText; //Console log message to be displayed
    GUIUpdater guiUpdater; //the GUI handler of the activity

    public InputTextWatcher (String text, GUIUpdater updater){
        consoleText = text;
        guiUpdater = updater;
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    /**
     * Creates a console log entry after the view's text has been changed
     * @param s current text content of te view
     */
    public void afterTextChanged(Editable s) {
        guiUpdater.createConsoleLog(consoleText + s);
    }
}
