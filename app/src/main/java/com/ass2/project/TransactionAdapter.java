package com.ass2.project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLData;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    List<TransactionModel> modelList;
    Context c;

    public TransactionAdapter(List<TransactionModel> modelList, Context c) {
        this.modelList = modelList;
        this.c = c;
    }

    @NonNull
    @Override
    public TransactionAdapter.TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(c).inflate(R.layout.transactions_row,parent,false);
        return new TransactionViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        holder.category.setText(modelList.get(position).getCategory());
        holder.amount.setText(modelList.get(position).getAmount());
        holder.description.setText(modelList.get(position).getDescription());
        holder.time.setText(modelList.get(position).getTime());
        String encodedImage=modelList.get(position).getImage();
        if(encodedImage!=null) {
            byte[] decodedString = android.util.Base64.decode(encodedImage, 0);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
            holder.image.setImageBitmap(bitmap);
        }

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(c);
                alertDialog.setTitle("Delete table?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myDBHelper myDBHelper=new myDBHelper(c);
                        SQLiteDatabase sqLiteDatabase=myDBHelper.getWritableDatabase();
                        sqLiteDatabase.delete(dataContract.Data.TABLE_NAME,
                                dataContract.Data._ITEM_ID+"=?",
                                new String[]{String.valueOf(modelList.get(position).getItemID())});
                        modelList.remove(position);
                        notifyDataSetChanged();
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alertDialog1=alertDialog.create();
                alertDialog1.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView category, amount, description, time;
        ImageView image;
        RelativeLayout relativeLayout;
        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            category=itemView.findViewById(R.id.transactionRowTVCategory);
            amount=itemView.findViewById(R.id.transactionRowTvAmount);
            description=itemView.findViewById(R.id.transactionRowTVDescription);
            time=itemView.findViewById(R.id.transactionRowTVTime);
            image=itemView.findViewById(R.id.transactionRowIVImage);
            relativeLayout=itemView.findViewById(R.id.transactionRow);
        }
    }
}
