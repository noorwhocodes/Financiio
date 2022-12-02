package com.ass2.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;

import java.util.Locale;

public class Calendar extends AppCompatActivity {
    CalendarView calendarView;
    int hour, minute;
    String dateNTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView=findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                //yyyy/mm/dd
                dateNTime=i +"/"+(i1+1)+"/"+i2;
                popTimePicker();
            }
        });
    }

    public void popTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(android.widget.TimePicker timePicker, int selectedhour, int selectedminute) {
                hour = selectedhour;
                minute = selectedminute;
                String temp=String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
                dateNTime += "_" +temp;

                Intent intent=new Intent(Calendar.this,TransactionAdd.class);
                intent.putExtra("date",dateNTime);
                startActivity(intent);
                finish();
            }
        };
        int style = AlertDialog.THEME_HOLO_LIGHT;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, timeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }
}