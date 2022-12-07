package com.ass2.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TransactionRowAdapter extends RecyclerView.Adapter<TransactionRowAdapter.TransactionRowViewHolder>{
    List<TransactionRowModel> ls;
    Context c;

    public TransactionRowAdapter(List<TransactionRowModel> ls, Context c) {
        this.ls = ls;
        this.c = c;
    }

    @NonNull
    @Override
    public TransactionRowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(c).inflate(R.layout.transactions_row,parent,false);
        return new TransactionRowViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionRowViewHolder holder, int position) {
        holder.adapterIconName.setText(ls.get(position).getIconName());
        holder.adapterAmount.setText(ls.get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class TransactionRowViewHolder extends RecyclerView.ViewHolder {
        TextView adapterIconName, adapterAmount;
        public TransactionRowViewHolder(@NonNull View itemView) {
            super(itemView);
            adapterIconName=itemView.findViewById(R.id.transactionRowTvName);
            adapterAmount=itemView.findViewById(R.id.transactionRowTvAmount);
        }
    }
}
