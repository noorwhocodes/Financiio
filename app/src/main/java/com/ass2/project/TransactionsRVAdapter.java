package com.ass2.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TransactionsRVAdapter extends RecyclerView.Adapter<TransactionsRVAdapter.TransactionsRVViewHolder>{
    List<TransactionsRVModel> ls;
    Context c;

    @NonNull
    @Override
    public TransactionsRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(c).inflate(R.layout.activity_transactions_rc,parent,false);
        return new TransactionsRVViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionsRVViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class TransactionsRVViewHolder extends RecyclerView.ViewHolder {
        View trans;
        public TransactionsRVViewHolder(@NonNull View itemView) {
            super(itemView);
            trans=itemView.findViewById(R.id.transactionRV);
        }
    }
}
