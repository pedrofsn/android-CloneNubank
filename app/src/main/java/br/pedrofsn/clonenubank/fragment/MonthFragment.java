package br.pedrofsn.clonenubank.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.text.NumberFormat;

import br.pedrofsn.clonenubank.R;
import br.pedrofsn.clonenubank.adapter.AdapterBillItem;
import br.pedrofsn.clonenubank.model.Bill;
import br.pedrofsn.clonenubank.utils.Utils;

/**
 * Created by pedrofsn on 02/01/2016.
 */
@SuppressWarnings("deprecation")
// The alternative for getColorStateList and getDrawable just runs on API 23
public class MonthFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section";

    private ListView listView;
    private LinearLayout linearLayout;
    private TextView textViewAmount;
    private TextView textViewDueDate;
    private TextView textViewTitle;
    private LinearLayout linearLayoutPaymentReceived;
    private TextView textViewLabelPaidReceived;
    private TextView textViewLabelReceived;
    private RelativeLayout relativeLayoutData;
    private TextView textViewLabelTotalCumulative;
    private TextView textViewTotalCumulative;
    private TextView textViewLabelPastBalance;
    private TextView textViewPastBalance;
    private TextView textViewLabelInterest;
    private TextView textViewInterest;
    private Button buttonGenerateBillet;
    private TextView textViewDescriptionLimitDate;
    private Bill bill;

    public static MonthFragment newInstance(int sectionNumber) {
        MonthFragment fragment = new MonthFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootViewListView = inflater.inflate(R.layout.month_fragment, container, false);
        listView = (ListView) rootViewListView.findViewById(R.id.listView);

        View header = inflater.inflate(R.layout.header_bill, null, false);
        linearLayout = (LinearLayout) header.findViewById(R.id.linearLayout);
        textViewAmount = (TextView) header.findViewById(R.id.textViewAmount);
        textViewDueDate = (TextView) header.findViewById(R.id.textViewDueDate);
        textViewTitle = (TextView) header.findViewById(R.id.textViewTitle);

        linearLayoutPaymentReceived = (LinearLayout) header.findViewById(R.id.linearLayoutPaymentReceived);
        textViewLabelPaidReceived = (TextView) header.findViewById(R.id.textViewLabelPaidReceived);
        textViewLabelReceived = (TextView) header.findViewById(R.id.textViewPaidReceived);

        relativeLayoutData = (RelativeLayout) header.findViewById(R.id.relativeLayoutData);
        textViewLabelTotalCumulative = (TextView) header.findViewById(R.id.textViewLabelTotalCumulative);
        textViewTotalCumulative = (TextView) header.findViewById(R.id.textViewTotalCumulative);
        textViewLabelPastBalance = (TextView) header.findViewById(R.id.textViewLabelPastBalance);
        textViewPastBalance = (TextView) header.findViewById(R.id.textViewPastBalance);
        textViewLabelInterest = (TextView) header.findViewById(R.id.textViewLabelInterest);
        textViewInterest = (TextView) header.findViewById(R.id.textViewInterest);

        buttonGenerateBillet = (Button) header.findViewById(R.id.buttonGenerateBillet);

        TextView textViewLabelValueInCurrency = (TextView) header.findViewById(R.id.textViewLabelValueInCurrency);
        textViewDescriptionLimitDate = (TextView) header.findViewById(R.id.textViewDescriptionLimitDate);

        textViewLabelValueInCurrency.setText(Utils.aplicarMoeda(getString(R.string.values_in_currency)));

        listView.addHeaderView(header);
        return rootViewListView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!Utils.isNull(bill)) {
            DateTime dateOpen = new DateTime(bill.getSummary().getOpenDate());
            DateTime dateOverdue = new DateTime(bill.getSummary().getDueDate());
            DateTime dateClosed = new DateTime(bill.getSummary().getCloseDate());

            String totalBalance = NumberFormat.getCurrencyInstance().format(bill.getSummary().getTotalBalanceAsFloat());
            String overdueMessage = String.format(getString(R.string.past_due_message), dateOverdue.toString(Utils.MONTH_AND_DAY)).toUpperCase();
            String closedMessage = null;

            // ENUM is aplicable in this case, but it consumes a lot of memory in android #perfmatters
            if (bill.isOverdue()) {
                if (bill.getSummary().isPaid()) {
                    showPaymentReceived(bill.getSummary().getPaidAsFloat(), bill.getColor());
                }

            } else if (bill.isOpen()) {
                showGenerateButtonBillet(R.color.text_selector_azul, R.drawable.selector_button_blue);
                closedMessage = getString(R.string.closing_in).concat(" ").concat(dateClosed.toString(Utils.MONTH_AND_DAY)).toUpperCase();

            } else if (bill.isClosed()) {
                showGenerateButtonBillet(R.color.text_selector_vermelho, R.drawable.selector_button_red);
                showData(bill.getSummary().getTotalCumulativeAsFloat(), bill.getSummary().getPastBalanceAsFloat(), bill.getSummary().getInterestAsFloat());

            } else if (bill.isFuture()) {
                hideElements();
                closedMessage = getString(R.string.partial).toUpperCase();
            }

            setupHeader(totalBalance, overdueMessage, closedMessage);

            textViewDescriptionLimitDate.setText(String.format(getString(R.string.since_x_until_x), dateOpen.toString(Utils.MONTH_AND_DAY), dateClosed.toString(Utils.MONTH_AND_DAY)).toUpperCase());

            linearLayout.setBackgroundColor(bill.getColor());

            AdapterBillItem adapterBillItem = new AdapterBillItem(getActivity(), bill.getLine_items());
            listView.setAdapter(adapterBillItem);
        }
    }

    private void setupHeader(String totalBalance, String overdueMessage, String closedMessage) {
        textViewAmount.setText(totalBalance);
        textViewDueDate.setText(overdueMessage);
        if (Utils.isNull(closedMessage)) {
            textViewTitle.setVisibility(View.GONE);
        } else {
            textViewTitle.setText(closedMessage);
        }
    }

    private void showPaymentReceived(Float valorPagamentoRecebido, int color) {
        linearLayoutPaymentReceived.setVisibility(View.VISIBLE);
        textViewLabelPaidReceived.setTextColor(color);
        textViewLabelReceived.setTextColor(color);
        textViewLabelReceived.setText(NumberFormat.getCurrencyInstance().format(Utils.aplicarMoeda(String.valueOf(valorPagamentoRecebido))));
    }

    public LinearLayout getLinearLayoutPaymentReceived() {
        return linearLayoutPaymentReceived;
    }

    private void showData(Float totalCumulative, Float pastBalance, Float interest) {
        relativeLayoutData.setVisibility(View.VISIBLE);

        if (totalCumulative > 0) {
            textViewTotalCumulative.setText(NumberFormat.getCurrencyInstance().format(totalCumulative));
        } else {
            textViewLabelTotalCumulative.setVisibility(View.GONE);
            textViewTotalCumulative.setVisibility(View.GONE);
        }

        if (pastBalance != 0) {
            textViewPastBalance.setText(NumberFormat.getCurrencyInstance().format(pastBalance));
            if (pastBalance < 0) {
                textViewLabelPastBalance.setText(getString(R.string.prepaid_values));
            }
        } else {
            textViewLabelPastBalance.setVisibility(View.GONE);
            textViewPastBalance.setVisibility(View.GONE);
        }

        if (interest > 0) {
            textViewInterest.setText(NumberFormat.getCurrencyInstance().format(interest));
        } else {
            textViewLabelInterest.setVisibility(View.GONE);
            textViewInterest.setVisibility(View.GONE);
        }
    }

    public RelativeLayout getRelativeLayoutData() {
        return relativeLayoutData;
    }

    private void showGenerateButtonBillet(int cor1, int cor2) {
        buttonGenerateBillet.setVisibility(View.VISIBLE);
        buttonGenerateBillet.setTextColor(getResources().getColorStateList(cor1));
        buttonGenerateBillet.setBackground(getResources().getDrawable(cor2));
    }

    public Button getButtonGenerateBillet() {
        return buttonGenerateBillet;
    }

    private void hideElements() {
        buttonGenerateBillet.setVisibility(View.GONE);
        linearLayoutPaymentReceived.setVisibility(View.GONE);
        relativeLayoutData.setVisibility(View.GONE);
    }

    public TextView getTextViewLabelTotalCumulative() {
        return textViewLabelTotalCumulative;
    }

    public TextView getTextViewTotalCumulative() {
        return textViewTotalCumulative;
    }

    public TextView getTextViewLabelPastBalance() {
        return textViewLabelPastBalance;
    }

    public TextView getTextViewPastBalance() {
        return textViewPastBalance;
    }

    public TextView getTextViewLabelInterest() {
        return textViewLabelInterest;
    }

    public TextView getTextViewInterest() {
        return textViewInterest;
    }

    public void addBill(Bill bill) {
        this.bill = bill;
    }

    public Bill getBill() {
        return bill;
    }

}