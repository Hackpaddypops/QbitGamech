package com.qbit.qbitgamech.model;

public class Transaction {
    String date;
    int ruppees;

    public Transaction(String date, int ruppees) {
        this.date = date;
        this.ruppees = ruppees;
    }

    public String getDate() {
        return date;
    }

    public int getRuppees() {
        return ruppees;
    }
}
