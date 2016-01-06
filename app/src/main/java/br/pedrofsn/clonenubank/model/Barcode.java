package br.pedrofsn.clonenubank.model;

import java.io.Serializable;

/**
 * Created by pedrofsn on 04/01/2016.
 */
public class Barcode implements Serializable {

    private String href;

    public String getHref() {
        return this.href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
