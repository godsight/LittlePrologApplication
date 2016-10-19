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
    private MainInterpreter mainInterpreter; //Main class for backend operations
    private String uiType; // type of UI

    //Constructor of the class
    public ClickToDelete(String console, GUIUpdater gui, MainInterpreter interpreter, String type){
        consoleText = console;
        guiUpdater = gui;
        mainInterpreter = interpreter;
        uiType = type;
    }

    /**
     * Removes view and creates a console log. At this stage, only used by predicate objects
     * @param v, View
     */
    @Override
    public void onClick(View v) {
        RelativeLayout attr = (RelativeLayout) v.getParent();
        View param = attr.getChildAt(0);

        //predicate elements in coding playground
        if(uiType.equalsIgnoreCase("Predicate")) {
            LinearLayout pred = (LinearLayout) v.getParent().getParent();

            //remove element from predicate in backend
            Predicate predicate = mainInterpreter.getPredicate(pred.getId());
            predicate.deleteAttribute(param.getId());
        }

        //predicate element in console command line
        else if(uiType.equalsIgnoreCase("Query")){

            //remove element from query predicate in backend
            Predicate predicate = mainInterpreter.queryPredicate;
            predicate.deleteAttribute(param.getId());
        }

        //remove view
        guiUpdater.removeView(attr.getId());

        //create console log
        guiUpdater.createConsoleLog(consoleText);
    }
}
