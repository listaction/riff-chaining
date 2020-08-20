package com.example.aggregation;

import java.util.List;

public class CombinedBalance {

    private Double balance;
    private List<Double> transactions;

    public CombinedBalance(Double balance, List<Double> transactions) {
        this.balance = balance;
        this.transactions = transactions;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public List<Double> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Double> transactions) {
        this.transactions = transactions;
    }

}
