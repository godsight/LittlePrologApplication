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

    //delete views based on their Id
    public void removeView (int viewId);

    //disable/enable view based on their Id
    public void disableEnableView(int viewId, boolean enable);

    //hide view based on their Id
    public void hideView(int viewId);

    //show view based on their Id
    public void showView(int viewId);

    //generation of console log entry based on input
    public void createConsoleLog(String logText);

    //creation of new UI elements in the layout
    public int generateUI(String uiType);

    //creation of new parameter UI to existing Predicate UI in layout
    public int generateUI(int parentId, String uiType);

    //creation of new component UI to existing Mathematical rule UI in layout
    public void generateUIForMathComp(int id, String uiType);

    //replace TextView value with dropped component's value
    public boolean replaceUIValue(int id, String value);

    //updates the value of a view based on id
    public void updateUIValue(int id, String value);
}
