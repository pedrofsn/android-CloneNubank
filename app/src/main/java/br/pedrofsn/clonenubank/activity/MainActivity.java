package br.pedrofsn.clonenubank.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import br.pedrofsn.clonenubank.R;
import br.pedrofsn.clonenubank.fragment.MonthFragment;
import br.pedrofsn.clonenubank.model.Bill;
import br.pedrofsn.clonenubank.model.RootObject;
import br.pedrofsn.clonenubank.rest.Rest;
import br.pedrofsn.clonenubank.utils.Utils;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by pedrofsn on 02/01/2016.
 */
public class MainActivity extends AppCompatActivity {

    private List<RootObject> rootObject;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private CoordinatorLayout coordinatorLayout;
    private SectionsPagerAdapter sectionAdapter;

    public SectionsPagerAdapter getSectionAdapter() {
        return sectionAdapter;
    }

    public List<RootObject> getRootObject() {
        return rootObject;
    }

    public void setRootObject(List<RootObject> rootObject) {
        this.rootObject = rootObject;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // It isn't necessary. I'm just using this to show my knowledge :P
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        setSupportActionBar(toolbar);

        // You can do this on style
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        fetchData();
    }

    public void fetchData() {
        Rest.getInstance().getService().getRootObject().
                enqueue(new Callback<List<RootObject>>() {

                    @Override
                    public void onResponse(Response<List<RootObject>> response, Retrofit retrofit) {
                        if (response.isSuccess()) {
                            run(response.body());
                        } else {
                            int statusCode = response.code();

                            if (statusCode >= 400 && statusCode < 500) {
                                showMessage(getString(R.string.error_4xx));
                            }

                            if (statusCode >= 500) {
                                showMessage(getString(R.string.error_5xx));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        showMessage(getString(R.string.error_without_connection));
                    }
                });
    }

    public void run(final List<RootObject> rootObject) {
        setRootObject(rootObject);

        if (!Utils.isNull(rootObject)) {
            viewPager.setAdapter(sectionAdapter = new SectionsPagerAdapter(getSupportFragmentManager()));
            tabLayout.setupWithViewPager(viewPager);

            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                Bill bill = rootObject.get(i).getBill();
                TabLayout.Tab tab = tabLayout.getTabAt(i);

                if (!Utils.isNull(tab)) {
                    View view = getLayoutInflater().inflate(R.layout.custom_tab, null);

                    TextView textView = (TextView) view.findViewById(R.id.textView);
                    View viewTriangle = view.findViewById(R.id.viewTriangle);

                    textView.setText(bill.getShortMonth());
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextColor(bill.getColor(this));

                    // First run!
                    if (tab.isSelected()) {
                        viewTriangle.setVisibility(View.VISIBLE);
                        viewTriangle.setBackground(bill.getDrawable(MainActivity.this));
                        textView.setTextSize(17);
                    }

                    tab.setCustomView(view);
                } else {
                    return;
                }
            }

            tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    super.onTabSelected(tab);
                    if (!Utils.isNull(tab.getCustomView())) {
                        View viewTriangle = tab.getCustomView().findViewById(R.id.viewTriangle);
                        TextView textView = (TextView) tab.getCustomView().findViewById(R.id.textView);
                        viewTriangle.setBackground(rootObject.get(tab.getPosition()).getBill().getDrawable(MainActivity.this));
                        viewTriangle.setVisibility(View.VISIBLE);
                        textView.setTextSize(17);
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    super.onTabUnselected(tab);
                    if (!Utils.isNull(tab.getCustomView())) {
                        View viewTriangle = tab.getCustomView().findViewById(R.id.viewTriangle);
                        TextView textView = (TextView) tab.getCustomView().findViewById(R.id.textView);
                        viewTriangle.setBackground(rootObject.get(tab.getPosition()).getBill().getDrawable(MainActivity.this));
                        viewTriangle.setVisibility(View.INVISIBLE);
                        textView.setTextSize(13);
                    }
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }
    }

    private void showMessage(String mensagem) {
        if (mensagem != null) {
            // With coordinatorlayout it's possible use 'swipe to dismiss' in snackbar, it's cool.
            Snackbar.make(coordinatorLayout, mensagem, Snackbar.LENGTH_INDEFINITE).setCallback(new Snackbar.Callback() {
                @Override
                public void onDismissed(Snackbar snackbar, int event) {
                    super.onDismissed(snackbar, event);
                    finish();
                }
            }).setAction(getString(R.string.close_my_app), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            }).show();
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            MonthFragment fragment = MonthFragment.newInstance(position);
            fragment.addBill(getRootObject().get(position).getBill());
            return fragment;
        }

        @Override
        public int getCount() {
            return rootObject.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null; // I'm doing this on first run
        }
    }

}
