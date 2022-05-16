package com.example.testcallphone;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //check if sim card is ready to call
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        int simCardState = telephonyManager.getSimState();

        switch (simCardState){
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



    }
}