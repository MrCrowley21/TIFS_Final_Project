package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.EditText;
import android.text.TextUtils;
import android.widget.Switch;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    public int spinner = 0;//note the current value of spinner
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    public Switch mySwitch;
    //set the spinner
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //tur on/off dark mode
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.darktheme);
        }
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner mySpinner = (Spinner) findViewById(R.id.spinner1);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this,
                R.layout.spinner, getResources().getStringArray(R.array.names)) {

            //set the default value of the spinner
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            //set colors vor values of the spinner
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.rgb(169, 169, 169));
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        //spinner options (set the value of variable)
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    spinner = 1;
                } else if (i == 2) {
                    spinner = 2;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //button actions
        final EditText minutes = (EditText) findViewById(R.id.userMinutes);
        final TextView result = (TextView) findViewById(R.id.result);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String min = minutes.getText().toString();
                int price;

                String regexStr = "^[1-9]\\d*(\\.\\d+)?$";//describe the required type of user's input

                //describe the case when there is no minutes input
                if ((TextUtils.isEmpty(min)) || !(min.trim().matches(regexStr))) {
                    minutes.setError("Input talked minutes");
                    minutes.requestFocus();
                    if (spinner == 0) {
                        TextView errorTextview = (TextView) mySpinner.getSelectedView();
                        errorTextview.setError("Choose the package");
                        errorTextview.setTextColor(Color.RED);
                        errorTextview.requestFocus();
                        return;
                    }
                    return;
                }

                //describe the case when the package is not selected
                if (spinner == 0) {
                    TextView errorTextview = (TextView) mySpinner.getSelectedView();
                    errorTextview.setError("Choose the package");
                    errorTextview.setTextColor(Color.RED);
                    errorTextview.requestFocus();
                    return;
                }

                float talked = Float.parseFloat(min);//user's input value (float)

                //describe the standard package
                if (spinner == 1) {
                    price = 24;
                    if (talked <= 300)
                        result.setText(String.valueOf("To pay: " + price + " lei"));

                    else
                        result.setText(String.valueOf("To pay: " + String.format("%.2f", (price + (talked - 300) * 9.6 / 100)) + " lei"));
                }

                //describe the econom package
                else if (spinner == 2) {
                    price = 6;
                    if (talked <= 200)
                        result.setText(String.valueOf("To pay: " + price + " lei"));

                    else
                        result.setText(String.valueOf("To pay: " + String.format("%.2f", (price + (talked - 200) * 24 / 100)) + " lei"));
                }
            }
        });

        //check the current mode (for dark mode)
        mySwitch = (Switch)findViewById(R.id.mySwitch);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            mySwitch.setChecked(true);
        }
        if (mySwitch != null) {
            mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    }
                    else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    }
                    restartApp();
                }
            });
        }

    }

    public void restartApp(){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

}