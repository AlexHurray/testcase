package com.example.ermolaenkoalex.testcase;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ermolaenkoalex.admodule_annotations.AddAdvertisement;

import static android.content.pm.PackageManager.PERMISSION_DENIED;

@AddAdvertisement
public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_READ_PHONE_STATE = 42;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PERMISSION_DENIED) {
            showRequestRationaleDialog();
        }
    }

    private void showRequestRationaleDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.permission_title)
                .setMessage(R.string.permission_ask_message)
                .setPositiveButton(R.string.permission_ok, (dialogInterface, i) ->
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.READ_PHONE_STATE},
                                REQUEST_READ_PHONE_STATE))
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_PHONE_STATE) {
            if (permissions[0].equalsIgnoreCase(Manifest.permission.READ_PHONE_STATE) && grantResults[0] == PERMISSION_DENIED) {
                Toast.makeText(this, R.string.permission_error_message, Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}
