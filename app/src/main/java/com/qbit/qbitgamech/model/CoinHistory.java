package com.qbit.qbitgamech.model;

import java.time.LocalDateTime;

public class CoinHistory {
    LocalDateTime date;
    int coins;

    public CoinHistory(LocalDateTime date, int coins) {
        this.date = date;
        this.coins = coins;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getCoins() {
        return coins;
    }
}
