package com.example.testcallphone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String _numberToCall = "tel:yournumber";
        //check if sim card is ready to call
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        int simCardState = telephonyManager.getSimState();

        switch (simCardState) {
            case TelephonyManager.SIM_STATE_ABSENT:
                Toast.makeText(MainActivity.this, "SIM_STATE_ABSET", Toast.LENGTH_SHORT).show();
                break;
            case TelephonyManager.SIM_STATE_NOT_READY:
                Toast.makeText(MainActivity.this, "SIM_STATE_NOT_READY", Toast.LENGTH_SHORT).show();
                break;
            case TelephonyManager.SIM_STATE_CARD_IO_ERROR:
                Toast.makeText(MainActivity.this, "SIM_STATE_CARD_IO_ERROR", Toast.LENGTH_SHORT).show();
                break;
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                Toast.makeText(MainActivity.this, "SIM_STATE_NETWORK_LOCKED", Toast.LENGTH_SHORT).show();
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                Toast.makeText(MainActivity.this, "SIM_STATE_UNKNOWN", Toast.LENGTH_SHORT).show();
                break;
        }

        // if not have permission, open dialog and request
        if(!checkPermission(Manifest.permission.CALL_PHONE)){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, MAKE_CALL_PERMISSION_REQUEST_CODE);
        }

        //start call
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(_numberToCall)));
    }

    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MAKE_CALL_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, "You can call the number by clicking on the button", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }
}