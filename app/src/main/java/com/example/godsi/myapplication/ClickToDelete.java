package com.example.godsi.myapplication;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * @author: Chan Kai Ying
 * @CreatedDate 5/10/2016
 */

public class ClickToDelete implements View.OnClickListener {

    private String consoleText;
    private GUIUpdater guiUpdater;
    private MainInterpreter mainInterpreter;
    private String uiType;

    public ClickToDelete(String console, GUIUpdater gui, MainInterpreter interpreter, String type){
        consoleText = console;
        guiUpdater = gui;
        mainInterpreter = interpreter;
        uiType = type;
    }

    @Override
    public void onClick(View v) {
        RelativeLayout attr = (RelativeLayout) v.getParent();
        View param = attr.getChildAt(0);

        if(uiType.equalsIgnoreCase("Predicate")) {
            LinearLayout pred = (LinearLayout) v.getParent().getParent();
            Predicate predicate = mainInterpreter.getPredicate(pred.getId());
            predicate.deleteAttribute(param.getId());
        }
        else if(uiType.equalsIgnoreCase("Query")){
            Predicate predicate = mainInterpreter.query;
            predicate.deleteAttribute(param.getId());
        }

        guiUpdater.removeView(attr.getId());

        guiUpdater.createConsoleLog(consoleText);
    }
}
