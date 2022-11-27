package com.ass2.project;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TransactionRV extends AppCompatActivity {
    RecyclerView rv;
    List<TransactionRowModel> ls;
    TransactionRowAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions_rc);

//        rv=findViewById(R.id.transactionsRowRV);
//        ls=new ArrayList<>();
//
//        ls.add(new TransactionRowModel(1,"Temp","3000"));
//        ls.add(new TransactionRowModel(2,"Temp","3000"));
//
//        adapter=new TransactionRowAdapter(ls, TransactionRV.this);
//        rv.setAdapter(adapter);
//        RecyclerView.LayoutManager lm=new LinearLayoutManager(TransactionRV.this);
//        rv.setLayoutManager(lm);
    }
}