package com.example.ermolaenkoalex.admodule;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.example.ermolaenkoalex.admodule.api.RestApi;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class AdModule {
    private static final String LOG_TAG = "AdModule";
    private static final int REPEAT_PERIOD = 60;

    private Disposable disposable;

    public void attach(@NonNull Context context) {
        TelephonyManager telephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyMgr != null && ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PERMISSION_GRANTED) {
            @SuppressLint("HardwareIds") String imsi = telephonyMgr.getSubscriberId();

            disposable = Observable.interval(REPEAT_PERIOD, TimeUnit.SECONDS)
                    .flatMapSingle(aLong -> RestApi.endpoint().getAds(imsi))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(answer -> {
                        if (answer.isOK()) {
                            AdActivity.start(context, answer.getUrl());
                        } else {
                            Toast.makeText(context, answer.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }, throwable -> Log.e(LOG_TAG, throwable.toString()));
        } else {
            Log.e(LOG_TAG, context.getString(R.string.error_permission));
        }
    }

    public void detach() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
