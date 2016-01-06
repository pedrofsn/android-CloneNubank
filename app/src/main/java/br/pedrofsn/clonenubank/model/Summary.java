package br.pedrofsn.clonenubank.model;

import java.io.Serializable;

import br.pedrofsn.clonenubank.utils.Utils;

/**
 * Created by pedrofsn on 04/01/2016.
 */
public class Summary implements Serializable {

    private String due_date;
    private String close_date;
    private int past_balance;
    private int total_balance;
    private int interest;
    private int total_cumulative;
    private int paid;
    private int minimum_payment;
    private String open_date;

    public boolean isPaid() {
        return paid < 0;
    }

    public String getDueDate() {
        return this.due_date;
    }

    public void setDueDate(String due_date) {
        this.due_date = due_date;
    }

    public String getCloseDate() {
        return this.close_date;
    }

    public void setCloseDate(String close_date) {
        this.close_date = close_date;
    }

    public int getPastBalance() {
        return this.past_balance;
    }

    public void setPastBalance(int past_balance) {
        this.past_balance = past_balance;
    }

    public Float getPastBalanceAsFloat() {
        return Utils.convertToFloat(this.past_balance);
    }

    public int getTotalBalance() {
        return this.total_balance;
    }

    public void setTotalBalance(int total_balance) {
        this.total_balance = total_balance;
    }

    public Float getTotalBalanceAsFloat() {
        return Utils.convertToFloat(this.total_balance);
    }

    public int getInterest() {
        return this.interest;
    }

    public void setInterest(int interest) {
        this.interest = interest;
    }

    public Float getInterestAsFloat() {
        return Utils.convertToFloat(this.interest);
    }

    public int getTotalCumulative() {
        return this.total_cumulative;
    }

    public void setTotalCumulative(int total_cumulative) {
        this.total_cumulative = total_cumulative;
    }

    public int getPaid() {
        return this.paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public Float getPaidAsFloat() {
        return Utils.convertToFloat(this.paid);
    }

    public int getMinimumPayment() {
        return this.minimum_payment;
    }

    public void setMinimumPayment(int minimum_payment) {
        this.minimum_payment = minimum_payment;
    }

    public String getOpenDate() {
        return this.open_date;
    }

    public void setOpenDate(String open_date) {
        this.open_date = open_date;
    }

    public Float getTotalCumulativeAsFloat() {
        return Utils.convertToFloat(this.total_cumulative);
    }
}
