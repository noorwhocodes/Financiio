package com.ass2.project;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Transactions extends AppCompatActivity {

    RecyclerView rv;
    List<TransactionRowModel> ls;
    TransactionRowAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

//        rv=findViewById(R.id.MainActivityRV);
//        ls=new ArrayList<>();
//
//        ls.add(new TransactionRowModel("Temp","3000"));
//        ls.add(new TransactionRowModel("Temp","3000"));
//
//        adapter=new TransactionRowAdapter(ls, Transactions.this);
//        rv.setAdapter(adapter);
//        RecyclerView.LayoutManager lm=new LinearLayoutManager(Transactions.this);
//        rv.setLayoutManager(lm);
    }
}