package com.qbit.qbitgamech;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qbit.qbitgamech.adapter.HistoryAdapterRecyclerView;
import com.qbit.qbitgamech.model.CoinHistory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView noTransactionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.transaction_history);
        noTransactionTextView = findViewById(R.id.no_transaction);

        // Set up the RecyclerView with a LinearLayoutManager and a custom adapter
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        HistoryAdapterRecyclerView adapter = new HistoryAdapterRecyclerView(getHistory());
        recyclerView.setAdapter(adapter);

        // Check if there are any transactions to show
        List<CoinHistory> histories = getHistory();
        if (histories.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            noTransactionTextView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noTransactionTextView.setVisibility(View.GONE);
            adapter.setTransactions(histories);
        }
    }

    private List<CoinHistory> getHistory() {
        List<CoinHistory> histories = new ArrayList<>();
        histories.add(new CoinHistory(LocalDateTime.now(), 100));
        histories.add(new CoinHistory(LocalDateTime.now(), 50));
        histories.add(new CoinHistory(LocalDateTime.now(), 75));
        return histories;
    }
}