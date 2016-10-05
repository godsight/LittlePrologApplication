package com.example.godsi.myapplication;

import android.view.DragEvent;
import android.view.View;

/**
 * This class is a listener which listens for drag drop events and handles it
 * @author Hong Chin Choong
 * @version 0.1v
 */
public class ElementDragListener implements View.OnDragListener {

    private String elementType; //type of element the view is accepting
    private String console_text; //Console log message to be displayed
    private String success_instruction; //Instruction on successful drop event
    private String error_instruction; //Instruction on failed drop event
    private GUIUpdater guiUpdater; //the GUI handler of the activity

    public ElementDragListener (String type, String consoleText, String successInstruction, String errorInstruction, GUIUpdater updater){
        elementType = type;
        console_text = consoleText;
        success_instruction = successInstruction;
        error_instruction = errorInstruction;
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
                //if data is matches the type the view is accepting
                if (clipData.equalsIgnoreCase(elementType)) {
                    guiUpdater.generateUI(elementType);
                    guiUpdater.createConsoleLog(console_text);
                    guiUpdater.updateInstructions(success_instruction);
                }
                else {
                    guiUpdater.updateInstructions(error_instruction);
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