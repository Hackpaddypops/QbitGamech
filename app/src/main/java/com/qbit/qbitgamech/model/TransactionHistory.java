package com.qbit.qbitgamech.model;

import java.time.LocalDateTime;

public class TransactionHistory {
    LocalDateTime date;
    int ruppees;

    public TransactionHistory(LocalDateTime date, int ruppees) {
        this.date = date;
        this.ruppees = ruppees;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getRuppees() {
        return ruppees;
    }
}
