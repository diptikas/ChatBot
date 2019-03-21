package com.diptika.chatbot.network.common;

import android.support.annotation.NonNull;

import com.diptika.chatbot.BuildConfig;
import com.diptika.chatbot.network.api.ChatBotApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Diptika Shukla on 21/03/19.
 */

public class RetrofitProvider {
    private static final int TIMEOUT = 180;
    private static final String BASE_URL = "https://www.personalityforge.com";
    private static RetrofitProvider sRetrofitInstance;
    private ChatBotApi chatBotApi;

    private RetrofitProvider() {
        buildRestApi();
    }

    public static synchronized RetrofitProvider getInstance() {
        if (sRetrofitInstance == null) {
            sRetrofitInstance = new RetrofitProvider();
        }
        return sRetrofitInstance;
    }

    public ChatBotApi getRestApi() {
        if (null == chatBotApi) {
            buildRestApi();
        }
        return chatBotApi;
    }

    private void buildRestApi() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient.Builder okHttpClient = getOkHttpBuilder();
        okHttpClient.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient.build());
        chatBotApi = builder.build().create(ChatBotApi.class);
    }

    @NonNull
    private OkHttpClient.Builder getOkHttpBuilder() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        }
        okHttpClient.addInterceptor(logging);
        return okHttpClient;
    }

}
