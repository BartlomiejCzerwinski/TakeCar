package com.example.myapplication.takecar;

public class NotEnoughMoneyException extends Exception{
    public NotEnoughMoneyException(String message) {
        super(message);
    }
}
