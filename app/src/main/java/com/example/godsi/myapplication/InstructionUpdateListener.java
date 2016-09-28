package com.example.godsi.myapplication;

import android.view.MotionEvent;
import android.view.View;

/**
 * This class is a listener which listens for touch events and updates the instruction view based on the element touched
 * @author Hong Chin Choong
 * @version 0.1v
 */
public class InstructionUpdateListener implements View.OnTouchListener {

    private String newInstruction; //instruction to be shown on instruction view
    private GUIUpdater guiUpdater; //the GUI handler of the activity

    public InstructionUpdateListener(String instruction, GUIUpdater updater){
        newInstruction = instruction;
        guiUpdater = updater;
    }

    /**
     * Handles the touch event and update the instruction screen based on the newInstruction string
     * @param view
     * @param motionEvent
     * @return true if event is handled else false
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            guiUpdater.updateInstructions(newInstruction);
            return true;
        } else {
            return false;
        }
    }
}
