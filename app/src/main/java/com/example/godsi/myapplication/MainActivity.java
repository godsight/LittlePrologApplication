package com.example.godsi.myapplication;

        import android.app.Activity;
        import android.content.Context;
        import android.content.res.Resources;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.support.v4.content.ContextCompat;
        import android.support.v4.content.res.ResourcesCompat;
        import android.view.KeyEvent;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.WindowManager;
        import android.view.inputmethod.EditorInfo;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.RelativeLayout;
        import android.widget.ScrollView;
        import android.widget.TextView;

        import java.io.File;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;

/**
 * This class handles the initialization of the main activity of the application
 * @author Hong Chin Choong
 * @version 0.1v
 */

public class MainActivity extends Activity {
    //initialize the GUI updater for the activity
    MainGUIUpdater activityGUIUpdater = new MainGUIUpdater();
    MainInterpreter mainInterpreter = new MainInterpreter(activityGUIUpdater);
    FileManager fileManager;

    /** Called when the activity is first created to initialize it.
     * @param savedInstanceState bundle containing data stored when the activity was suspended
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sets the layout of the activity
        setContentView(R.layout.main);
        fileManager = new FileManager(getApplicationContext(), mainInterpreter, activityGUIUpdater);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            String fileName = bundle.getString("file");
            ((EditText) findViewById(R.id.fileName)).setText(fileName);
            fileManager.loadFile(fileName);
        }

        //Assigning the listeners for the views in the activity
        findViewById(R.id.predicate).setOnTouchListener(new TouchToDragListener(getString(R.string.predicate_touch_instructions),activityGUIUpdater,getString(R.string.predicate)));
        findViewById(R.id.variable).setOnTouchListener(new TouchToDragListener(getString(R.string.variable_touch_instructions),activityGUIUpdater,getString(R.string.variable)));
        findViewById(R.id.query).setOnTouchListener(new TouchToDragListener(getString(R.string.query_drag_instructions),activityGUIUpdater, getString(R.string.query)));
        findViewById(R.id.componentText).setOnTouchListener(new InstructionUpdateListener(getString(R.string.component_touch_instructions),activityGUIUpdater));
        findViewById(R.id.playgroundText).setOnTouchListener(new InstructionUpdateListener(getString(R.string.playground_touch_instructions),activityGUIUpdater));
        findViewById(R.id.variablesText).setOnTouchListener(new InstructionUpdateListener(getString(R.string.variablelist_touch_instructions),activityGUIUpdater));
        findViewById(R.id.consoleText).setOnTouchListener(new InstructionUpdateListener(getString(R.string.console_touch_instructions),activityGUIUpdater));
        findViewById(R.id.playgroundContainer).setOnDragListener(new ElementDragListener(getString(R.string.predicate), getString(R.string.predicate_console_add), getString(R.string.predicate_drag_instructions), getString(R.string.predicate_drag_error), activityGUIUpdater));
        findViewById(R.id.playground).setOnDragListener(new ElementDragListener(getString(R.string.predicate), getString(R.string.predicate_console_add), getString(R.string.predicate_drag_instructions), getString(R.string.predicate_drag_error),activityGUIUpdater));
        findViewById(R.id.variablesContainer).setOnDragListener(new ElementDragListener(getString(R.string.variable),getString(R.string.variable_console_add),getString(R.string.variable_drag_instructions),getString(R.string.variable_drag_error), activityGUIUpdater));
        findViewById(R.id.variables).setOnDragListener(new ElementDragListener(getString(R.string.variable),getString(R.string.variable_console_add),getString(R.string.variable_drag_instructions),getString(R.string.variable_drag_error), activityGUIUpdater));
        findViewById(R.id.consoleCommandLine).setOnDragListener(new ElementDragListener(getString(R.string.query), "", getString(R.string.query_create_instructions), getString(R.string.console_command_droperror), activityGUIUpdater));
        findViewById(R.id.dustbin).setOnDragListener(new DragToDeleteListener(getString(R.string.delete_success_console),activityGUIUpdater, mainInterpreter));
        findViewById(R.id.run).setOnClickListener(new OnClickDo(getString(R.string.run_interpreter), activityGUIUpdater, mainInterpreter, getString(R.string.run), getString(R.string.fail_to_run_interpreter), getResources().getInteger(R.integer.runningState), getResources().getInteger(R.integer.editState)));
        findViewById(R.id.stop).setOnClickListener(new OnClickDo(getString(R.string.stop_interpreter), activityGUIUpdater, mainInterpreter, getString(R.string.stop), "", getResources().getInteger(R.integer.editState), getResources().getInteger(R.integer.editState)));
        findViewById(R.id.next).setOnClickListener(new OnClickDo("", activityGUIUpdater, mainInterpreter, getString(R.string.next), "", getResources().getInteger(R.integer.runningState), getResources().getInteger(R.integer.runningState)));
        findViewById(R.id.enter).setOnClickListener(new OnClickDo("", activityGUIUpdater, mainInterpreter, getString(R.string.enter), "", getResources().getInteger(R.integer.runningState), getResources().getInteger(R.integer.runningState)));


        ImageView image = (ImageView) findViewById(R.id.dustbin);
        image.setImageResource(R.drawable.dustbin);
        ImageView run = (ImageView) findViewById(R.id.run);
        run.setImageResource(R.drawable.runicon);
        ImageView stop = (ImageView) findViewById(R.id.stop);
        stop.setImageResource(R.drawable.stopicon);
        //update instructions to display the welcome text
        activityGUIUpdater.updateInstructions(getString(R.string.welcome_text));
    }

    public void saveFile(View view){
        EditText fileName = (EditText) findViewById(R.id.fileName);
        EditText authorName = (EditText) findViewById(R.id.authorName);
        EditText emailAddress = (EditText) findViewById(R.id.email);
        EditText description = (EditText) findViewById(R.id.description);
        mainInterpreter.metaInfo.fileName = fileName.getText().toString();
        mainInterpreter.metaInfo.authorName = authorName.getText().toString();
        mainInterpreter.metaInfo.email = emailAddress.getText().toString();
        mainInterpreter.metaInfo.description = description.getText().toString();
        ArrayList<Writable> classesToWrite = new ArrayList<>();
        classesToWrite.add(mainInterpreter.metaInfo);
        for (Predicate predicate: mainInterpreter.predicates
             ) {
            classesToWrite.add(predicate);

        }
        fileManager.createFile(fileName.getText().toString(), classesToWrite);
    }

    /**
     * This class handles the creation and update of UI element in the main activity
     * @author Hong Chin Choong
     * @version 0.1v
     */
    public class MainGUIUpdater implements GUIUpdater {

