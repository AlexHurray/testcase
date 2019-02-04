package com.example.ermolaenkoalex.admodule.api;

import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RestApi {

    private static final String URL = "http://www.505.rs/";
    private static final int TIMEOUT_IN_SECONDS = 2;

    private static AdEndpoint adEndpoint;

    public static synchronized AdEndpoint endpoint() {
        if (adEndpoint == null) {
            adEndpoint = createAdEndpoint();
        }
        return adEndpoint;
    }

    private RestApi() {
        throw new AssertionError("No instances");
    }

    @NonNull
    private static Retrofit buildRetrofitClient(@NonNull OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @NonNull
    private static OkHttpClient buildOkHttpClient() {

        return new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .build();
    }

    @NonNull
    private static AdEndpoint createAdEndpoint() {
        final OkHttpClient httpClient = buildOkHttpClient();
        final Retrofit retrofit = buildRetrofitClient(httpClient);

        return retrofit.create(AdEndpoint.class);
    }
}
