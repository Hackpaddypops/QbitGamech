package com.qbit.qbitgamech.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Prediction {
    String userInput;
    String result;
    boolean isMatched;
    int qbitsCredited;
    LocalDateTime userInputTime;
    LocalDateTime resultTime;
}
