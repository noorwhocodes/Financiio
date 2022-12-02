package com.ass2.project;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.trusted.sharing.ShareTarget;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Transactions extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<TransactionModel> transactionModelList;
    TransactionAdapter transactionAdapter;

    TextView viewReportTv;
    ImageView searchIV, optionsIV, crossIV;

    RelativeLayout searchHeader, normalHeader, totalOptions;
    LinearLayout collectiveDetails;
    EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        recyclerView=findViewById(R.id.transactionsRV);
        viewReportTv=findViewById(R.id.transactionsTvViewReport);
        searchIV=findViewById(R.id.searchIVTransaction);
        optionsIV=findViewById(R.id.transactionsTvMoreOptions);
        normalHeader=findViewById(R.id.normalHeaderTransactions);
        searchHeader=findViewById(R.id.searchHeaderTransactions);
        searchBar=findViewById(R.id.searchBarETTransactions);
        crossIV=findViewById(R.id.crossIVTransaction);
        totalOptions=findViewById(R.id.transactionsTvTotalOptions);
        collectiveDetails=findViewById(R.id.collectiveDetailsLinearLayout);

        transactionModelList=new ArrayList<>();

        transactionAdapter=new TransactionAdapter(transactionModelList,Transactions.this);
        recyclerView.setAdapter(transactionAdapter);
        RecyclerView.LayoutManager lm=new LinearLayoutManager(Transactions.this);
        recyclerView.setLayoutManager(lm);

        viewReportTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                myDBHelper myDBHelper=new myDBHelper(Transactions.this);
//                SQLiteDatabase sqLiteDatabase=myDBHelper.getWritableDatabase();
//                myDBHelper.onUpgrade(sqLiteDatabase,1,1);
//                Toast.makeText(Transactions.this, "Data Deleted", Toast.LENGTH_SHORT).show();


            }
        });

        searchIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                normalHeader.setVisibility(View.INVISIBLE);
                searchHeader.setVisibility(View.VISIBLE);
                totalOptions.setVisibility(View.GONE);
                collectiveDetails.setVisibility(View.GONE);
                Toast.makeText(Transactions.this, "Search CLicked", Toast.LENGTH_SHORT).show();

            }
        });

        crossIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                normalHeader.setVisibility(View.VISIBLE);
                searchHeader.setVisibility(View.INVISIBLE);
                totalOptions.setVisibility(View.VISIBLE);
                collectiveDetails.setVisibility(View.VISIBLE);

                transactionAdapter.filterList(transactionModelList);
                Toast.makeText(Transactions.this, "Cross CLicked", Toast.LENGTH_SHORT).show();

            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }

    public void getMysqlData(){

        StringRequest request=new StringRequest(Request.Method.GET,
                "http://192.168.18.40/project/Get.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("code");
                            JSONArray jsonArray=new JSONArray("data");

                            if(success.equals("1")){
                                for(int i=0;i<jsonArray.length();i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String itemID=object.getString("itemID");
                                    String amount=object.getString("amount");
                                    String category=object.getString("category");
                                    String description=object.getString("description");
                                    String time=object.getString("time");
                                    String image=object.getString("image");

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Transactions.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void filter(String text) {
        ArrayList<TransactionModel> filteredList=new ArrayList<>();
        for(TransactionModel item : transactionModelList){
            if(item.getCategory().toLowerCase().contains(text.toLowerCase()) ||
                    item.getDescription().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        if (!filteredList.isEmpty()) {
            transactionAdapter.filterList(filteredList);
        } else {
            Toast.makeText(this, "Sorry No Data Found", Toast.LENGTH_SHORT).show();
        }
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