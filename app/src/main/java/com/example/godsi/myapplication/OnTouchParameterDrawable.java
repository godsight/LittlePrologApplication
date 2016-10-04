package com.example.godsi.myapplication;

import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * @author: Chan Kai Ying
 * @CreatedDate 4/10/16
 */

public class OnTouchParameterDrawable implements View.OnTouchListener{

    private String consoleText;
    private GUIUpdater guiUpdater;
    private MainInterpreter mainInterpreter;

    public OnTouchParameterDrawable(String console, GUIUpdater gui, MainInterpreter interpreter){
        consoleText = console;
        guiUpdater = gui;
        mainInterpreter = interpreter;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        final int DRAWABLE_BOTTOM = 3;

        if(event.getAction() == MotionEvent.ACTION_UP) {
            EditText parameter = (EditText) v;
            if(event.getRawX() >= (parameter.getRight() - parameter.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width() + 175)) {
                int parentId = ((LinearLayout)parameter.getParent()).getId();
                Predicate parentPred = mainInterpreter.getPredicate(parentId);
                parentPred.deleteAttribute(parameter.getId());

                guiUpdater.removeView(parameter.getId());
                guiUpdater.createConsoleLog(consoleText);
                return true;
            }
        }
        return false;
    }
}
