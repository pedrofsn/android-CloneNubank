package br.pedrofsn.clonenubank.model;

import java.io.Serializable;

/**
 * Created by pedrofsn on 04/01/2016.
 */
public class Links implements Serializable {

    private Self self;
    private BoletoEmail boleto_email;
    private Barcode barcode;

    public Self getSelf() {
        return this.self;
    }

    public void setSelf(Self self) {
        this.self = self;
    }

    public BoletoEmail getBoletoEmail() {
        return this.boleto_email;
    }

    public void setBoletoEmail(BoletoEmail boleto_email) {
        this.boleto_email = boleto_email;
    }

    public Barcode getBarcode() {
        return this.barcode;
    }

    public void setBarcode(Barcode barcode) {
        this.barcode = barcode;
    }
}
