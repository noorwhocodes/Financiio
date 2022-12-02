package com.ass2.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class home_page extends AppCompatActivity {

    CircleImageView add;
    ImageView trans;
    ImageView prof;

    RecyclerView recyclerView;
    ArrayList<TransactionModel> transactionModelList;
    TransactionAdapter transactionAdapter;

    TextView viewReportTv;
    ImageView searchIV, optionsIV, crossIV;

    RelativeLayout searchHeader, normalHeader, totalOptions;
    LinearLayout collectiveDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        add = findViewById(R.id.idadd);
        trans = findViewById(R.id.idshowtrans);
        prof = findViewById(R.id.idprofile);

        recyclerView=findViewById(R.id.idrv);
        transactionModelList=new ArrayList<>();

        transactionAdapter=new TransactionAdapter(transactionModelList,home_page.this);
        recyclerView.setAdapter(transactionAdapter);
        RecyclerView.LayoutManager lm=new LinearLayoutManager(home_page.this);
        recyclerView.setLayoutManager(lm);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(home_page.this, "add clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(home_page.this,TransactionAdd.class));
            }
        });
        trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_page.this,Transactions.class));
            }
        });
        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home_page.this,edit_profile.class));
            }
        });
    }

    @SuppressLint("Range")
    void getData(){
        myDBHelper myDBHelper=new myDBHelper(home_page.this);
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
        transactionAdapter=new TransactionAdapter(transactionModelList, home_page.this);
        recyclerView.setAdapter(transactionAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

}