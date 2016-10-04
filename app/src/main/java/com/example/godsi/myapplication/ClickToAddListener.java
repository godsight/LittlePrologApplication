package com.example.godsi.myapplication;

import android.view.View;

/**
 * @author: Chan Kai Ying
 */

public class ClickToAddListener implements View.OnClickListener {

    private String consoleText;
    private GUIUpdater guiUpdater;

    public ClickToAddListener(String console, GUIUpdater gui){
        consoleText = console;
        guiUpdater = gui;
    }

    @Override
    public void onClick(View v) {
        guiUpdater.generateUI(v);
        guiUpdater.createConsoleLog(consoleText);
    }
}
