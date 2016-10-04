package com.example.godsi.myapplication;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * This class watches the changes in text content of the view it is assigned to and handles it
 * @author Hong Chin Choong
 * @version 0.1v
 */
public class InputTextWatcher implements TextWatcher {

    String consoleText; //Console log message to be displayed
    GUIUpdater guiUpdater; //the GUI handler of the activity
    MainInterpreter mainInterpreter;
    EditText editText;
    String uiType;

    public InputTextWatcher (String text, GUIUpdater updater, MainInterpreter interpreter, EditText view, String type){
        consoleText = text;
        guiUpdater = updater;
        mainInterpreter = interpreter;
        editText = view;
        uiType = type;
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
        mainInterpreter.UpdatePredicate(s, uiType, editText);
        guiUpdater.createConsoleLog(consoleText + " " + s);
    }
}
