package br.pedrofsn.clonenubank.model;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.List;

import br.pedrofsn.clonenubank.R;
import br.pedrofsn.clonenubank.utils.Utils;

/**
 * Created by pedrofsn on 04/01/2016.
 */
public class Bill implements Serializable {

    private String state;
    private String id;
    private Summary summary;
    private Links _links;
    private String barcode;
    private String linha_digitavel;
    private List<LineItem> line_items;

    public boolean isOverdue() {
        return "overdue".equals(state);
    }

    public boolean isOpen() {
        return "open".equals(state);
    }

    public boolean isClosed() {
        return "closed".equals(state);
    }

    public boolean isFuture() {
        return "future".equals(state);
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getColor() {
        if (isOverdue())
            return R.color.bill_overdue;
        if (isOpen())
            return R.color.bill_open;
        if (isClosed())
            return R.color.bill_closed;
        if (isFuture())
            return R.color.bill_future;

        return Utils.INVALID_VALUE;
    }

    public int getDrawable() {
        if (isOverdue())
            return R.drawable.triangle_bill_overdue;
        if (isOpen())
            return R.drawable.triangle_bill_open;
        if (isClosed())
            return R.drawable.triangle_bill_closed;
        if (isFuture())
            return R.drawable.triangle_bill_future;

        return Utils.INVALID_VALUE;
    }

    public String getShortMonth() {
        String shortMonth = "";
        if (!Utils.isNull(getSummary()) && !Utils.isNull(getSummary().getDueDate())) {
            DateTime dateTime = new DateTime(getSummary().getDueDate());
            String rule = "MMM";
            if (dateTime.getYear() != new DateTime().getYear()) {
                rule = "MMM YY";
            }
            shortMonth = dateTime.toString(rule).toUpperCase();
        }

        return shortMonth;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public Links get_links() {
        return _links;
    }

    public void set_links(Links _links) {
        this._links = _links;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getLinha_digitavel() {
        return linha_digitavel;
    }

    public void setLinha_digitavel(String linha_digitavel) {
        this.linha_digitavel = linha_digitavel;
    }

    public List<LineItem> getLine_items() {
        return line_items;
    }

    public void setLine_items(List<LineItem> line_items) {
        this.line_items = line_items;
    }

}
