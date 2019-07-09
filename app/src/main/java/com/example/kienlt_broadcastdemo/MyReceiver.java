package com.example.kienlt_broadcastdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        Log.i("Phone state", state);
        if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
            String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            try {
                FileOutputStream fos = context.openFileOutput("log.txt", Context.MODE_APPEND);
                OutputStreamWriter osw = new OutputStreamWriter(fos);
                Calendar c = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
                osw.write(format.format(c.getTime()) + number);
                osw.close();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
