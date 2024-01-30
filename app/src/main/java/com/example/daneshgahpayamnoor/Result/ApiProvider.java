package com.example.daneshgahpayamnoor.Result;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiProvider {

    private static ApiService apiService;
    public static ApiService getApiProvider() {
        if (apiService==null){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pnyou.ir/krjpnubot/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }


}
