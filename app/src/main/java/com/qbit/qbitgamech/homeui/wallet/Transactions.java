package com.qbit.qbitgamech.homeui.wallet;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qbit.qbitgamech.R;
import com.qbit.qbitgamech.adapter.TransactionAdapterRecyclerView;
import com.qbit.qbitgamech.model.TransactionHistory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Transactions extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView noTransactionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        recyclerView = findViewById(R.id.transaction_history);
        noTransactionTextView = findViewById(R.id.no_transaction);

        // Set up the RecyclerView with a LinearLayoutManager and a custom adapter
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        TransactionAdapterRecyclerView adapter = new TransactionAdapterRecyclerView(getTransactions());
        recyclerView.setAdapter(adapter);

        // Check if there are any transactions to show
        List<TransactionHistory> transactionHistories = getTransactions();
        if (transactionHistories.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            noTransactionTextView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noTransactionTextView.setVisibility(View.GONE);
            adapter.setTransactions(transactionHistories);
        }
    }

    // A dummy method to generate some sample transactions
    private List<TransactionHistory> getTransactions() {
        List<TransactionHistory> transactionHistories = new ArrayList<>();
        transactionHistories.add(new TransactionHistory(LocalDateTime.now(), 100));
        transactionHistories.add(new TransactionHistory(LocalDateTime.now(), 50));
        transactionHistories.add(new TransactionHistory(LocalDateTime.now(), 75));
        return transactionHistories;
    }
    }
