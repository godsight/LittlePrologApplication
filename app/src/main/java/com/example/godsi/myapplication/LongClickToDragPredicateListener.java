package com.example.godsi.myapplication;

import android.content.ClipData;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * @author Chan Kai Ying
 * @version 0.1v
 * @CreatedDate 4/10/2016
 */
public class LongClickToDragPredicateListener implements View.OnLongClickListener {

    private String instruction; //instruction to be shown on instruction view
    private GUIUpdater guiUpdater; //the GUI handler of the activity

    public LongClickToDragPredicateListener(String instructionText, GUIUpdater updater){
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
        LinearLayout layout = (LinearLayout) view.getParent().getParent();
        ClipData data = ClipData.newPlainText("", String.valueOf(layout.getId()));
        data.addItem(new ClipData.Item("Predicate"));
        int count = layout.getChildCount() - 1;
        for(int i = 0; i < count; i ++) {
            RelativeLayout relativeLayout = (RelativeLayout) layout.getChildAt(i);
            EditText valueText = (EditText) relativeLayout.getChildAt(0);
            String value = valueText.getText().toString();
            data.addItem(new ClipData.Item(value));
        }
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