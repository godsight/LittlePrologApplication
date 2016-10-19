package com.example.godsi.myapplication;

import android.content.res.Resources;
import android.view.DragEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

/**
 * This class is a listener which listens for drag drop events and handles it
 * @author Hong Chin Choong & Chan Kai Ying
 * @version 0.1v
 */
public class ElementDragListener implements View.OnDragListener {

    private GUIUpdater guiUpdater; //the GUI handler of the activity
    private List<String> ops = Arrays.asList("+", "-", "*", "/", ">", "<", "==", "=", "<=", ">="); //available operator types for used in this program

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

                //get data that contains the element's type
                String clipData = (String) event.getClipData().getItemAt(clipDataItemCount-1).getText();

                //for predicate element dropped in coding playground
                if (clipData.equalsIgnoreCase("Predicate")) {
                    if(v.getId() == R.id.playground || v.getId() == R.id.playgroundContainer) {
                        guiUpdater.generateUI(clipData);
                        guiUpdater.createConsoleLog("New predicate added");
                        guiUpdater.updateInstructions("Predicate and parameter values can be updated through keyboard");
                    }
                    else{
                        guiUpdater.updateInstructions("Predicates can only be dragged into coding playground");
                    }
                }

                //for mathematical rule elements dropped in coding playground
                else if (clipData.equalsIgnoreCase("MathematicalRule")) {
                    if(v.getId() == R.id.playground || v.getId() == R.id.playgroundContainer){
                        guiUpdater.generateUI(clipData);
                        guiUpdater.createConsoleLog("New mathematical rule added");
                        guiUpdater.updateInstructions("Rule\'s name can be updated through keyboard and parameters can be added by dragging suitable components into rule");
                    }
                    else{
                        guiUpdater.updateInstructions("Mathematical rule can only be dragged into coding playground");
                    }
                }

                //for query predicate element dropped in console command line
                else if(clipData.equalsIgnoreCase("Query Predicate")){
                    if(v.getId() == R.id.consoleCommandLine){
                        guiUpdater.generateUI(clipData);
                        guiUpdater.showView(R.id.enter);
                        guiUpdater.createConsoleLog("New Query Predicate for predicate added");
                        guiUpdater.updateInstructions("Fill in the predicate name and parameters to complete Query Predicate. Click on send to start interpreting");
                    }
                    else{
                        guiUpdater.updateInstructions("Query can only be dragged into console command line");
                    }
                }

                //for query mathematical rule element dropped in console comand line
                else if(clipData.equalsIgnoreCase("Query Rule")){
                    if(v.getId() == R.id.consoleCommandLine){
                        guiUpdater.generateUI(clipData);
                        guiUpdater.showView(R.id.interpret);
                        guiUpdater.createConsoleLog("New Query Rule for mathematical computations added");
                        guiUpdater.updateInstructions("Fill in the rule name and parameters to complete Query Rule. Click on interpret to start interpreting");
                    }
                    else{
                        guiUpdater.updateInstructions("Query can only be dragged into console command line");
                    }
                }

                //for read element dropped in mathematical rule elements in coding playground
                else if(clipData.equalsIgnoreCase("Read")){
                    if(v instanceof TextView) {
                        int parentId = ((View) v.getParent()).getId();
                        guiUpdater.generateUIForMathComp(parentId, "Read");
                        guiUpdater.createConsoleLog("New read component added into mathematical rule.");
                        guiUpdater.updateInstructions("Fill in the argument to complete read component.");
                    }
                }

                //for write element dropped in mathematical rule elements in coding playground
                else if(clipData.equalsIgnoreCase("Write")){
                    if(v instanceof TextView) {
                        int parentId = ((View) v.getParent()).getId();
                        guiUpdater.generateUIForMathComp(parentId, "Write");
                        guiUpdater.createConsoleLog("New write component added into mathematical rule.");
                        guiUpdater.updateInstructions("Fill in the argument to complete write component");
                    }
                }

                //for operator element dropped in mathematical rule elements in coding playground
                else if(clipData.equalsIgnoreCase("Operator")){
                    if(v instanceof TextView){
                        int parentId = ((View)v.getParent()).getId();
                        guiUpdater.generateUIForMathComp(parentId, "Operator");
                        guiUpdater.createConsoleLog("New operator component added into mathematical rule");
                        guiUpdater.updateInstructions("Fill in ( ) spaces with constants & [ ] with operator types provided in component container");
                    }

                }

                //for operator type element dropped in operator element in mathematical rule elements in coding playground
                else if(opsExist(clipData)){
                    if(v instanceof TextView){
                        if(((TextView) v).getHint() == "OP"){
                            guiUpdater.replaceUIValue(v.getId(), clipData);
                            guiUpdater.createConsoleLog(clipData + " added into operator component");
                        }
                    }
                }
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * Check if operator type exists in ops array
     * @param op, operator type
     * @return boolean: true if exists
     */
    private boolean opsExist(String op){
        if(ops.contains(op)){
            return true;
        }
        return false;
    }
}