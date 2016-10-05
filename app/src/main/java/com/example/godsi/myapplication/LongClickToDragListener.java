package com.example.godsi.myapplication;

import android.content.ClipData;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * This class is a listener which listens for long click events and handles the drag event of its element
 * @author Hong Chin Choong
 * @version 0.1v
 */
public class LongClickToDragListener implements View.OnLongClickListener {

    private String instruction; //instruction to be shown on instruction view
    private GUIUpdater guiUpdater; //the GUI handler of the activity

    public LongClickToDragListener(String instructionText, GUIUpdater updater){
        instruction = instructionText;
        guiUpdater = updater;
    }

    /**
     * Handles the long click event of the element
     * @param view the view element which is long clicked
     * @return true if event is handled, else false
     */
    public boolean onLongClick(View view) {

        //setting the data to be dragged
        LinearLayout layout = (LinearLayout) view.getParent();
        ClipData data = ClipData.newPlainText("", String.valueOf(layout.getId()));
        data.addItem(new ClipData.Item("Variable"));
        EditText valueText = (EditText) layout.getChildAt(layout.getChildCount()-1);
        String value = valueText.getText().toString();
        data.addItem(new ClipData.Item(value));
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                layout);

        //begin drag event
        layout.startDrag(data, shadowBuilder, layout, 0);

        //update instruction view
        guiUpdater.updateInstructions(instruction);
        guiUpdater.showDustbin();
        return true;
    }
}