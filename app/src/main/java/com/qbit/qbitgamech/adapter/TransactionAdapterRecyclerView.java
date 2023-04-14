package com.qbit.qbitgamech.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qbit.qbitgamech.R;
import com.qbit.qbitgamech.model.TransactionHistory;

import java.util.List;

public class TransactionAdapterRecyclerView extends RecyclerView.Adapter<TransactionViewHolder> {

    private List<TransactionHistory> transactionHistoryList;

    public TransactionAdapterRecyclerView(List<TransactionHistory> transactionHistoryList) {
        this.transactionHistoryList = transactionHistoryList;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_transactions_cards, parent, false);
        return new TransactionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        holder.bind(transactionHistoryList.get(position));
    }

    @Override
    public int getItemCount() {
        return transactionHistoryList != null ? transactionHistoryList.size() : 0;
    }

    public void setTransactions(List<TransactionHistory> transactionHistories) {
        transactionHistoryList = transactionHistories;
        notifyDataSetChanged();
    }
}

// The ViewHolder for each transaction item
 class TransactionViewHolder extends RecyclerView.ViewHolder {

    private TextView dateTextView;
    private TextView ruppeesTextView;

    public TransactionViewHolder(@NonNull View itemView) {
        super(itemView);
        dateTextView = itemView.findViewById(R.id.transaction_date);
        ruppeesTextView = itemView.findViewById(R.id.ruppees_credited);
    }

    public void bind(TransactionHistory transactionHistory) {
        dateTextView.setText(transactionHistory.getDate().toString());
        ruppeesTextView.setText(String.valueOf(transactionHistory.getRuppees()));
    }
}

