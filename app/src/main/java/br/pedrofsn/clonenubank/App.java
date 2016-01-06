package br.pedrofsn.clonenubank;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by pedrofsn on 04/01/2016.
 */
public class App extends Application {

    public static final String TAG = "nubank";

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }
}
