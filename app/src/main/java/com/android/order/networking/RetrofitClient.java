package com.android.order.networking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static final String BASE_URL = "https://urscrm.ordr.ae/api/";
    public static Dispatcher dispatcher = new Dispatcher();
    private static RetrofitClient mInstance;
    private Retrofit retrofit;


    private RetrofitClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.dispatcher(dispatcher);
        httpClientBuilder.addInterceptor(logging);
        httpClientBuilder.addInterceptor(new BasicAuthInterceptor("react", "urscrm@react"));
//        httpClientBuilder.addInterceptor(chain -> {
//            Request request = chain.request().newBuilder().addHeader("Authorization", "cmVhY3Q6dXJzY3JtQHJlYWN0").build();
//            return chain.proceed(request);
////        });

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClientBuilder.build())
                .build();
    }

    public static synchronized RetrofitClient getInstance() {

        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public static Dispatcher getDispatcher() {
        return dispatcher;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }
}
