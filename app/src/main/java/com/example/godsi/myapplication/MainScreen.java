package com.example.godsi.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * This class handles the initialization of the main screen of the application
 * @author Hong Chin Choong
 * @version 0.1v
 */
public class MainScreen extends AppCompatActivity {
    FileManager fileManager;
    MainScreenGUIUpdater guiUpdater = new MainScreenGUIUpdater();

    /** Called when the activity is first created to initialize it.
     * @param savedInstanceState bundle containing data stored when the activity was suspended
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        fileManager = new FileManager(getApplicationContext(), null, null);
        String [] listOfFiles = fileManager.getFileNames();
        for (String filename: listOfFiles) {
            if (filename.contains(".pl")) {
                guiUpdater.generateUI(filename);
            }
        }
    }

    /**
     * Updates file name list when the activity resumes
     */
    @Override
    protected void onResume() {
        super.onResume();
        ((LinearLayout) findViewById(R.id.fileList)).removeAllViews();
        String [] listOfFiles = fileManager.getFileNames();
        for (String filename: listOfFiles) {
            if (filename.contains(".pl")) {
                guiUpdater.generateUI(filename);
            }
        }
    }

    /***
     * Starts the main activity
     */
    public void createNewProgram(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Loads a file after starting the main activity
     * @param fileName Name of file to be loaded
     */
    public void loadProgram(String fileName) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("file", fileName.substring(0,fileName.length()-3));
        startActivity(intent);
    }

    public class MainScreenGUIUpdater implements GUIUpdater{

        public void updateInstructions(String instruction){}

        //delete views based on their Id
        public void removeView (int viewId){}

        //disable/enable view based on their Id
        public void disableEnableView(int viewId, boolean enable){}
        //hide view based on their Id
        public void hideView(int viewId){}

        //show view based on their Id
        public void showView(int viewId){}

        //get view based on their Id
        public View getView(int viewId) {return null; };

        //generation of console log entry based on input
        public void createConsoleLog(String logText){}

        //creation of new UI elements in the layout
        public int generateUI(final String fileName){
            LinearLayout fileList = (LinearLayout) findViewById(R.id.fileList);
            TextView newFile = new TextView(getApplicationContext());
            newFile.setText(fileName);
            newFile.setTextSize(25);
            newFile.setTextColor(0xff000000);
            newFile.setPadding(5,5,5,5);
            newFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadProgram(fileName);
                }
            });
            fileList.addView(newFile);
            return 0;
        }

        //creation of new parameter UI to existing Predicate UI in layout
        public int generateUI(int parentId, String uiType){
            return 0;
        }

        public void generateUIForMathComp(int parentId, String uiType){}

        public boolean replaceUIValue(int parentId, String value){ return false;}

        //updates the value of a view based on id
        public void updateUIValue(int id, String value) {}

        public View getLastConsoleLog(){ return null;}

        public void updateReadInput(String header, String var){}

    }


}
