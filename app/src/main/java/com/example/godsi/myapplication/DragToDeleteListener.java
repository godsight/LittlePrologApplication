package com.example.godsi.myapplication;

import android.content.ClipData;
import android.view.DragEvent;
import android.view.View;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * This class is a listener which listens for drag drop events and handles it
 * @author Hong Chin Choong
 * @version 0.1v
 */
public class DragToDeleteListener implements View.OnDragListener {

    private String console_text; //Console log message to be displayed
    private GUIUpdater guiUpdater; //the GUI handler of the activity
    private MainInterpreter mainInterpreter;

    public DragToDeleteListener (String consoleText, GUIUpdater updater, MainInterpreter interpreter){
        console_text = consoleText;
        guiUpdater = updater;
        mainInterpreter = interpreter;
    }

    /**
     * Deletes the UI dragged if the drop is accepted otherwise updates the instruction with error message
     * @param v View which is accepts the drag event
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
                int viewId = Integer.parseInt((String) event.getClipData().getItemAt(0).getText());
                guiUpdater.removeView(viewId);

                String uiType = (String) event.getClipData().getItemAt(1).getText();
                if(uiType.equalsIgnoreCase("Predicate")) {
                    mainInterpreter.deletePredicate(viewId);
                }
                else if(uiType.equalsIgnoreCase("MathematicalRule")){
                    mainInterpreter.deleteMathComp(viewId);
                }
                else{
                    int parentId = Integer.parseInt((String) event.getClipData().getItemAt(1).getText());
                    MathematicalComputation mathematicalComputation = mainInterpreter.getMathComp(parentId);
                    mathematicalComputation.deleteAttribute(viewId);
                }

                guiUpdater.createConsoleLog(console_text);
            case DragEvent.ACTION_DRAG_ENDED:
                break;
            default:
                break;
        }
        return true;
    }
}