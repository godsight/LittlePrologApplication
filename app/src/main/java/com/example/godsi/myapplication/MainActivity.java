package com.example.godsi.myapplication;

        import android.app.Activity;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.view.Gravity;
        import android.view.MotionEvent;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.inputmethod.EditorInfo;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.RelativeLayout;
        import android.widget.ScrollView;
        import android.widget.TextView;

        import org.w3c.dom.Text;

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
            activityGUIUpdater.createConsoleLog(getString(R.string.file_loaded));
        }

        //Assigning the listeners for the views in the activity
        findViewById(R.id.predicate).setOnTouchListener(new TouchToDragListener(getString(R.string.predicate_touch_instructions),activityGUIUpdater,getString(R.string.predicate)));
        findViewById(R.id.dustbin).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                activityGUIUpdater.updateInstructions(getString(R.string.drag_delete_instructions));
                return false;
            }
        });
        findViewById(R.id.mathComputation).setOnTouchListener(new TouchToDragListener(getString(R.string.mathComp_touch_instructions), activityGUIUpdater, getString(R.string.mathematical_rule)));
        findViewById(R.id.read).setOnTouchListener(new TouchToDragListener(getString(R.string.rule_param_touch_instructions), activityGUIUpdater, getString(R.string.read)));
        findViewById(R.id.write).setOnTouchListener(new TouchToDragListener(getString(R.string.rule_param_touch_instructions), activityGUIUpdater, getString(R.string.write)));
        findViewById(R.id.operator).setOnTouchListener(new TouchToDragListener(getString(R.string.operator_touch_instructions), activityGUIUpdater, getString(R.string.operator)));
        findViewById(R.id.plus).setOnTouchListener(new TouchToDragListener(getString(R.string.operator_type_touch_instructions), activityGUIUpdater, getString(R.string.plusSymbol)));
        findViewById(R.id.minus).setOnTouchListener(new TouchToDragListener(getString(R.string.operator_touch_instructions), activityGUIUpdater, getString(R.string.minusSymbol)));
        findViewById(R.id.multiply).setOnTouchListener(new TouchToDragListener(getString(R.string.operator_touch_instructions), activityGUIUpdater, getString(R.string.multiplySymbol)));
        findViewById(R.id.divide).setOnTouchListener(new TouchToDragListener(getString(R.string.operator_touch_instructions), activityGUIUpdater, getString(R.string.divideSymbol)));
        findViewById(R.id.doubleEqual).setOnTouchListener(new TouchToDragListener(getString(R.string.operator_touch_instructions), activityGUIUpdater, getString(R.string.doubleEqualSymbol)));
        findViewById(R.id.less).setOnTouchListener(new TouchToDragListener(getString(R.string.operator_touch_instructions), activityGUIUpdater, getString(R.string.lessSymbol)));
        findViewById(R.id.more).setOnTouchListener(new TouchToDragListener(getString(R.string.operator_touch_instructions), activityGUIUpdater, getString(R.string.moreSymbol)));
        findViewById(R.id.equal).setOnTouchListener(new TouchToDragListener(getString(R.string.operator_touch_instructions), activityGUIUpdater, getString(R.string.equalSymbol)));
        findViewById(R.id.lessOrEqual).setOnTouchListener(new TouchToDragListener(getString(R.string.operator_touch_instructions), activityGUIUpdater, getString(R.string.lessOrEqualSymbol)));
        findViewById(R.id.moreOrEqual).setOnTouchListener(new TouchToDragListener(getString(R.string.operator_touch_instructions), activityGUIUpdater, getString(R.string.moreOrEqualSymbol)));
        findViewById(R.id.queryPredicate).setOnTouchListener(new TouchToDragListener(getString(R.string.query_predicate_drag_instructions),activityGUIUpdater, getString(R.string.query_predicate)));
        findViewById(R.id.queryRule).setOnTouchListener(new TouchToDragListener(getString(R.string.query_rule_drag_instructions),activityGUIUpdater, getString(R.string.query_rule)));
        findViewById(R.id.componentText).setOnTouchListener(new InstructionUpdateListener(getString(R.string.component_touch_instructions),activityGUIUpdater));
        findViewById(R.id.playgroundText).setOnTouchListener(new InstructionUpdateListener(getString(R.string.playground_touch_instructions),activityGUIUpdater));
        findViewById(R.id.variablesText).setOnTouchListener(new InstructionUpdateListener(getString(R.string.variablelist_touch_instructions),activityGUIUpdater));
        findViewById(R.id.consoleText).setOnTouchListener(new InstructionUpdateListener(getString(R.string.console_touch_instructions),activityGUIUpdater));
        findViewById(R.id.playgroundContainer).setOnDragListener(new ElementDragListener(activityGUIUpdater));
        findViewById(R.id.playground).setOnDragListener(new ElementDragListener(activityGUIUpdater));
        findViewById(R.id.consoleCommandLine).setOnDragListener(new ElementDragListener(activityGUIUpdater));
        findViewById(R.id.dustbin).setOnDragListener(new DragToDeleteListener(getString(R.string.delete_success_console),activityGUIUpdater, mainInterpreter));
        findViewById(R.id.run).setOnClickListener(new OnClickDo(getString(R.string.run_interpreter), activityGUIUpdater, mainInterpreter, getString(R.string.run), getString(R.string.fail_to_run_interpreter), getResources().getInteger(R.integer.runningState), getResources().getInteger(R.integer.editState)));
        findViewById(R.id.stop).setOnClickListener(new OnClickDo(getString(R.string.stop_interpreter), activityGUIUpdater, mainInterpreter, getString(R.string.stop), "", getResources().getInteger(R.integer.editState), getResources().getInteger(R.integer.editState)));
        findViewById(R.id.next).setOnClickListener(new OnClickDo("", activityGUIUpdater, mainInterpreter, getString(R.string.next), "", getResources().getInteger(R.integer.runningState), getResources().getInteger(R.integer.runningState)));
        findViewById(R.id.enter).setOnClickListener(new OnClickDo("", activityGUIUpdater, mainInterpreter, getString(R.string.enter), "", getResources().getInteger(R.integer.runningState), getResources().getInteger(R.integer.runningState)));
        findViewById(R.id.interpret).setOnClickListener(new OnClickDo("", activityGUIUpdater, mainInterpreter, getString(R.string.interpret), "", getResources().getInteger(R.integer.runningState), getResources().getInteger(R.integer.runningState)));
        findViewById(R.id.input).setOnClickListener(new OnClickDo("", activityGUIUpdater, mainInterpreter, getString(R.string.read_input), "", getResources().getInteger(R.integer.runningState), getResources().getInteger(R.integer.runningState)));

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

        public View getView(int viewId){
            return findViewById(viewId);
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
                predicateText.setOnLongClickListener(new LongClickToDragListener(getString(R.string.drag_delete_instructions),activityGUIUpdater, uiType));
                predicateText.setOnEditorActionListener(new TextViewKeyboardActionListener(getString(R.string.predicate_value_console_update), uiType, activityGUIUpdater, mainInterpreter));
                parameterText.setOnFocusChangeListener(new FocusListener(getString(R.string.parameter_value_console_update), getString(R.string.value_update_instructions), uiType, activityGUIUpdater, mainInterpreter));
                parameterText.setOnLongClickListener(new LongClickToDragListener(getString(R.string.drag_delete_instructions),activityGUIUpdater, uiType));
                parameterText.setOnEditorActionListener(new TextViewKeyboardActionListener(getString(R.string.parameter_value_console_update), uiType, activityGUIUpdater, mainInterpreter));
                additionButton.setOnClickListener(new ClickToAddListener(getString(R.string.add_new_parameter), activityGUIUpdater, uiType, mainInterpreter));
                additionButton.setOnLongClickListener(new LongClickToDragListener(getString(R.string.drag_delete_instructions),activityGUIUpdater, uiType));

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

            else if(uiType.equalsIgnoreCase(getString(R.string.mathematical_rule))){
                //Creation of UI element, mathematical rule in coding playground
                LinearLayout container = (LinearLayout) findViewById(R.id.playground);
                LinearLayout newLayout = new LinearLayout(getApplicationContext());
                int newId = View.generateViewId();
                newLayout.setId(newId);
                newLayout.setPadding(20, 5, 20, 5);
                EditText rule = new EditText(getApplicationContext());
                int ruleId = View.generateViewId();
                rule.setId(ruleId);
                TextView symbol = new TextView(getApplicationContext());
                symbol.setLayoutParams(new LinearLayout.LayoutParams(50,43));
                TextView dropSpace = new TextView(getApplicationContext());
                dropSpace.setLayoutParams(new LinearLayout.LayoutParams(50, 43));

                MathematicalComputation mathComp = new MathematicalComputation(newId);
                mainInterpreter.addMathComp(mathComp);

                rule.setPadding(5, 5, 5, 5);
                rule.setMinimumWidth(250);
                rule.setHint(getString(R.string.mathematical_rule));
                rule.setBackgroundColor(Color.parseColor("#0099cc"));
                rule.setImeOptions(EditorInfo.IME_ACTION_DONE);
                rule.setSingleLine(true);
                symbol.setPadding(5,5,5,5);
                symbol.setText(" : - ");
                symbol.setTextColor(Color.BLACK);
                symbol.setBackgroundColor(Color.parseColor("#a9a9a9"));
                symbol.setPadding(5,10,5,5);
                symbol.setGravity(Gravity.CENTER_HORIZONTAL);
                dropSpace.setBackgroundColor(Color.parseColor("#a9a9a9"));
                dropSpace.setPadding(5,10, 5,5 );

                rule.setOnEditorActionListener(new TextViewKeyboardActionListener(getString(R.string.mathComp_value_console_update), uiType, activityGUIUpdater, mainInterpreter));
                rule.setOnFocusChangeListener(new FocusListener(getString(R.string.mathComp_value_console_update), getString(R.string.value_update_instructions), uiType, activityGUIUpdater, mainInterpreter));
                rule.setOnLongClickListener(new LongClickToDragListener(getString(R.string.drag_delete_instructions), activityGUIUpdater, getString(R.string.mathematical_rule)));
                symbol.setOnLongClickListener(new LongClickToDragListener(getString(R.string.drag_delete_instructions), activityGUIUpdater, getString(R.string.mathematical_rule)));
                dropSpace.setOnDragListener(new ElementDragListener(activityGUIUpdater));
                dropSpace.setOnLongClickListener(new LongClickToDragListener(getString(R.string.drag_delete_instructions), activityGUIUpdater, getString(R.string.mathematical_rule)));


                newLayout.addView(rule);
                newLayout.addView(symbol);
                newLayout.addView(dropSpace);
                newLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                container.addView(newLayout);
            }

            else if(uiType.equalsIgnoreCase(getString(R.string.query_predicate))){
                //Creation of UI element in command line
                RelativeLayout consoleCommandLine = (RelativeLayout) findViewById(R.id.consoleCommandLine);
                LinearLayout newLayout = new LinearLayout(getApplicationContext());
                int newId = View.generateViewId();
                newLayout.setId(newId);
                TextView queryHead = new TextView(getApplicationContext());
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
                mainInterpreter.queryPredicate = queryPred;

                //Styling of UI elements
                queryHead.setText("?-");
                queryHead.setTextSize(20);
                queryHead.setPadding(5, 10, 5, 5);
                queryHead.setTextColor(Color.BLACK);
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
                predicateText.setOnLongClickListener(new LongClickToDragListener(getString(R.string.drag_delete_instructions), activityGUIUpdater, getString(R.string.query_predicate)));
                parameterText.setOnFocusChangeListener(new FocusListener(getString(R.string.predicate_value_console_update), getString(R.string.value_update_instructions), uiType, activityGUIUpdater, mainInterpreter));
                parameterText.setOnEditorActionListener(new TextViewKeyboardActionListener(getString(R.string.parameter_value_console_update), uiType, activityGUIUpdater, mainInterpreter));
                additionButton.setOnClickListener(new ClickToAddListener(getString(R.string.add_new_parameter), activityGUIUpdater, uiType, mainInterpreter));

                RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                newLayout2.addView(predicateText, rp);
                newLayout3.addView(parameterText, rp);
                //Adding the UI elements into the container
                newLayout.addView(queryHead);
                newLayout.addView(newLayout2);
                newLayout.addView(newLayout3);
                newLayout.addView(additionButton);
                newLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                RelativeLayout.LayoutParams rp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                rp2.addRule(RelativeLayout.RIGHT_OF, consoleCommandLine.getChildAt(0).getId());
                rp2.addRule(RelativeLayout.CENTER_VERTICAL);
                consoleCommandLine.addView(newLayout, rp2);

            }
            else if(uiType.equalsIgnoreCase(getString(R.string.query_rule))){
                //Creation of UI element, mathematical rule in coding playground
                RelativeLayout container = (RelativeLayout) findViewById(R.id.consoleCommandLine);
                LinearLayout newLayout = new LinearLayout(getApplicationContext());
                int newId = View.generateViewId();
                newLayout.setId(newId);
                newLayout.setPadding(20, 5, 20, 5);
                TextView queryHead = new TextView(getApplicationContext());
                EditText rule = new EditText(getApplicationContext());
                TextView fullStop = new TextView(getApplicationContext());

                MathematicalComputation mathComp = new MathematicalComputation(newId);
                mainInterpreter.queryRule = mathComp;

                queryHead.setPadding(5, 10, 5, 5);
                queryHead.setText("?- ");
                queryHead.setTextColor(Color.BLACK);
                rule.setPadding(5, 5, 5, 5);
                rule.setHint(getString(R.string.mathematical_rule));
                rule.setTextColor(Color.BLACK);
                rule.setBackgroundColor(Color.parseColor("#e6e6e6"));
                rule.setImeOptions(EditorInfo.IME_ACTION_DONE);
                rule.setSingleLine(true);
                fullStop.setPadding(5, 10, 5, 5);
                fullStop.setText(". ");
                fullStop.setBackgroundColor(Color.parseColor("#0099cc"));
                fullStop.setTextColor(Color.WHITE);

                rule.setOnEditorActionListener(new TextViewKeyboardActionListener(getString(R.string.mathComp_value_console_update), uiType, activityGUIUpdater, mainInterpreter));
                rule.setOnFocusChangeListener(new FocusListener(getString(R.string.mathComp_value_console_update), getString(R.string.value_update_instructions), uiType, activityGUIUpdater, mainInterpreter));
                rule.setOnLongClickListener(new LongClickToDragListener(getString(R.string.drag_delete_instructions), activityGUIUpdater, getString(R.string.query_rule)));

                newLayout.addView(queryHead);
                newLayout.addView(rule);
                newLayout.addView(fullStop);
                newLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                rp.addRule(RelativeLayout.RIGHT_OF, R.id.consoleArrow);
                container.addView(newLayout, rp);
            }
            return 0;
        }

        public int generateUI(int id, String uiType){
            if(uiType.equalsIgnoreCase("Predicate") || uiType.equalsIgnoreCase("Query")) {
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

                parameterText.setPadding(5, 5, 5, 5);
                parameterText.setMinimumWidth(250);
                parameterText.setHint(getString(R.string.parameter));
                parameterText.setBackgroundColor(Color.parseColor("#FF80AB"));
                parameterText.setImeOptions(EditorInfo.IME_ACTION_DONE);
                parameterText.setSingleLine(true);

                Constant newConstant = new Constant(paramId);

                if (uiType.equalsIgnoreCase("Predicate")) {
                    Predicate existingPred = mainInterpreter.getPredicate(id);
                    existingPred.addAttribute(newConstant);
                    parameterText.setOnFocusChangeListener(new FocusListener(getString(R.string.parameter_value_console_update), getString(R.string.value_update_instructions), uiType, activityGUIUpdater, mainInterpreter));
                    parameterText.setOnLongClickListener(new LongClickToDragListener(getString(R.string.drag_delete_instructions), activityGUIUpdater, uiType));
                    parameterText.setOnEditorActionListener(new TextViewKeyboardActionListener(getString(R.string.predicate_value_console_update), uiType, activityGUIUpdater, mainInterpreter));
                    close.setOnClickListener(new ClickToDelete(getString(R.string.delete_parameter), activityGUIUpdater, mainInterpreter, uiType));
                } else if (uiType.equalsIgnoreCase("Query")) {
                    Predicate query = mainInterpreter.queryPredicate;
                    query.addAttribute(newConstant);
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
                existingLayout.addView(newLayout, count - 1);
                return paramId;
            }
            else if(uiType.equalsIgnoreCase("Operator")){
                LinearLayout existingLayout = (LinearLayout) findViewById(id);

                TextView openBracket = new TextView(getApplicationContext());
                TextView closeBracket = new TextView(getApplicationContext());
                TextView openSquareBracket = new TextView(getApplicationContext());
                TextView closeSquareBracket = new TextView(getApplicationContext());
                TextView operator = new TextView(getApplicationContext());
                operator.setLayoutParams(new LinearLayout.LayoutParams(43, 43));
                int opId = View.generateViewId();
                operator.setId(opId);
                EditText value = new EditText(getApplicationContext());
                int valueId = View.generateViewId();
                value.setId(valueId);

                int mathId = ((View) existingLayout.getParent()).getId();
                MathematicalComputation mathematicalComputation = mainInterpreter.getMathComp(mathId);
                Operator existingOp = (Operator) mathematicalComputation.getAttribute(id);
                OperatorType newOperatorType = new OperatorType(opId);
                Constant newConstant = new Constant(valueId);
                existingOp.addOperatorParam(newOperatorType);
                existingOp.addOperatorParam(newConstant);

                openBracket.setPadding(5,10,5,5);
                openBracket.setBackgroundColor(Color.parseColor("#cc00cc"));
                openBracket.setText(R.string.openBracket);
                openBracket.setTextColor(Color.WHITE);
                closeBracket.setPadding(5,10,5,5);
                closeBracket.setBackgroundColor(Color.parseColor("#cc00cc"));
                closeBracket.setText(R.string.closeBraket);
                closeBracket.setTextColor(Color.WHITE);
                openSquareBracket.setPadding(5,10,5,5);
                openSquareBracket.setBackgroundColor(Color.parseColor("#cc00cc"));
                openSquareBracket.setText(R.string.openSquareBracket);
                openSquareBracket.setTextColor(Color.WHITE);
                closeSquareBracket.setPadding(5,10,5,5);
                closeSquareBracket.setBackgroundColor(Color.parseColor("#cc00cc"));
                closeSquareBracket.setText(R.string.closeSquareBracket);
                closeSquareBracket.setTextColor(Color.WHITE);
                operator.setPadding(5,10,5,5);
                operator.setBackgroundColor(Color.parseColor("#cc00cc"));
                operator.setHint("OP");
                operator.setBackgroundColor(Color.parseColor("#e6e6e6"));
                operator.setTextColor(Color.BLACK);
                operator.setGravity(Gravity.CENTER_HORIZONTAL);
                value.setPadding(5, 5, 5, 5);
                value.setHint("val");
                value.setBackgroundColor(Color.parseColor("#e6e6e6"));
                value.setImeOptions(EditorInfo.IME_ACTION_DONE);
                value.setSingleLine(true);
                value.setTextColor(Color.BLACK);
                value.setGravity(Gravity.CENTER_HORIZONTAL);

                openBracket.setOnLongClickListener(new LongClickToDragListener(getString(R.string.drag_delete_instructions), activityGUIUpdater, uiType));
                closeBracket.setOnLongClickListener(new LongClickToDragListener(getString(R.string.drag_delete_instructions), activityGUIUpdater, uiType));
                openSquareBracket.setOnLongClickListener(new LongClickToDragListener(getString(R.string.drag_delete_instructions), activityGUIUpdater, uiType));
                closeSquareBracket.setOnLongClickListener(new LongClickToDragListener(getString(R.string.drag_delete_instructions), activityGUIUpdater, uiType));
                value.setOnFocusChangeListener(new FocusListener(getString(R.string.constant_value_console_update), getString(R.string.value_update_instructions), getString(R.string.mathematical_rule), activityGUIUpdater, mainInterpreter));
                value.setOnEditorActionListener(new TextViewKeyboardActionListener(getString(R.string.constant_value_console_update), getString(R.string.mathematical_rule), activityGUIUpdater, mainInterpreter));
                value.setOnLongClickListener(new LongClickToDragListener(getString(R.string.drag_delete_instructions), activityGUIUpdater, uiType));
                operator.setOnDragListener(new ElementDragListener(activityGUIUpdater));
                operator.setOnLongClickListener(new LongClickToDragListener(getString(R.string.drag_delete_instructions), activityGUIUpdater, uiType));

                int count = existingLayout.getChildCount();
                existingLayout.addView(openSquareBracket, count - 2);
                existingLayout.addView(operator, count - 1);
                existingLayout.addView(closeSquareBracket, count);
                existingLayout.addView(openBracket, count + 1);
                existingLayout.addView(value, count + 2);
                existingLayout.addView(closeBracket, count + 3);
            }
            return 0;
        }

        public void generateUIForMathComp(int id, String uiType){
            if(uiType.equalsIgnoreCase("Read") || uiType.equalsIgnoreCase("Write")) {
                LinearLayout existingLayout = (LinearLayout) findViewById(id);
                LinearLayout newLayout = new LinearLayout(getApplicationContext());
                int layoutId = View.generateViewId();
                newLayout.setId(layoutId);
                EditText readTextFront = new EditText(getApplicationContext());
                EditText param = new EditText(getApplicationContext());
                int paramId = View.generateViewId();
                param.setId(paramId);
                EditText readTextEnd = new EditText(getApplicationContext());

                if (uiType.equalsIgnoreCase("Read")) {
                    readTextFront.setText(getString(R.string.read) + " ( ");
                    Read newRead = new Read(paramId);
                    MathematicalComputation existingMathComp = mainInterpreter.getMathComp(id);
                    existingMathComp.addParam(newRead);
                    param.setHint("readArg");
                    param.setOnEditorActionListener(new TextViewKeyboardActionListener(getString(R.string.read_value_console_update), getString(R.string.mathematical_rule), activityGUIUpdater, mainInterpreter));
                    param.setOnFocusChangeListener(new FocusListener(getString(R.string.read_value_console_update), getString(R.string.value_update_instructions), getString(R.string.mathematical_rule), activityGUIUpdater, mainInterpreter));
                } else if (uiType.equalsIgnoreCase("Write")) {
                    readTextFront.setText(getString(R.string.write) + " ( ");
                    Write newWrite = new Write(paramId);
                    MathematicalComputation existingMathComp = mainInterpreter.getMathComp(id);
                    existingMathComp.addParam(newWrite);
                    param.setHint("writeArg");
                    param.setOnEditorActionListener(new TextViewKeyboardActionListener(getString(R.string.write_value_console_update), getString(R.string.mathematical_rule), activityGUIUpdater, mainInterpreter));
                    param.setOnFocusChangeListener(new FocusListener(getString(R.string.write_value_console_update), getString(R.string.value_update_instructions), getString(R.string.mathematical_rule), activityGUIUpdater, mainInterpreter));
                }
                readTextFront.setPadding(5, 5, 5, 5);
                readTextFront.setBackgroundColor(Color.parseColor("#ff669900"));
                readTextEnd.setEnabled(false);
                param.setPadding(5, 5, 5, 5);
                param.setBackgroundColor(Color.parseColor("#e6e6e6"));
                param.setImeOptions(EditorInfo.IME_ACTION_DONE);
                param.setSingleLine(true);
                param.setTextColor(Color.BLACK);
                readTextEnd.setId(View.generateViewId());
                readTextEnd.setText(" ),");
                readTextEnd.setPadding(5, 5, 5, 5);
                readTextEnd.setBackgroundColor(Color.parseColor("#ff669900"));
                readTextEnd.setEnabled(false);
                readTextEnd.setTextColor(Color.WHITE);

                readTextFront.setOnLongClickListener(new LongClickToDragListener(getString(R.string.drag_delete_instructions), activityGUIUpdater, uiType));
                param.setOnLongClickListener(new LongClickToDragListener(getString(R.string.drag_delete_instructions), activityGUIUpdater, uiType));
                readTextEnd.setOnLongClickListener(new LongClickToDragListener(getString(R.string.drag_delete_instructions), activityGUIUpdater, uiType));

                newLayout.addView(readTextFront);
                newLayout.addView(param);
                newLayout.addView(readTextEnd);
                int count = existingLayout.getChildCount();
                existingLayout.addView(newLayout, count - 1);
            }
            else if(uiType.equalsIgnoreCase("Operator")){
                LinearLayout existingLayout = (LinearLayout) findViewById(id);
                LinearLayout newLayout = new LinearLayout(getApplicationContext());
                int layoutId = View.generateViewId();
                newLayout.setId(layoutId);
                TextView openBracket = new TextView(getApplicationContext());
                TextView closeBracket = new TextView(getApplicationContext());
                TextView openSquareBracket = new TextView(getApplicationContext());
                TextView closeSquareBracket = new TextView(getApplicationContext());
                TextView openBracketRight = new TextView(getApplicationContext());
                TextView closeBracketRight = new TextView(getApplicationContext());
                EditText left = new EditText(getApplicationContext());
                int leftId = View.generateViewId();
                left.setId(leftId);
                TextView operator = new TextView(getApplicationContext());
                int opId = View.generateViewId();
                operator.setId(opId);
                operator.setLayoutParams(new LinearLayout.LayoutParams(43, 43));
                EditText right = new EditText(getApplicationContext());
                int rightId = View.generateViewId();
                right.setId(rightId);
                EditText comma = new EditText(getApplicationContext());
                ImageButton additionButton = new ImageButton(getApplicationContext());
                additionButton.setLayoutParams(new LinearLayout.LayoutParams(43, 43));

                Constant newLeft = new Constant(leftId);
                OperatorType newOpType = new OperatorType(opId);
                Constant newRight = new Constant(rightId);
                Operator newOperator = new Operator(layoutId);
                newOperator.addOperatorParam(newLeft);
                newOperator.addOperatorParam(newOpType);
                newOperator.addOperatorParam(newRight);
                MathematicalComputation existingMathComp = mainInterpreter.getMathComp(id);
                existingMathComp.addParam(newOperator);

                openBracket.setPadding(5,10,5,5);
                openBracket.setBackgroundColor(Color.parseColor("#cc00cc"));
                openBracket.setText(R.string.openBracket);
                openBracket.setTextColor(Color.WHITE);
                closeBracket.setPadding(5,10,5,5);
                closeBracket.setBackgroundColor(Color.parseColor("#cc00cc"));
                closeBracket.setText(R.string.closeBraket);
                closeBracket.setTextColor(Color.WHITE);
                openSquareBracket.setPadding(5,10,5,5);
                openSquareBracket.setBackgroundColor(Color.parseColor("#cc00cc"));
                openSquareBracket.setText(R.string.openSquareBracket);
                openSquareBracket.setTextColor(Color.WHITE);
                closeSquareBracket.setPadding(5,10,5,5);
                closeSquareBracket.setBackgroundColor(Color.parseColor("#cc00cc"));
                closeSquareBracket.setText(R.string.closeSquareBracket);
                closeSquareBracket.setTextColor(Color.WHITE);
                openBracketRight.setPadding(5,10,5,5);
                openBracketRight.setBackgroundColor(Color.parseColor("#cc00cc"));
                openBracketRight.setText(R.string.openBracket);
                openBracketRight.setTextColor(Color.WHITE);
                closeBracketRight.setPadding(5,10,5,5);
                closeBracketRight.setBackgroundColor(Color.parseColor("#cc00cc"));
                closeBracketRight.setText(R.string.closeBraket);
                closeBracketRight.setTextColor(Color.WHITE);
                left.setPadding(5, 5, 5, 5);
                left.setHint("val");
                left.setBackgroundColor(Color.parseColor("#e6e6e6"));
                left.setImeOptions(EditorInfo.IME_ACTION_DONE);
                left.setSingleLine(true);
                left.setTextColor(Color.BLACK);
                left.setGravity(Gravity.CENTER_HORIZONTAL);
                operator.setPadding(5,10,5,5);
                operator.setBackgroundColor(Color.parseColor("#cc00cc"));
                operator.setHint("OP");
                operator.setBackgroundColor(Color.parseColor("#e6e6e6"));
                operator.setTextColor(Color.BLACK);
                operator.setGravity(Gravity.CENTER_HORIZONTAL);
                right.setPadding(5, 5, 5, 5);
                right.setHint("val");
                right.setBackgroundColor(Color.parseColor("#e6e6e6"));
                right.setImeOptions(EditorInfo.IME_ACTION_DONE);
                right.setSingleLine(true);
                right.setTextColor(Color.BLACK);
                right.setGravity(Gravity.CENTER_HORIZONTAL);
                comma.setPadding(5,5,5,5);
                comma.setBackgroundColor(Color.parseColor("#cc00cc"));
                comma.setText(", ");
                comma.setTextColor(Color.WHITE);
                comma.setEnabled(false);
                additionButton.setImageResource(R.drawable.addicon);

                openBracket.setOnLongClickListener(new LongClickToDragListener(getString(R.string.drag_delete_instructions), activityGUIUpdater, uiType));
                closeBracket.setOnLongClickListener(new LongClickToDragListener(getString(R.string.drag_delete_instructions), activityGUIUpdater, uiType));
                openBracketRight.setOnLongClickListener(new LongClickToDragListener(getString(R.string.drag_delete_instructions), activityGUIUpdater, uiType));
                closeBracketRight.setOnLongClickListener(new LongClickToDragListener(getString(R.string.drag_delete_instructions), activityGUIUpdater, uiType));
                openSquareBracket.setOnLongClickListener(new LongClickToDragListener(getString(R.string.drag_delete_instructions), activityGUIUpdater, uiType));
                closeSquareBracket.setOnLongClickListener(new LongClickToDragListener(getString(R.string.drag_delete_instructions), activityGUIUpdater, uiType));
                left.setOnFocusChangeListener(new FocusListener(getString(R.string.constant_value_console_update), getString(R.string.value_update_instructions), getString(R.string.mathematical_rule), activityGUIUpdater, mainInterpreter));
                left.setOnEditorActionListener(new TextViewKeyboardActionListener(getString(R.string.constant_value_console_update), getString(R.string.mathematical_rule), activityGUIUpdater, mainInterpreter));
                left.setOnLongClickListener(new LongClickToDragListener(getString(R.string.drag_delete_instructions), activityGUIUpdater, uiType));
                right.setOnFocusChangeListener(new FocusListener(getString(R.string.constant_value_console_update), getString(R.string.value_update_instructions), getString(R.string.mathematical_rule), activityGUIUpdater, mainInterpreter));
                right.setOnEditorActionListener(new TextViewKeyboardActionListener(getString(R.string.constant_value_console_update), getString(R.string.mathematical_rule), activityGUIUpdater, mainInterpreter));
                right.setOnLongClickListener(new LongClickToDragListener(getString(R.string.drag_delete_instructions), activityGUIUpdater, uiType));
                operator.setOnDragListener(new ElementDragListener(activityGUIUpdater));
                operator.setOnLongClickListener(new LongClickToDragListener(getString(R.string.drag_delete_instructions), activityGUIUpdater, uiType));
                additionButton.setOnClickListener(new ClickToAddListener(getString(R.string.operator_console_add), activityGUIUpdater, getString(R.string.operator), mainInterpreter));

                newLayout.addView(openBracket);
                newLayout.addView(left);
                newLayout.addView(closeBracket);
                newLayout.addView(openSquareBracket);
                newLayout.addView(operator);
                newLayout.addView(closeSquareBracket);
                newLayout.addView(openBracketRight);
                newLayout.addView(right);
                newLayout.addView(closeBracketRight);
                newLayout.addView(additionButton);
                newLayout.addView(comma);

                int count = existingLayout.getChildCount();
                existingLayout.addView(newLayout, count - 1);
            }
        }

        public boolean replaceUIValue(int id, String value){
            TextView view = (TextView) findViewById(id);
            view.setText(value);
            if(mainInterpreter.updateMathComp(getString(R.string.mathematical_rule), view)){
                return true;
            }
            return false;
        }

        public void updateUIValue (int id, String value){
            EditText editText = (EditText) findViewById(id);
            editText.setText(value);
        }

        public View getLastConsoleLog(){
            LinearLayout console = (LinearLayout) findViewById(R.id.console);
            return (TextView) console.getChildAt(console.getChildCount() - 1);
        }

        public void updateReadInput(String header, String var){
            LinearLayout readInput = (LinearLayout) findViewById(R.id.readInput);
            TextView readHeader = (TextView) readInput.getChildAt(0);
            readHeader.setText(header);
            TextView input = (TextView) readInput.getChildAt(1);
            input.setHint(var);
            readInput.setVisibility(View.VISIBLE);
        }
    }
}
