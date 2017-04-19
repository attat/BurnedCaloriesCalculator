package com.tat.burnedcaloriescalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import org.w3c.dom.Text;

public class BurnedCaloriesCalculatorActivity extends AppCompatActivity
        implements OnEditorActionListener, OnSeekBarChangeListener, View.OnKeyListener, AdapterView.OnItemSelectedListener {

    private EditText personNameTextFieldLabel;
    private EditText decimalTextFieldLabel;
    private SeekBar seekBar;
    private Spinner spinner;
    private Spinner spinner2;
    private TextView weightTextViewLabel;
    private TextView milesRanSetText;
    private TextView caloriesBurnedSetText;
    private TextView bmiSetText;

    private SharedPreferences savedValues;

    private float caloriesBurned;
    private int bmi;
    private int weight = 0;
    private int milesRan = 0;
    private int feet= 1;
    private int inches= 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burned_calories_calculator);


        //reference to widgets
        decimalTextFieldLabel = (EditText) findViewById(R.id.decimalTextFieldLabel);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        Integer[] items = new Integer[]{0,1,2,3,4,5,6,7,8,9,10,11};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, items);
        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        // set listeners
        decimalTextFieldLabel.setOnEditorActionListener(this);
        seekBar.setOnSeekBarChangeListener(this);
        //seekBar.setOnKeyListener(this);

        spinner.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

    }


    public void onPause() {
        // save the instance variables
        Editor editor = savedValues.edit();
        editor.putInt("weight", weight);
        editor.putInt("milesRan", milesRan);
        editor.putInt("feet", feet);
        editor.putInt("inches", inches);
        editor.commit();

        super.onPause();
    }
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
    {
        if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED)
        {
            calculateAndDisplay();
        }
        return false;
    }

    private void calculateAndDisplay() {



        caloriesBurned =(float) (0.75 * weight * milesRan);
        bmi = (weight * 703) / ((12 * feet + inches) * (12 * feet + inches));

        //caloriesBurnedSetText.setText(String.valueOf(caloriesBurned));


        //bmiSetText.setText(bmi);
        milesRan = seekBar.getProgress();
        System.out.println("THIS IS MILES RAN " + milesRan);
        milesRanSetText.setText(String.valueOf(milesRan));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
        milesRanSetText.setText(String.valueOf(progress));
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        calculateAndDisplay();
    }

    public void onResume()
    {
        super.onResume();

        weight = savedValues.getInt("weight", weight);
        milesRan = savedValues.getInt("milesRan", milesRan);
        feet = savedValues.getInt("feet", feet);
        inches = savedValues.getInt("inches", feet);

        spinner.setSelection(feet);
        spinner2.setSelection(inches);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        feet = position;
        inches = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
