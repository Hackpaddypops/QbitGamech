package com.qbit.qbitgamech.model;

import java.time.LocalDateTime;
import java.util.List;


public class User {

    String name;
    String email;
    String photoUrl;
    int qbits;
    List<CoinHistory> coinHistory;
    List<TransactionHistory> transactionHistory;

    List<Prediction> predictions;
    private LocalDateTime createdAt;


    private  LocalDateTime modifiedAt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int getQbits() {
        return qbits;
    }

    public void setQbits(int qbits) {
        this.qbits = qbits;
    }

    public List<CoinHistory> getCoinHistory() {
        return coinHistory;
    }

    public void setCoinHistory(List<CoinHistory> coinHistory) {
        this.coinHistory = coinHistory;
    }

    public List<TransactionHistory> getTransactionHistory() {
        return transactionHistory;
    }

    public void setTransactionHistory(List<TransactionHistory> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    public List<Prediction> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<Prediction> predictions) {
        this.predictions = predictions;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
