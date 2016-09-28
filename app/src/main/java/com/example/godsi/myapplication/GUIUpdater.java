package com.example.godsi.myapplication;

/**
 * This is the interface which a GUI handler of the activity must implement
 * @author Hong Chin Choong
 * @version 0.1v
 */
public interface GUIUpdater {

    //update of instruction view based on input
    public void updateInstructions(String instruction);

    //generation of console log entry based on input
    public void createConsoleLog(String logText);

    //creation of new UI elements in the layout
    public void generateUI(String uiType);
}
