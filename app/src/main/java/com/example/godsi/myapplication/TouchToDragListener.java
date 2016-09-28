package com.example.godsi.myapplication;

import android.content.ClipData;
import android.view.MotionEvent;
import android.view.View;

/**
 * This class is a listener which listens for touch events and handles the drag event of its element
 * @author Hong Chin Choong
 * @version 0.1v
 */
public class TouchToDragListener implements View.OnTouchListener {

    private String instructions; //instruction to be shown on the instruction view
    private GUIUpdater guiUpdater; //the GUI handler of the activity
    private String dataType; //type of data the view is creating

    public TouchToDragListener(String message, GUIUpdater updater, String type){
        instructions = message;
        guiUpdater = updater;
        dataType = type;
    }

    /**
     * Handles the touch event of the element
     * @param view
     * @param motionEvent
     * @return true if event is handled, else false
     */
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //when the element is touched
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

            //setting the data to be dragged
            ClipData data = ClipData.newPlainText("", "");
            data.addItem(new ClipData.Item(dataType));
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                    view);

            //begin drag event
            view.startDrag(data, shadowBuilder, view, 0);

            //update instruction view
            guiUpdater.updateInstructions(instructions);

            return true;
        } else {
            return false;
        }
    }
}