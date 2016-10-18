package com.example.godsi.myapplication;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Deletes a predicate/queryPredicate parameter when click is detected
 * @author: Chan Kai Ying
 * @CreatedDate 5/10/2016
 */

public class ClickToDelete implements View.OnClickListener {

    private String consoleText; //text to be logged into console
    private GUIUpdater guiUpdater; //Main GuIupdater of the activity
    private MainInterpreter mainInterpreter; //Main Interpreter of the activity
    private String uiType; // type of UI

    //Constructor of the class
    public ClickToDelete(String console, GUIUpdater gui, MainInterpreter interpreter, String type){
        consoleText = console;
        guiUpdater = gui;
        mainInterpreter = interpreter;
        uiType = type;
    }

    //Removes view and creates a console log
    @Override
    public void onClick(View v) {
        RelativeLayout attr = (RelativeLayout) v.getParent();
        View param = attr.getChildAt(0);

        if(uiType.equalsIgnoreCase("Predicate")) {
            LinearLayout pred = (LinearLayout) v.getParent().getParent();
            Predicate predicate = mainInterpreter.getPredicate(pred.getId());
            predicate.deleteAttribute(param.getId());
        }
        else if(uiType.equalsIgnoreCase("Query")){
            Predicate predicate = mainInterpreter.queryPredicate;
            predicate.deleteAttribute(param.getId());
        }

        guiUpdater.removeView(attr.getId());

        guiUpdater.createConsoleLog(consoleText);
    }
}
