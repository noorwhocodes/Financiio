package com.ass2.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

public class TransactionAdd extends AppCompatActivity {
    Context c;
    TextView thedate;
    Button date_button, time_button;
    int hour, minute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_add);

        thedate=findViewById(R.id.thedate);
        date_button=findViewById(R.id.trans_date_button);
        time_button=findViewById(R.id.trans_time_button);

        Spinner spin=(Spinner)findViewById(R.id.spinner_category);

        ArrayAdapter<String> MyAdapter=new ArrayAdapter<String>(TransactionAdd.this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.categories));
        MyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(MyAdapter);

        Intent incomingdate=getIntent();
        String date=incomingdate.getStringExtra("date");
        thedate.setText(date);

        date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TransactionAdd.this, Calendar.class);
                startActivity(intent);
            }
        });
    }
    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(android.widget.TimePicker timePicker, int selectedhour, int selectedminute) {
                hour = selectedhour;
                minute = selectedminute;
                time_button.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };
        int style = AlertDialog.THEME_HOLO_LIGHT;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, timeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
        }
}