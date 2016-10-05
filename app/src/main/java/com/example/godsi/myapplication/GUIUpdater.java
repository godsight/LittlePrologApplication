package com.example.godsi.myapplication;

import android.view.View;

/**
 * This is the interface which a GUI handler of the activity must implement
 * @author Hong Chin Choong
 * @version 0.1v
 */
public interface GUIUpdater {

    //update of instruction view based on input
    public void updateInstructions(String instruction);

    //show dustbin to delete elements
    public void showDustbin ();

    //hide dustbin to delete elements
    public void hideDustbin ();

    //delete views based on their Id
    public void removeView (int viewId);

    //generation of console log entry based on input
    public void createConsoleLog(String logText);

    //creation of new UI elements in the layout
    public void generateUI(String uiType);

    //creation of new parameter UI to existing Predicate UI in layout
    public void generateUI(View v, String uiType);
}
