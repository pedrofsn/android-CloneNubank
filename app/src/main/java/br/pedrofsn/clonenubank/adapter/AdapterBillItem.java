package br.pedrofsn.clonenubank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.pedrofsn.clonenubank.R;
import br.pedrofsn.clonenubank.model.LineItem;
import br.pedrofsn.clonenubank.utils.Utils;

/**
 * Created by pedrofsn on 03/01/2016.
 */
public class AdapterBillItem extends BaseAdapter {

    private List<LineItem> data;
    private LayoutInflater inflater;
    private int lastPosition;
    private Context context;

    public AdapterBillItem(Context context, List<LineItem> data) {
        this.inflater = LayoutInflater.from(context);
        this.lastPosition = data.size() - 1;
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public LineItem getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (Utils.isNull(convertView)) {
            convertView = inflater.inflate(R.layout.adapter_bill_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        LineItem lineItem = getItem(position);

        StringBuilder stringBuilder = new StringBuilder(lineItem.getTitle());

        if (lineItem.getIndex() > 0) {
            stringBuilder.append(" ");
            stringBuilder.append(lineItem.getIndex());
            stringBuilder.append("/");
            stringBuilder.append(lineItem.getCharges());
        }

        viewHolder.textViewPostDate.setText(lineItem.getPostDateFormatted());
        viewHolder.textViewTitle.setText(stringBuilder);

        viewHolder.textViewAmount.setText(Utils.valorSemMoeda(lineItem.getAmountAsFloat()));

        if (lineItem.getAmount() < 0) {
            viewHolder.textViewTitle.setTextColor(context.getResources().getColor(R.color.bill_overdue));
            viewHolder.textViewAmount.setTextColor(context.getResources().getColor(R.color.bill_overdue));
        } else {
            viewHolder.textViewTitle.setTextColor(context.getResources().getColor(R.color.item_bill_text));
            viewHolder.textViewAmount.setTextColor(context.getResources().getColor(R.color.item_bill_text));
        }

        // Lonely case
        if (position == lastPosition && position == 0) {
            viewHolder.viewUp.setVisibility(View.INVISIBLE);
            viewHolder.viewBall.setVisibility(View.VISIBLE);
            viewHolder.viewDown.setVisibility(View.INVISIBLE);
        } else if (0 == position) {
            viewHolder.viewUp.setVisibility(View.INVISIBLE);
            viewHolder.viewBall.setVisibility(View.VISIBLE);
            viewHolder.viewDown.setVisibility(View.VISIBLE);
        } else if (position == lastPosition) {
            viewHolder.viewUp.setVisibility(View.VISIBLE);
            viewHolder.viewBall.setVisibility(View.VISIBLE);
            viewHolder.viewDown.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.viewUp.setVisibility(View.VISIBLE);
            viewHolder.viewBall.setVisibility(View.VISIBLE);
            viewHolder.viewDown.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    private static class ViewHolder {

        View viewUp;
        View viewBall;
        View viewDown;
        TextView textViewPostDate;
        TextView textViewTitle;
        TextView textViewAmount;

        public ViewHolder(View convertView) {
            viewUp = convertView.findViewById(R.id.viewUp);
            viewBall = convertView.findViewById(R.id.viewBall);
            viewDown = convertView.findViewById(R.id.viewDown);
            textViewPostDate = (TextView) convertView.findViewById(R.id.textViewPostDate);
            textViewTitle = (TextView) convertView.findViewById(R.id.textViewTitle);
            textViewAmount = (TextView) convertView.findViewById(R.id.textViewAmount);
        }
    }

}
