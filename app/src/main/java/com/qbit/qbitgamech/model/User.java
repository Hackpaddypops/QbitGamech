package com.qbit.qbitgamech.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "users")
public class User {
    String name;
    @MongoId
    String email;
    String photoUrl;
    int qbits;
    List<CoinHistory> coinHistory;
    List<TransactionHistory> transactionHistory;

    List<Prediction> predictions;
    private LocalDateTime createdAt;


    private  LocalDateTime modifiedAt;
}
