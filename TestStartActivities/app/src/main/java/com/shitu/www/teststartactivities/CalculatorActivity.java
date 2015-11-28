package com.shitu.www.teststartactivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class CalculatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        final EditText n1 = (EditText)findViewById(R.id.num1);
        final EditText n2 = (EditText)findViewById(R.id.num2);

        final TextView sum = (TextView) findViewById(R.id.sum);

        Button.OnClickListener listener = new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    float f1 = Float.valueOf(n1.getText().toString());
                    float f2 = Float.valueOf(n2.getText().toString());

                    sum.setText(Float.toString(f1+f2));
                } catch (NumberFormatException e) {
                    n1.setText("0");
                    n1.requestFocus();
                    n2.setText("0");
                    sum.setText(Integer.toString(0));
                    Toast.makeText(getApplicationContext(), "Input number is illegal.", Toast.LENGTH_LONG).show();
                }
            }
        };

        Button bttn = (Button) findViewById(R.id.button);
        bttn.setOnClickListener(listener);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent welActvty = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(welActvty);
                finish();
            }
        });
    }

}
