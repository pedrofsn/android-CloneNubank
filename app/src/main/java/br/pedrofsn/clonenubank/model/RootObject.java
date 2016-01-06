package br.pedrofsn.clonenubank.model;

import java.io.Serializable;

/**
 * Created by pedrofsn on 04/01/2016.
 */
public class RootObject implements Serializable {

    private Bill bill;

    public Bill getBill() {
        return this.bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}