        /**
         * Updates the text on the instruction view based on the input string
         * @param instruction Instruction to be shown on the instruction view
         * */
        public void updateInstructions(String instruction){
            TextView instructionsText = (TextView) findViewById(R.id.instructions);
            instructionsText.setText(instruction);
        }

        public void showDustbin () {
            ImageView dustbin = (ImageView) findViewById(R.id.dustbin);
            dustbin.setVisibility(View.VISIBLE);
        }

        public void hideDustbin () {
            ImageView dustbin = (ImageView) findViewById(R.id.dustbin);
            dustbin.setVisibility(View.INVISIBLE);
        }

        /**
         * Creates a log entry in the console view on the layout
         * @param logMessage log message to be entered into the console
         */
        public void createConsoleLog (String logMessage) {

            //Gets the current time at the moment of log creation
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss aa");
            String currentTime = dateFormat.format(calendar.getTime());

            //creation and styling of the log before adding it into the console
            String logEntry = "[" + currentTime + "] " + logMessage;
            LinearLayout console = (LinearLayout) findViewById(R.id.console);
            TextView newLog = new TextView(getApplicationContext());
            newLog.setPadding(10, 2, 2, 2);
            newLog.setText(logEntry);
            newLog.setTextColor(Color.parseColor("#757575"));
            console.addView(newLog);
            final ScrollView scrollView = (ScrollView) console.getParent();
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }

        public void removeView (int viewId){
            View toBeRemoved = findViewById(viewId);
            ViewGroup parentView = (ViewGroup) toBeRemoved.getParent();
            parentView.removeView(toBeRemoved);
        }

        public void disableEnableView(int viewId, boolean enable){
            View v = findViewById(viewId);
            v.setEnabled(enable);
        }

        public void hideView(int viewId){
            View v = findViewById(viewId);
            v.setVisibility(View.INVISIBLE);
        }

        public void showView(int viewId){
            View v = findViewById(viewId);
            v.setVisibility(View.VISIBLE);
        }

