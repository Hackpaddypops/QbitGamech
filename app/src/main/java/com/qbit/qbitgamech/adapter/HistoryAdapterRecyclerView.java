package com.qbit.qbitgamech.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qbit.qbitgamech.R;
import com.qbit.qbitgamech.model.CoinHistory;

import java.util.List;

public class HistoryAdapterRecyclerView extends RecyclerView.Adapter<HistoryViewHolder> {

    private List<CoinHistory> coinHistoryList;

    public HistoryAdapterRecyclerView(List<CoinHistory> coinHistoryList) {
        this.coinHistoryList = coinHistoryList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_transactions_cards, parent, false);
        return new HistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.bind(coinHistoryList.get(position));
    }

    @Override
    public int getItemCount() {
        return coinHistoryList != null ? coinHistoryList.size() : 0;
    }

    public void setTransactions(List<CoinHistory> transactions) {
        coinHistoryList = transactions;
        notifyDataSetChanged();
    }
}

// The ViewHolder for each transaction item
class HistoryViewHolder extends RecyclerView.ViewHolder {

    private TextView dateTextView;
    private TextView ruppeesTextView;

    public HistoryViewHolder(@NonNull View itemView) {
        super(itemView);
        dateTextView = itemView.findViewById(R.id.transaction_date);
        ruppeesTextView = itemView.findViewById(R.id.ruppees_credited);
    }

    public void bind(CoinHistory coinHistory) {
        dateTextView.setText(coinHistory.getDate().toString());
        ruppeesTextView.setText(String.valueOf(coinHistory.getCoins()));
    }
}


