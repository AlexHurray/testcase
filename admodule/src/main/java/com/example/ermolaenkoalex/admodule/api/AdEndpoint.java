package com.example.ermolaenkoalex.admodule.api;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AdEndpoint {
    @FormUrlEncoded
    @POST("adviator/index.php")
    Single<Answer> getAds(@Field("id") String id);
}