        /**
         * Generate specific UI elements for the activity based on the parameter
         * @param uiType type of UI element to be created
         */
        public int generateUI (String uiType){
            //Creation of UI element for playground
            if (uiType.equalsIgnoreCase(getString(R.string.predicate))){
                //Creation of UI elements
                LinearLayout container = (LinearLayout) findViewById(R.id.playground);
                LinearLayout newLayout = new LinearLayout(getApplicationContext());
                int newId = View.generateViewId();
                newLayout.setId(newId);
                newLayout.setPadding(20, 5, 20, 5);
                RelativeLayout newLayout2 = new RelativeLayout(getApplicationContext());
                newLayout2.setId(View.generateViewId());
                RelativeLayout newLayout3 = new RelativeLayout(getApplicationContext());
                newLayout3.setId(View.generateViewId());
                EditText predicateText = new EditText(getApplicationContext());
                EditText parameterText = new EditText(getApplicationContext());
                int paramId = View.generateViewId();
                int predicateNameId = View.generateViewId();
                predicateText.setId(predicateNameId);
                parameterText.setId(paramId);
                ImageButton additionButton = new ImageButton(getApplicationContext());
                additionButton.setLayoutParams(new LinearLayout.LayoutParams(43, 43));

                Constant newConstant = new Constant(paramId);
                Predicate newPredicate = new Predicate(newId, newConstant);
                newPredicate.nameId = predicateNameId;
                mainInterpreter.addPredicate(newPredicate);

                //Styling of UI elements
                predicateText.setPadding(5, 5, 5, 5);
                predicateText.setMinimumWidth(250);
                predicateText.setHint(getString(R.string.predicate));
                predicateText.setBackgroundColor(Color.parseColor("#ff99cc00"));
                predicateText.setImeOptions(EditorInfo.IME_ACTION_DONE);
                predicateText.setSingleLine(true);
                parameterText.setPadding(5, 5, 5, 5);
                parameterText.setMinimumWidth(250);
                parameterText.setHint(getString(R.string.parameter));
                parameterText.setBackgroundColor(Color.parseColor("#FF80AB"));
                parameterText.setImeOptions(EditorInfo.IME_ACTION_DONE);
                parameterText.setSingleLine(true);
                additionButton.setImageResource(R.drawable.addicon);

                //Assigning listeners to the UI elements
                predicateText.setOnFocusChangeListener(new FocusListener(getString(R.string.predicate_value_console_update), getString(R.string.value_update_instructions), uiType, activityGUIUpdater, mainInterpreter));
                predicateText.setOnLongClickListener(new LongClickToDragPredicateListener(getString(R.string.drag_delete_instructions),activityGUIUpdater));
                predicateText.setOnEditorActionListener(new TextViewKeyboardActionListener(getString(R.string.predicate_value_console_update), uiType, activityGUIUpdater, mainInterpreter));
                parameterText.setOnFocusChangeListener(new FocusListener(getString(R.string.parameter_value_console_update), getString(R.string.value_update_instructions), uiType, activityGUIUpdater, mainInterpreter));
                parameterText.setOnLongClickListener(new LongClickToDragPredicateListener(getString(R.string.drag_delete_instructions),activityGUIUpdater));
                parameterText.setOnEditorActionListener(new TextViewKeyboardActionListener(getString(R.string.parameter_value_console_update), uiType, activityGUIUpdater, mainInterpreter));
                additionButton.setOnClickListener(new ClickToAddListener(getString(R.string.add_new_parameter), activityGUIUpdater, uiType));
                additionButton.setOnLongClickListener(new LongClickToDragPredicateListener(getString(R.string.drag_delete_instructions),activityGUIUpdater));

                RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                newLayout2.addView(predicateText, rp);
                newLayout3.addView(parameterText, rp);
                //Adding the UI elements into the container
                newLayout.addView(newLayout2);
                newLayout.addView(newLayout3);
                newLayout.addView(additionButton);
                newLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                container.addView(newLayout);
                return newId;
            }

            else if(uiType.equalsIgnoreCase(getString(R.string.query))){
                //Creation of UI elements
                RelativeLayout consoleCommandLine = (RelativeLayout) findViewById(R.id.consoleCommandLine);
                LinearLayout newLayout = new LinearLayout(getApplicationContext());
                int newId = View.generateViewId();
                newLayout.setId(newId);
                TextView queryHead = new TextView(getApplicationContext());
                queryHead.setText("?-");
                RelativeLayout newLayout2 = new RelativeLayout(getApplicationContext());
                newLayout2.setId(View.generateViewId());
                RelativeLayout newLayout3 = new RelativeLayout(getApplicationContext());
                newLayout3.setId(View.generateViewId());
                EditText predicateText = new EditText(getApplicationContext());
                EditText parameterText = new EditText(getApplicationContext());
                int paramId = View.generateViewId();
                parameterText.setId(paramId);
                ImageButton additionButton = new ImageButton(getApplicationContext());
                additionButton.setLayoutParams(new LinearLayout.LayoutParams(43, 43));

                Constant newConstant = new Constant(paramId);
                Predicate queryPred = new Predicate(newId, newConstant);
                mainInterpreter.query = queryPred;

                //Styling of UI elements
                predicateText.setPadding(5, 5, 5, 5);
                predicateText.setMinimumWidth(250);
                predicateText.setHint(getString(R.string.predicate));
                predicateText.setBackgroundColor(Color.parseColor("#ff99cc00"));
                predicateText.setImeOptions(EditorInfo.IME_ACTION_DONE);
                predicateText.setSingleLine(true);
                parameterText.setPadding(5, 5, 5, 5);
                parameterText.setMinimumWidth(250);
                parameterText.setHint(getString(R.string.parameter));
                parameterText.setBackgroundColor(Color.parseColor("#FF80AB"));
                parameterText.setImeOptions(EditorInfo.IME_ACTION_DONE);
                parameterText.setSingleLine(true);
                additionButton.setImageResource(R.drawable.addicon);

                predicateText.setOnFocusChangeListener(new FocusListener(getString(R.string.predicate_value_console_update), getString(R.string.value_update_instructions), uiType, activityGUIUpdater, mainInterpreter));
                predicateText.setOnEditorActionListener(new TextViewKeyboardActionListener(getString(R.string.predicate_value_console_update), uiType, activityGUIUpdater, mainInterpreter));
                parameterText.setOnFocusChangeListener(new FocusListener(getString(R.string.predicate_value_console_update), getString(R.string.value_update_instructions), uiType, activityGUIUpdater, mainInterpreter));
                parameterText.setOnEditorActionListener(new TextViewKeyboardActionListener(getString(R.string.parameter_value_console_update), uiType, activityGUIUpdater, mainInterpreter));
                additionButton.setOnClickListener(new ClickToAddListener(getString(R.string.add_new_parameter), activityGUIUpdater, uiType));

                RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                newLayout2.addView(predicateText, rp);
                newLayout3.addView(parameterText, rp);
                //Adding the UI elements into the container
                newLayout.addView(queryHead);
                newLayout.addView(newLayout2);
                newLayout.addView(newLayout3);
                newLayout.addView(additionButton);
                newLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                consoleCommandLine.addView(newLayout);

            }
            return 0;
        }

