package com.example.godsi.myapplication;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * @author Chan Kai Ying
 * @version 0.lv
 * @createdDate 10/3/2016
 */

public class MainInterpreter {

    private ArrayList<Predicate> predicates;
    private ArrayList<Rule> rules;

    public MainInterpreter(){
        predicates = new ArrayList<>();
        rules = new ArrayList<>();

    }

    public void AddPredicate(Predicate pred){
        predicates.add(pred);
    }

    public void UpdatePredicate(Editable s, String uiType, EditText editText){
        if (uiType.equalsIgnoreCase("Predicate")) {
            int parentId = ((View) editText.getParent()).getId();
            Predicate pred = GetPredicate(parentId);
            CharSequence eType = editText.getHint();

            if ("Predicate".contentEquals(eType)) {
                pred.UpdatePredicate(s);
            }

            else if("Parameter".contentEquals(eType)){
                int viewId = editText.getId();
                pred.UpdatePredicate(s, viewId);
            }
        }
    }

    public Predicate GetPredicate(int id){
        int index = predicates.indexOf(new Predicate(id));
        return predicates.get(index);
    }
}
