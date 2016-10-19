package com.example.godsi.myapplication;

import android.content.ClipData;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Responds to long click actions in UI
 * @author Chan Kai Ying
 * @version 0.1v
 * @CreatedDate 4/10/2016
 */
public class LongClickToDragListener implements View.OnLongClickListener {

    private String instruction; //instruction to be shown on instruction view
    private GUIUpdater guiUpdater; //the GUI handler of the activity
    private String uiType; //element type

    public LongClickToDragListener(String instructionText, GUIUpdater updater, String ui){
        instruction = instructionText;
        guiUpdater = updater;
        uiType = ui;
    }

    /**
     * Handles the long click event of the element
     * @param view the view element which is long clicked
     * @return true if event is handled, else false
     */
    public boolean onLongClick(View view) {

        //For predicate elements in coding playground
        if(uiType.equalsIgnoreCase("Predicate")) {
            //setting the data to be dragged
            LinearLayout layout = (LinearLayout) view.getParent().getParent();
            ClipData data = ClipData.newPlainText("", String.valueOf(layout.getId()));
            data.addItem(new ClipData.Item(uiType));
            int count = layout.getChildCount() - 1;

            //save name and parameter elements data
            for (int i = 0; i < count; i++) {
                RelativeLayout relativeLayout = (RelativeLayout) layout.getChildAt(i);
                EditText valueText = (EditText) relativeLayout.getChildAt(0);
                String value = valueText.getText().toString();
                data.addItem(new ClipData.Item(value));
            }
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                    layout);

            //begin drag event
            layout.startDrag(data, shadowBuilder, layout, 0);
        }

        //For mathematical rule elements in coding playground
        else if(uiType.equals("MathematicalRule")){
            LinearLayout layout = (LinearLayout) view.getParent();
            ClipData data = ClipData.newPlainText("", String.valueOf(layout.getId()));
            data.addItem(new ClipData.Item(uiType));
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                    layout);

            //begin drag event
            layout.startDrag(data, shadowBuilder, layout, 0);
        }

        //For query predicate element in console command line
        else if(uiType.equalsIgnoreCase("Query Predicate")) {
            //setting the data to be dragged
            LinearLayout layout = (LinearLayout) view.getParent().getParent();
            ClipData data = ClipData.newPlainText("", String.valueOf(layout.getId()));
            data.addItem(new ClipData.Item(uiType));
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                    layout);

            //begin drag event
            layout.startDrag(data, shadowBuilder, layout, 0);
        }

        //For query mathematical rule in console command line
        else if(uiType.equalsIgnoreCase("Query Rule")){
            //setting the data to be dragged
            LinearLayout layout = (LinearLayout) view.getParent();
            ClipData data = ClipData.newPlainText("", String.valueOf(layout.getId()));
            data.addItem(new ClipData.Item(uiType));

            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                    layout);

            //begin drag event
            layout.startDrag(data, shadowBuilder, layout, 0);
        }

        //For Read, Write and Operator elements exist in mathematical rule as parameters in coding playground
        else {
            //setting the data to be dragged
            LinearLayout layout = (LinearLayout) view.getParent();
            ClipData data = ClipData.newPlainText("", String.valueOf(layout.getId()));
            int grandparentsId = ((View)layout.getParent()).getId();
            data.addItem(new ClipData.Item(String.valueOf(grandparentsId)));
            data.addItem(new ClipData.Item(uiType));
            int count = layout.getChildCount();
            for (int i = 0; i < count; i++) {
                TextView valueText = (TextView) layout.getChildAt(0);
                String value = valueText.getText().toString();
                data.addItem(new ClipData.Item(value));
            }
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                    layout);

            //begin drag event
            layout.startDrag(data, shadowBuilder, layout, 0);
        }

        //update instruction view
        guiUpdater.updateInstructions(instruction);
        return true;

    }
}