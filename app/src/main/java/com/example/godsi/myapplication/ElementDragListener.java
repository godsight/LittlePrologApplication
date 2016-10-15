package com.example.godsi.myapplication;

import android.view.DragEvent;
import android.view.View;

/**
 * This class is a listener which listens for drag drop events and handles it
 * @author Hong Chin Choong
 * @version 0.1v
 */
public class ElementDragListener implements View.OnDragListener {

    private GUIUpdater guiUpdater; //the GUI handler of the activity

    public ElementDragListener (GUIUpdater updater){;
        guiUpdater = updater;
    }

    /**
     * Creates new UI and updates the console and instruction if the drop is accepted otherwise updates the instruction with error message
     * @param v View which accepts the drag event
     * @param event the Drag Event
     * @return true if event is handled, else false
     */
    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                break;
            case DragEvent.ACTION_DROP:
                int clipDataItemCount = event.getClipData().getItemCount();
                String clipData = (String) event.getClipData().getItemAt(clipDataItemCount-1).getText();
                guiUpdater.generateUI(clipData);
                if (clipData.equalsIgnoreCase("Predicate")){
                    guiUpdater.createConsoleLog("New predicate added");
                    guiUpdater.updateInstructions("Predicate and parameter values can be updated through keyboard");
                }
                else if(clipData.equalsIgnoreCase("MathematicalRule")){
                    guiUpdater.createConsoleLog("New mathematical rule added");
                    guiUpdater.updateInstructions("Mathematical computation\\'s name can be updated through keyboard and parameters can be added by dragging suitable components into mathematical rule");
                }
                else if(clipData.equalsIgnoreCase("Query")){
                    guiUpdater.createConsoleLog("New query added");
                    guiUpdater.updateInstructions("Fill in the parameters and press send to run the command");
                }
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                break;
            default:
                break;
        }
        return true;
    }
}