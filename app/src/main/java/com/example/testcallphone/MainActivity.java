package com.example.testcallphone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean result = true;

        final String _numberToCall = "tel:12321564651";
        //check if sim card is ready to call
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        int simCardState = telephonyManager.getSimState();


        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        switch (simCardState) {
            case TelephonyManager.SIM_STATE_ABSENT:
                Toast.makeText(MainActivity.this, "SIM_STATE_ABSET NOT START CALL", Toast.LENGTH_SHORT).show();
                dialog.setMessage("ERROR: SIM_STATE_ABSET NOT START CALL");
                break;
            case TelephonyManager.SIM_STATE_NOT_READY:
                Toast.makeText(MainActivity.this, "SIM_STATE_NOT_READY NOT START CALL", Toast.LENGTH_SHORT).show();
                dialog.setMessage("ERROR: SIM_STATE_NOT_READY NOT START CALL");
                break;
            case TelephonyManager.SIM_STATE_CARD_IO_ERROR:
                Toast.makeText(MainActivity.this, "SIM_STATE_CARD_IO_ERROR NOT START CALL", Toast.LENGTH_SHORT).show();
                dialog.setMessage("ERROR: SIM_STATE_CARD_IO_ERROR NOT START CALL");
                break;
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                Toast.makeText(MainActivity.this, "SIM_STATE_NETWORK_LOCKED NOT START CALL", Toast.LENGTH_SHORT).show();
                dialog.setMessage("ERROR: SIM_STATE_NETWORK_LOCKED NOT START CALL");
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                Toast.makeText(MainActivity.this, "SIM_STATE_UNKNOWN NOT START CALL", Toast.LENGTH_SHORT).show();
                dialog.setMessage("ERROR: SIM_STATE_UNKNOWN NOT START CALL");
                break;
        }
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
        // if not have permission, open dialog and request
        if(!checkPermission(Manifest.permission.CALL_PHONE) && !checkPermission(Manifest.permission.READ_PHONE_STATE)){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                    MAKE_CALL_PERMISSION_REQUEST_CODE);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
        }

        //start call if sim card is ok
        if(TelephonyManager.SIM_STATE_READY == simCardState){
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(_numberToCall)));
        }

        int callStatus = telephonyManager.getCallState();
        if(TelephonyManager.CALL_STATE_RINGING == callStatus ) {
            Toast.makeText(MainActivity.this, "Calling...", Toast.LENGTH_SHORT).show();

            result = true;
        }
        else{
            result = false;
        }

        if(result){
            dialog.setMessage("Call made");
        }
        else{
            dialog.setMessage("check the sim card and or connection error");
        }

        dialog.setTitle("Call phone test");
        dialog.show();
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
                    Toast.makeText(this, "The call will start", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }
}