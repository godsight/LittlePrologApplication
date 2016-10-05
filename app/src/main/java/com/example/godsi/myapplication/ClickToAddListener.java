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

    //Constructor of the class
    public ClickToAddListener(String console, GUIUpdater gui, String type){
        consoleText = console;
        guiUpdater = gui;
        uiType = type;
    }

    //Generates the UI and creates a console log
    @Override
    public void onClick(View v) {
        guiUpdater.generateUI(v, uiType);
        guiUpdater.createConsoleLog(consoleText);
    }
}
