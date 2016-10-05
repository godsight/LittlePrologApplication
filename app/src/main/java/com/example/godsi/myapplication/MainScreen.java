package com.example.godsi.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

/**
 * This class handles the initialization of the main screen of the application
 * @author Hong Chin Choong
 * @version 0.1v
 */
public class MainScreen extends AppCompatActivity {

    /** Called when the activity is first created to initialize it.
     * @param savedInstanceState bundle containing data stored when the activity was suspended
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
    }

    /***
     * Starts the main activity
     */
    public void createNewProgram(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
