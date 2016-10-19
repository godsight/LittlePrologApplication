package com.example.godsi.myapplication;

import android.view.View;

/**
 * Responds to click and adds ui elements
 * @author: Chan Kai Ying
 */

public class ClickToAddListener implements View.OnClickListener {

    private String consoleText; //text to be logged into console
    private GUIUpdater guiUpdater; //Main GuIupdater of the activity
    private String uiType; //type of ui to generate
    private MainInterpreter mainInterpreter; //main class for backend operations

    //Constructor of the class
    public ClickToAddListener(String console, GUIUpdater gui, String type, MainInterpreter interpreter){
        consoleText = console;
        guiUpdater = gui;
        uiType = type;
        mainInterpreter = interpreter;
    }

    //Generates the UI and creates a console log

    /**
     * Expand parameters in predicate and mathematical rule which includes addition of predicate parameter, expanding operator parameter in mathematical rule
     * @param v
     */
    @Override
    public void onClick(View v) {
        //addition of predicate parameters
        if(uiType.equalsIgnoreCase("Predicate") || uiType.equalsIgnoreCase("Query")) {
            int parentId = ((View) v.getParent()).getId();
            guiUpdater.generateUI(parentId, uiType);
            guiUpdater.createConsoleLog(consoleText);
        }

        //expansion of operator parameter in mathematical rule
        else if(uiType.equalsIgnoreCase("Operator")){
            int opId = ((View) v.getParent()).getId();
            guiUpdater.generateUI(opId, uiType);
            guiUpdater.createConsoleLog(consoleText);
        }
    }
}
