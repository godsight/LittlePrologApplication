package com.example.godsi.myapplication;

import android.content.Context;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author: Chan Kai Ying
 */

public class TextViewKeyboardActionListener implements TextView.OnEditorActionListener {

    private String consoleText;
    private String uiType;
    private GUIUpdater guiUpdater;
    private MainInterpreter mainInterpreter;

    public TextViewKeyboardActionListener(String console, String type, GUIUpdater gui, MainInterpreter interpreter){
        consoleText = console;
        uiType = type;
        guiUpdater = gui;
        mainInterpreter = interpreter;
    }

    @Override
    public boolean onEditorAction(TextView view, int actionId, KeyEvent event){
        if(actionId == EditorInfo.IME_ACTION_DONE){
            if(uiType.equalsIgnoreCase("Predicate")) {
                if(mainInterpreter.updatePredicate(uiType, view)){
                    guiUpdater.createConsoleLog(consoleText + " " + view.getText().toString());
                }
            }
            else if(uiType.equalsIgnoreCase("Query")){
                if(mainInterpreter.updateQuery(uiType, view)){
                    guiUpdater.createConsoleLog(consoleText + " " + view.getText().toString());
                }
            }
            else if(uiType.equalsIgnoreCase("MathematicalRule")){
                if(mainInterpreter.updateMathComp(uiType, view)){
                    guiUpdater.createConsoleLog(consoleText + " " + view.getText().toString());
                }
            }

            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            return true;
        }
        return false;
    }

}
