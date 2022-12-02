package com.ass2.project;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.StringTokenizer;

public class TransactionAdd extends AppCompatActivity {
    TextView reminder, save;
    Button date_button, uploadPic_button;
    Bitmap bitmap;
    ImageView image;
    String encodedImage, itemID;
    EditText amount, description;
    Spinner category;
    AlarmManager alarmManager;
    PendingIntent pendingIntentAlarm;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_add);

        reminder=findViewById(R.id.transactionAddTVReminder);
        date_button=findViewById(R.id.trans_date_button);
        uploadPic_button=findViewById(R.id.addTransactionBtnUploadPic);
        image=findViewById(R.id.addTransactionImageView);
        save=findViewById(R.id.addTransactionTVSave);
        amount=findViewById(R.id.trans_amount);
        description=findViewById(R.id.trans_description);
        category=findViewById(R.id.spinner_category);

//        Spinner spin=(Spinner)findViewById(R.id.spinner_category);

        createNotificationChannel();

        ArrayAdapter<String> MyAdapter=new ArrayAdapter<String>(TransactionAdd.this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.categories));
        MyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(MyAdapter);

        date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TransactionAdd.this, Calendar.class);
                startActivity(intent);
            }
        });

        uploadPic_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image"), 111);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sqlite
                myDBHelper myDBHelper=new myDBHelper(TransactionAdd.this);
                SQLiteDatabase database=myDBHelper.getWritableDatabase();
                ContentValues contentValues=new ContentValues();
                String text=category.getSelectedItem().toString().trim();
                contentValues.put(dataContract.Data._CATEGORY, text);
                contentValues.put(dataContract.Data._AMOUNT, amount.getText().toString().trim());
                contentValues.put(dataContract.Data._DESCRIPTION, description.getText().toString().trim());
                contentValues.put(dataContract.Data._TIME, reminder.getText().toString().trim());
                contentValues.put(dataContract.Data._IMAGE, encodedImage);
                database.insert(dataContract.Data.TABLE_NAME, null, contentValues);
                database.close();
                myDBHelper.close();
                itemID=getItemID(amount.getText().toString().trim(),text,reminder.getText().toString().trim());

                //sending data to mySQL db
                StringRequest request = new StringRequest(Request.Method.POST
                        , "http://192.168.18.40/project/transactions.php"
                        , new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(TransactionAdd.this, response, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(TransactionAdd.this, home_page.class));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TransactionAdd.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        String categoryStr=category.getSelectedItem().toString().trim();
                        String amountStr=amount.getText().toString().trim();
                        String timeStr=reminder.getText().toString().trim();

                        params.put("itemID", getItemID(amount.getText().toString().trim(),text,reminder.getText().toString().trim()));
//                        Log.d("amount", "getParams: " + amount.getText().toString().trim());
                        params.put("amount", amountStr);
//                        Log.d("amount", "getParams: " + amount.getText().toString().trim());
                        params.put("category", categoryStr);
//                        Log.d("category", "getParams: " + text);
                        params.put("description", description.getText().toString().trim());
//                        Log.d("description", "getParams: " + description.getText().toString().trim());
                        params.put("time",timeStr);
//                        Log.d("time", "getParams: " + reminder.getText().toString().trim());
                        params.put("image",encodedImage.trim());
//                        Log.d("image", "getParams: " + encodedImage.trim());
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(TransactionAdd.this);
                requestQueue.add(request);

                //setting reminder
                setAlarm(getTimeInMiliSec(reminder.getText().toString().trim()));

            }
        });
    }

    private int getTimeInMiliSec(String dateNTime) {

        int miliSec=0;
        Log.d("test 001", "getTimeInMiliSec: "+dateNTime);

        ArrayList<String> setDateNTimeTokens=stringTok(dateNTime, "_");

        //setting current time formate
        SimpleDateFormat time = new SimpleDateFormat("HH:MM");
        String currentTime = time.format(new Date());
        Log.d("test 002", "getTimeInMiliSec: "+currentTime);


        //current time tokens
        ArrayList<String> currentTimeToken=stringTok(currentTime, ":");

        //set time tokens
        ArrayList<String> setTimeToken=stringTok(setDateNTimeTokens.get(1), ":");

        //resultant tokens
        ArrayList<Integer> resultantTime=new ArrayList<Integer>();

        resultantTime.add(Integer.parseInt(setTimeToken.get(0)) - Integer.parseInt(currentTimeToken.get(0)));
        resultantTime.add(Integer.parseInt(setTimeToken.get(1)) - Integer.parseInt(currentTimeToken.get(1)));

        //time in mili sec
        //miliSec to sec to min
        if(resultantTime.get(0)>0 && resultantTime.get(1)>0){
            miliSec += resultantTime.get(1)*60*1000 + resultantTime.get(0)*60*60*1000;
        }
        Log.d("test 003", "getTimeInMiliSec: "+miliSec);


        //setting current date formate
        SimpleDateFormat date = new SimpleDateFormat("yyyy.MM.dd");
        String currentDate = date.format(new Date());
        Log.d("test 004", "getTimeInMiliSec: "+currentDate);


        //current date tokens
        ArrayList<String> currentDateToken=stringTok(currentDate, ".");

        //set date tokens
        ArrayList<String> setDateToken=stringTok(setDateNTimeTokens.get(0), "/");

        //resultant tokens
        ArrayList<Integer> resultantDate=new ArrayList<Integer>();

        resultantDate.add(Integer.parseInt(setDateToken.get(0)) - Integer.parseInt(currentDateToken.get(0)));
        resultantDate.add(Integer.parseInt(setDateToken.get(1)) - Integer.parseInt(currentDateToken.get(1)));
        resultantDate.add(Integer.parseInt(setDateToken.get(2)) - Integer.parseInt(currentDateToken.get(2)));

        if(resultantDate.get(0)>0 && resultantDate.get(1)>0 && resultantDate.get(2)>0){
            miliSec += resultantDate.get(0)*365*24*60*60*1000 +
                    resultantDate.get(1)*30*24*60*60*1000 +
                    resultantDate.get(2)*24*60*60*1000;
        }

        Log.d("test 010", "getTimeInMiliSec: "+miliSec);
        return miliSec;
    }

    private ArrayList<String> stringTok(String s, String delim){
        StringTokenizer stringTokenizer = new StringTokenizer(s, delim);
        ArrayList<String> arrayList=new ArrayList<String>();
        while (stringTokenizer.hasMoreTokens()) {
            arrayList.add(stringTokenizer.nextToken());
        }
        return arrayList;
    }


    private void setAlarm(int time) {
        alarmManager= (AlarmManager) getSystemService(TransactionAdd.ALARM_SERVICE);
        Intent intent=new Intent(TransactionAdd.this,AlarmReceiver.class);
        pendingIntentAlarm=PendingIntent.getBroadcast(TransactionAdd.this, 0, intent, 0);
//        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_DAY, pendingIntentAlarm);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntentAlarm);
        Toast.makeText(this, "Alarm Set Successfully", Toast.LENGTH_SHORT).show();
    }

    private void createNotificationChannel() {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name="userSetReminderChannel";
            String description="Channel used for user set reminders in app";
            int importance= NotificationCompat.PRIORITY_HIGH;
            NotificationChannel notificationChannel=new NotificationChannel("userSetReminder",name,importance);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent inComingDate=getIntent();
        String date=inComingDate.getStringExtra("date");
        reminder.setText(date);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 111 && resultCode == RESULT_OK && data != null){
            Uri filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                image.setImageBitmap(bitmap);
                imageStore(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void imageStore(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageBytes = stream.toByteArray();
        encodedImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    @SuppressLint("Range")
    public String getItemID(String amount, String category, String time) {
        myDBHelper myDBHelper = new myDBHelper(TransactionAdd.this);
        SQLiteDatabase sqLiteDatabase = myDBHelper.getReadableDatabase();
        String[] projection = new String[]{
                dataContract.Data._ITEM_ID,
                dataContract.Data._AMOUNT,
                dataContract.Data._CATEGORY,
                dataContract.Data._DESCRIPTION,
                dataContract.Data._TIME,
                dataContract.Data._IMAGE
        };
        Cursor cursor = sqLiteDatabase.query(dataContract.Data.TABLE_NAME, projection,
                null, null, null, null, null);

        int counts= (int) myDBHelper.getCount();
        Log.d("Counts ", "getItemID: "+counts);
        String[][] DB_date = new String[counts][6];

//        String tempAmount = cursor.getString(cursor.getColumnIndex(dataContract.Data._AMOUNT));
        for(int i=0; cursor.moveToNext() ; i++) {
            for(int j=0; j<6 ; j++) {
                if(j==0){
                    DB_date[i][j]=cursor.getString(cursor.getColumnIndex(dataContract.Data._ITEM_ID));
                }
                if(j==1){
                    DB_date[i][j]=cursor.getString(cursor.getColumnIndex(dataContract.Data._AMOUNT));
                }
                if(j==2){
                    DB_date[i][j]=cursor.getString(cursor.getColumnIndex(dataContract.Data._CATEGORY));
                }
                if(j==3){
                    DB_date[i][j]=cursor.getString(cursor.getColumnIndex(dataContract.Data._DESCRIPTION));
                }
                if(j==4){
                    DB_date[i][j]=cursor.getString(cursor.getColumnIndex(dataContract.Data._TIME));
                }
                if(j==5){
                    DB_date[i][j]=cursor.getString(cursor.getColumnIndex(dataContract.Data._IMAGE));
                }
            }
        }

        for(int i=0; i<counts ; i++) {
            for (int j = 0; j < 6; j++) {
                Log.d("j: "+j+ " : ", "getItemID: "+DB_date[i][j]);
            }
            Log.d("DB a", "getItemID: "+DB_date[i][1]);
            Log.d("a", "getItemID: "+amount);
            Log.d("DB c", "getItemID: "+DB_date[i][2]);
            Log.d("c", "getItemID: "+category);
            Log.d("DB t", "getItemID: "+DB_date[i][4]);
            Log.d("t", "getItemID: "+time);

            if(DB_date[i][1].equals(amount) && DB_date[i][2].equals(category) && DB_date[i][4].equals(time)){
                Log.d("ITEM ID", "getItemID: "+DB_date[i][0]);
                return itemID = DB_date[i][0];
            }
        }
        return "";
    }

}