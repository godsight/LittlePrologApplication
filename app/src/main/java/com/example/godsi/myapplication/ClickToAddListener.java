package com.example.godsi.myapplication;

import android.view.View;

/**
 * @author: Chan Kai Ying
 */

public class ClickToAddListener implements View.OnClickListener {

    private String consoleText;
    private GUIUpdater guiUpdater;
    private String uiType;

    public ClickToAddListener(String console, GUIUpdater gui, String type){
        consoleText = console;
        guiUpdater = gui;
        uiType = type;
    }

    @Override
    public void onClick(View v) {
        guiUpdater.generateUI(v, uiType);
        guiUpdater.createConsoleLog(consoleText);
    }
}
