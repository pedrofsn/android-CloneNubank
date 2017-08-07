package br.pedrofsn.clonenubank.model;

import org.joda.time.DateTime;

import java.io.Serializable;

import br.pedrofsn.clonenubank.utils.Utils;

/**
 * Created by pedrofsn on 04/01/2016.
 */
public class LineItem implements Serializable {

    private String post_date;
    private int amount;
    private String title;
    private int index;
    private int charges;
    private String href;

    public LineItem(String post_date, int amount, String title, int index, int charges, String href) {
        this.post_date = post_date;
        this.amount = amount;
        this.title = title;
        this.index = index;
        this.charges = charges;
        this.href = href;
    }

    public LineItem() {
    }

    public String getPostDateFormatted() {
        String string = Utils.EMPTY_STRING;
        if (!Utils.isNull(post_date)) {
            DateTime dateTime = new DateTime(post_date);
            string = dateTime.toString(Utils.MONTH_AND_YEAR).toUpperCase();
        }

        return string;
    }

    public String getPostDate() {
        return this.post_date;
    }

    public void setPostDate(String post_date) {
        this.post_date = post_date;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getCharges() {
        return this.charges;
    }

    public void setCharges(int charges) {
        this.charges = charges;
    }

    public String getHref() {
        return this.href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Float getAmountAsFloat() {
        return Utils.convertToFloat(this.amount);
    }
}