        public int generateUI(int id, String uiType){
            LinearLayout existingLayout = (LinearLayout) findViewById(id);
            RelativeLayout newLayout = new RelativeLayout(getApplicationContext());
            int layoutId = View.generateViewId();
            newLayout.setId(layoutId)
            ;
            EditText parameterText = new EditText(getApplicationContext());
            int paramId = View.generateViewId();
            parameterText.setId(paramId);
            ImageView close = new ImageView(getApplicationContext());
            int imageId = View.generateViewId();
            close.setId(imageId);
            close.setImageResource(R.drawable.xicon);

            Constant newConstant = new Constant(paramId);

            if(uiType.equalsIgnoreCase("Predicate")) {
                Predicate existingPred = mainInterpreter.getPredicate(id);
                existingPred.addAttribute(newConstant);
            }
            else if(uiType.equalsIgnoreCase("Query")){
                Predicate query = mainInterpreter.query;
                query.addAttribute(newConstant);
            }

            parameterText.setPadding(5, 5, 5, 5);
            parameterText.setMinimumWidth(250);
            parameterText.setHint(getString(R.string.parameter));
            parameterText.setBackgroundColor(Color.parseColor("#FF80AB"));
            parameterText.setImeOptions(EditorInfo.IME_ACTION_DONE);
            parameterText.setSingleLine(true);

            if(uiType.equalsIgnoreCase("Predicate")) {
                parameterText.setOnFocusChangeListener(new FocusListener(getString(R.string.parameter_value_console_update), getString(R.string.value_update_instructions), uiType, activityGUIUpdater, mainInterpreter));
                parameterText.setOnLongClickListener(new LongClickToDragPredicateListener(getString(R.string.drag_delete_instructions), activityGUIUpdater));
                parameterText.setOnEditorActionListener(new TextViewKeyboardActionListener(getString(R.string.predicate_value_console_update), uiType, activityGUIUpdater, mainInterpreter));
                close.setOnClickListener(new ClickToDelete(getString(R.string.delete_parameter), activityGUIUpdater, mainInterpreter, uiType));
            }
            else if(uiType.equalsIgnoreCase("Query")){
                parameterText.setOnFocusChangeListener(new FocusListener(getString(R.string.parameter_value_console_update), getString(R.string.value_update_instructions), uiType, activityGUIUpdater, mainInterpreter));
                parameterText.setOnEditorActionListener(new TextViewKeyboardActionListener(getString(R.string.predicate_value_console_update), uiType, activityGUIUpdater, mainInterpreter));
                close.setOnClickListener(new ClickToDelete(getString(R.string.delete_parameter), activityGUIUpdater, mainInterpreter, uiType));
            }

            RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            rp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            RelativeLayout.LayoutParams rp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            rp2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            rp2.addRule(RelativeLayout.CENTER_VERTICAL);
            rp2.leftMargin = 210;
            newLayout.addView(parameterText, rp);
            newLayout.addView(close, rp2);
            int count = existingLayout.getChildCount();
            existingLayout.addView(newLayout, count-1);
            return paramId;
        }

        public void updateUIValue (int id, String value){
            EditText editText = (EditText) findViewById(id);
            editText.setText(value);
        }
    }
}
