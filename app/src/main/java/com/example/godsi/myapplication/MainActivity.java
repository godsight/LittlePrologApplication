package com.example.godsi.myapplication;

        import android.app.Activity;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import java.text.SimpleDateFormat;
        import java.util.Calendar;

/**
 * This class handles the initialization of the main activity of the application
 * @author Hong Chin Choong
 * @version 0.1v
 */

public class MainActivity extends Activity {
    //initialize the GUI updater for the activity
    MainGUIUpdater activityGUIUpdater = new MainGUIUpdater();
    MainInterpreter mainInterpreter = new MainInterpreter();

    /** Called when the activity is first created to initialize it.
     * @param savedInstanceState bundle containing data stored when the activity was suspended
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sets the layout of the activity
        setContentView(R.layout.main);

        //Assigning the listeners for the views in the activity
        findViewById(R.id.predicate).setOnTouchListener(new TouchToDragListener(getString(R.string.predicate_touch_instructions),activityGUIUpdater,getString(R.string.predicate)));
        findViewById(R.id.variable).setOnTouchListener(new TouchToDragListener(getString(R.string.variable_touch_instructions),activityGUIUpdater,getString(R.string.variable)));
        findViewById(R.id.componentText).setOnTouchListener(new InstructionUpdateListener(getString(R.string.component_touch_instructions),activityGUIUpdater));
        findViewById(R.id.playgroundText).setOnTouchListener(new InstructionUpdateListener(getString(R.string.playground_touch_instructions),activityGUIUpdater));
        findViewById(R.id.variablesText).setOnTouchListener(new InstructionUpdateListener(getString(R.string.variablelist_touch_instructions),activityGUIUpdater));
        findViewById(R.id.consoleText).setOnTouchListener(new InstructionUpdateListener(getString(R.string.console_touch_instructions),activityGUIUpdater));
        findViewById(R.id.playgroundContainer).setOnDragListener(new ElementDragListener(getString(R.string.predicate), getString(R.string.predicate_console_add), getString(R.string.predicate_drag_instructions), getString(R.string.predicate_drag_error), activityGUIUpdater));
        findViewById(R.id.playground).setOnDragListener(new ElementDragListener(getString(R.string.predicate), getString(R.string.predicate_console_add), getString(R.string.predicate_drag_instructions), getString(R.string.predicate_drag_error),activityGUIUpdater));
        findViewById(R.id.variablesContainer).setOnDragListener(new ElementDragListener(getString(R.string.variable),getString(R.string.variable_console_add),getString(R.string.variable_drag_instructions),getString(R.string.variable_drag_error), activityGUIUpdater));
        findViewById(R.id.variables).setOnDragListener(new ElementDragListener(getString(R.string.variable),getString(R.string.variable_console_add),getString(R.string.variable_drag_instructions),getString(R.string.variable_drag_error), activityGUIUpdater));
        findViewById(R.id.dustbin).setOnDragListener(new DragToDeleteListener(getString(R.string.delete_success_console),activityGUIUpdater));

        ImageView image = (ImageView) findViewById(R.id.dustbin);
        image.setImageResource(R.drawable.dustbin);
        //update instructions to display the welcome text
        activityGUIUpdater.updateInstructions(getString(R.string.welcome_text));
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
        }

        public void removeView (int viewId){
            View toBeRemoved = findViewById(viewId);
            ViewGroup parentView = (ViewGroup) toBeRemoved.getParent();
            parentView.removeView(toBeRemoved);
        }
        /**
         * Generate specific UI elements for the activity based on the parameter
         * @param uiType type of UI element to be created
         */
        public void generateUI (String uiType){
            //Creation of UI element for variable list
            if (uiType.equalsIgnoreCase(getString(R.string.variable))){
                //Creation of UI elements
                LinearLayout container = (LinearLayout) findViewById(R.id.variables);
                LinearLayout newLayout = new LinearLayout(getApplicationContext());
                newLayout.setPadding(20, 5, 5, 5);
                int newId = View.generateViewId();
                newLayout.setId(newId);
                EditText variableText = new EditText(getApplicationContext());
                EditText valueText = new EditText(getApplicationContext());

                //Styling of UI elements
                variableText.setPadding(5, 5, 5, 5);
                variableText.setMinimumWidth(10);
                variableText.setHint(getString(R.string.name));
                variableText.setBackgroundColor(Color.parseColor("#ffaa66cc"));
                valueText.setPadding(5, 5, 5, 5);
                valueText.setMinimumWidth(10);
                valueText.setHint(getString(R.string.value));
                valueText.setBackgroundColor(Color.parseColor("#FF80AB"));

                //Assigning listeners to the UI elements
                variableText.setOnLongClickListener(new LongClickToDragListener(getString(R.string.variable_use_instructions),activityGUIUpdater));
                variableText.addTextChangedListener(new InputTextWatcher(getString(R.string.variable_name_console_update),activityGUIUpdater, mainInterpreter, variableText, uiType));
                variableText.setOnFocusChangeListener(new FocusListener(getString(R.string.value_update_instructions), activityGUIUpdater));
                valueText.setOnLongClickListener(new LongClickToDragListener(getString(R.string.variable_use_instructions),activityGUIUpdater));
                valueText.addTextChangedListener(new InputTextWatcher(getString(R.string.variable_value_console_update),activityGUIUpdater, mainInterpreter, valueText, uiType));
                valueText.setOnFocusChangeListener(new FocusListener(getString(R.string.value_update_instructions), activityGUIUpdater));

                //Adding the UI elements into the container
                newLayout.addView(variableText);
                newLayout.addView(valueText);
                newLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                container.addView(newLayout);
            }
            //Creation of UI element for playground
            else if (uiType.equalsIgnoreCase(getString(R.string.predicate))){
                //Creation of UI elements
                LinearLayout container = (LinearLayout) findViewById(R.id.playground);
                LinearLayout newLayout = new LinearLayout(getApplicationContext());
                int newId = View.generateViewId();
                newLayout.setId(newId);
                newLayout.setPadding(20, 5, 5, 5);
                EditText predicateText = new EditText(getApplicationContext());
                EditText parameterText = new EditText(getApplicationContext());
                int paramId = View.generateViewId();
                parameterText.setId(paramId);

                Constant newConstant = new Constant(paramId);
                Predicate newPredicate = new Predicate(newId, newConstant);
                mainInterpreter.AddPredicate(newPredicate);

                //Styling of UI elements
                predicateText.setPadding(5, 5, 5, 5);
                predicateText.setMinimumWidth(250);
                predicateText.setHint(getString(R.string.predicate));
                predicateText.setBackgroundColor(Color.parseColor("#ff99cc00"));
                parameterText.setPadding(5, 5, 5, 5);
                parameterText.setMinimumWidth(250);
                parameterText.setHint(getString(R.string.parameter));
                parameterText.setBackgroundColor(Color.parseColor("#FF80AB"));

                //Assigning listeners to the UI elements
                predicateText.addTextChangedListener(new InputTextWatcher(getString(R.string.predicate_value_console_update),activityGUIUpdater, mainInterpreter, predicateText, uiType));
                predicateText.setOnFocusChangeListener(new FocusListener(getString(R.string.value_update_instructions), activityGUIUpdater));
                predicateText.setOnLongClickListener(new LongClickToDragListener(getString(R.string.drag_delete_instructions),activityGUIUpdater));
                parameterText.addTextChangedListener(new InputTextWatcher(getString(R.string.parameter_value_console_update), activityGUIUpdater, mainInterpreter, parameterText, uiType));
                parameterText.setOnFocusChangeListener(new FocusListener(getString(R.string.value_update_instructions), activityGUIUpdater));
                parameterText.setOnLongClickListener(new LongClickToDragListener(getString(R.string.drag_delete_instructions),activityGUIUpdater));

                //Adding the UI elements into the container
                newLayout.addView(predicateText);
                newLayout.addView(parameterText);
                newLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                container.addView(newLayout);
            }
        }
    }
}
