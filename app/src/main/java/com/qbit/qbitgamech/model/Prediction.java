package com.qbit.qbitgamech.model;

import java.time.LocalDateTime;


public class Prediction {
    String userInput;
    String result;
    boolean isMatched;
    int qbitsCredited;
    LocalDateTime userInputTime;
    LocalDateTime resultTime;

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    public int getQbitsCredited() {
        return qbitsCredited;
    }

    public void setQbitsCredited(int qbitsCredited) {
        this.qbitsCredited = qbitsCredited;
    }

    public LocalDateTime getUserInputTime() {
        return userInputTime;
    }

    public void setUserInputTime(LocalDateTime userInputTime) {
        this.userInputTime = userInputTime;
    }

    public LocalDateTime getResultTime() {
        return resultTime;
    }

    public void setResultTime(LocalDateTime resultTime) {
        this.resultTime = resultTime;
    }
}
