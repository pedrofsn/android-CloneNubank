package br.pedrofsn.clonenubank;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import br.pedrofsn.clonenubank.activity.MainActivity;
import br.pedrofsn.clonenubank.fragment.MonthFragment;
import br.pedrofsn.clonenubank.model.Bill;
import br.pedrofsn.clonenubank.model.Summary;

/**
 * Created by pedrofsn on 05/01/2016.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mainActivity;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private CoordinatorLayout coordinatorLayout;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mainActivity = getActivity();
        coordinatorLayout = (CoordinatorLayout) mainActivity.findViewById(R.id.coordinatorLayout);
        viewPager = (ViewPager) mainActivity.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) mainActivity.findViewById(R.id.tabLayout);
        toolbar = (Toolbar) mainActivity.findViewById(R.id.toolbar);

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            /*
                            * Delay pra dar tempo da requisição ser realizada e o callback executado.
                            * Os dois últimos casos de teste podem falhar de acordo com a velocidade de conexão.
                            * Deve ser migrado pra mockito ou wire. Ou puxar um json do assets.
                            * Sim, isto é um GAMBI PATTERN.
                            */
                            Thread.sleep(1000 * 10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).run();
    }

    public void testPreconditions() {
        assertNotNull(mainActivity);
        assertNotNull(coordinatorLayout);
        assertNotNull(viewPager);
        assertNotNull(tabLayout);
        assertNotNull(toolbar);
        assertFalse(toolbar.isShown());
        assertNotNull(mainActivity.getRootObject());
        assertTrue(viewPager.getAdapter().getCount() > 0);
        assertTrue(mainActivity.getSectionAdapter().getCount() > 0);

        for (int i = 0; i < mainActivity.getSupportFragmentManager().getFragments().size(); i++) {
            MonthFragment monthFragment = (MonthFragment) mainActivity.getSupportFragmentManager().getFragments().get(i);
            Bill bill = monthFragment.getBill();
            Summary summary = bill.getSummary();

            assertNotNull(bill);
            assertNotNull(summary);
            assertNotNull(monthFragment.getLinearLayoutPaymentReceived());
            assertNotNull(monthFragment.getButtonGenerateBillet());
            assertNotNull(monthFragment.getRelativeLayoutData());
            assertNotNull(monthFragment.getTextViewTotalCumulative());
            assertNotNull(monthFragment.getTextViewLabelTotalCumulative());
            assertNotNull(monthFragment.getTextViewLabelPastBalance());
            assertNotNull(monthFragment.getTextViewPastBalance());
            assertNotNull(monthFragment.getTextViewLabelInterest());
            assertNotNull(monthFragment.getTextViewInterest());

            if (bill.isOverdue()) {
                assertEquals("overdue", bill.getState());

                if (bill.getSummary().getPaid() < 0) {
                    assertEquals(View.VISIBLE, monthFragment.getLinearLayoutPaymentReceived().getVisibility());
                } else {
                    assertEquals(View.GONE, monthFragment.getLinearLayoutPaymentReceived().getVisibility());
                }
            }

            if (bill.isClosed()) {
                assertEquals("closed", bill.getState());

                assertEquals(View.VISIBLE, monthFragment.getButtonGenerateBillet().getVisibility());
                assertEquals(View.VISIBLE, monthFragment.getRelativeLayoutData().getVisibility());

                if (summary.getTotalCumulative() > 0) {
                    assertEquals(View.VISIBLE, monthFragment.getTextViewLabelTotalCumulative().getVisibility());
                    assertEquals(View.VISIBLE, monthFragment.getTextViewTotalCumulative().getVisibility());
                } else {
                    assertEquals(View.GONE, monthFragment.getTextViewLabelTotalCumulative().getVisibility());
                    assertEquals(View.GONE, monthFragment.getTextViewTotalCumulative().getVisibility());
                }

                if (summary.getPastBalance() == 0) {
                    assertEquals(View.GONE, monthFragment.getTextViewLabelPastBalance().getVisibility());
                    assertEquals(View.GONE, monthFragment.getTextViewPastBalance().getVisibility());
                } else {

                    if (summary.getPastBalance() > 0) {
                        assertEquals(mainActivity.getString(R.string.unpaid_values).toLowerCase(), monthFragment.getTextViewLabelPastBalance().getText().toString().toLowerCase());
                    } else {
                        assertEquals(mainActivity.getString(R.string.prepaid_values).toLowerCase(), monthFragment.getTextViewLabelPastBalance().getText().toString().toLowerCase());
                    }

                    assertEquals(View.VISIBLE, monthFragment.getTextViewLabelPastBalance().getVisibility());
                    assertEquals(View.VISIBLE, monthFragment.getTextViewPastBalance().getVisibility());
                }

                if (summary.getInterest() > 0) {
                    assertEquals(View.VISIBLE, monthFragment.getTextViewLabelInterest().getVisibility());
                    assertEquals(View.VISIBLE, monthFragment.getTextViewInterest().getVisibility());
                } else {
                    assertEquals(View.GONE, monthFragment.getTextViewLabelInterest().getVisibility());
                    assertEquals(View.GONE, monthFragment.getTextViewInterest().getVisibility());
                }

            }

            if (bill.isOpen()) {
                assertEquals("open", bill.getState());
                assertEquals(View.VISIBLE, monthFragment.getButtonGenerateBillet().getVisibility());
            }

            if (bill.isFuture()) {
                assertEquals("future", bill.getState());
                assertEquals(View.GONE, monthFragment.getLinearLayoutPaymentReceived().getVisibility());
                assertEquals(View.GONE, monthFragment.getButtonGenerateBillet().getVisibility());
                assertEquals(View.GONE, monthFragment.getRelativeLayoutData().getVisibility());
            }
        }
    }

}
