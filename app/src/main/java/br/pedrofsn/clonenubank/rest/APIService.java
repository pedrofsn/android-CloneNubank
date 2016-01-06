package br.pedrofsn.clonenubank.rest;

import java.util.List;

import br.pedrofsn.clonenubank.model.RootObject;
import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by pedrofsn on 04/01/2016.
 */
public interface APIService {

    @GET("bill/bill_new.json")
    Call<List<RootObject>> getRootObject();

}
