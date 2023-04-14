package com.qbit.qbitgamech.model;

public class History {
    String date;
    int coins;

    public History(String date, int coins) {
        this.date = date;
        this.coins = coins;
    }

    public String getDate() {
        return date;
    }

    public int getCoins() {
        return coins;
    }
}
