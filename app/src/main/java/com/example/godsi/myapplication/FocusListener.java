package com.example.godsi.myapplication;

import android.view.View;

/**
 * This class is a listener which listens for focus change events and handles it
 * @author Hong Chin Choong
 * @version 0.1v
 */
public class FocusListener implements View.OnFocusChangeListener {

    private String instruction; //instruction to be shown on instruction view
    private GUIUpdater guiUpdater; //the GUI handler of the activity

    public FocusListener(String instructionText, GUIUpdater updater){
        instruction = instructionText;
        guiUpdater = updater;
    }

    /**
     * Updates the instruction screen when the view is being focused
     * @param view The view which is focused/unfocused
     * @param b focus state, true if focused else false
     */
    @Override
    public void onFocusChange(View view, boolean b) {
        if (b){
            guiUpdater.updateInstructions(instruction);
        }
    }
}
