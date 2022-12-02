package com.ass2.project;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Transactions extends AppCompatActivity {
    RecyclerView recyclerView;
    List<TransactionModel> transactionModelList;
    TransactionAdapter transactionAdapter;

    TextView viewReportTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        recyclerView=findViewById(R.id.transactionsRV);
        viewReportTv=findViewById(R.id.transactionsTvViewReport);

        transactionModelList=new ArrayList<>();

        transactionAdapter=new TransactionAdapter(transactionModelList,Transactions.this);
        recyclerView.setAdapter(transactionAdapter);
        RecyclerView.LayoutManager lm=new LinearLayoutManager(Transactions.this);
        recyclerView.setLayoutManager(lm);

        viewReportTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDBHelper myDBHelper=new myDBHelper(Transactions.this);
                SQLiteDatabase sqLiteDatabase=myDBHelper.getWritableDatabase();
                myDBHelper.onUpgrade(sqLiteDatabase,1,1);
                Toast.makeText(Transactions.this, "Data Deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("Range")
    void getData(){
        myDBHelper myDBHelper=new myDBHelper(Transactions.this);
        SQLiteDatabase sqLiteDatabase=myDBHelper.getReadableDatabase();
        String[] projection=new String[]{
                dataContract.Data._ITEM_ID,
                dataContract.Data._AMOUNT,
                dataContract.Data._CATEGORY,
                dataContract.Data._DESCRIPTION,
                dataContract.Data._TIME,
                dataContract.Data._IMAGE
        };
        Cursor cursor=sqLiteDatabase.query(dataContract.Data.TABLE_NAME, projection,
                null, null, null ,null, null);

        transactionModelList.clear();
        while(cursor.moveToNext()){
            transactionModelList.add(
                    new TransactionModel(
                            cursor.getString(cursor.getColumnIndex(dataContract.Data._CATEGORY)),
                            cursor.getString(cursor.getColumnIndex(dataContract.Data._AMOUNT)),
                            cursor.getString(cursor.getColumnIndex(dataContract.Data._DESCRIPTION)),
                            cursor.getString(cursor.getColumnIndex(dataContract.Data._TIME)),
                            cursor.getString(cursor.getColumnIndex(dataContract.Data._IMAGE))
                    )
            );
        }
        transactionAdapter=new TransactionAdapter(transactionModelList, Transactions.this);
        recyclerView.setAdapter(transactionAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }
}