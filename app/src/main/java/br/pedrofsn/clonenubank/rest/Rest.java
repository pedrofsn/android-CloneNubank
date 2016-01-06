package br.pedrofsn.clonenubank.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import br.pedrofsn.clonenubank.utils.Utils;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by pedrofsn on 04/01/2016.
 */
public class Rest {

    private static final String BASE_URL = "https://s3-sa-east-1.amazonaws.com/mobile-challenge/";

    private static Rest ourInstance;
    private final APIService service;

    private Rest() {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ") //ISO 8601
                .create();

        OkHttpClient client = new OkHttpClient();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        client.interceptors().add(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        service = retrofit.create(APIService.class);
    }

    public static Rest getInstance() {
        if (Utils.isNull(ourInstance)) {
            ourInstance = new Rest();
        }
        return ourInstance;
    }

    public APIService getService() {
        return service;
    }
}

