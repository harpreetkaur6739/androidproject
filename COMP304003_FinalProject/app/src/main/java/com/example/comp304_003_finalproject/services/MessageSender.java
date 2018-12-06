/**
 * Author
 * Harpreet Kaur
 * 300910377
 */
package com.example.comp304_003_finalproject.services;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;

public class MessageSender extends IntentService {
    private Thread thread = new Thread();
    String message;
    PendingIntent sentPI, deliveredPI;
    IntentFilter intentFilter;

    String SENT = "SMS_SENT";
    String DELIVERED = "SMS_DELIVERED";

    public MessageSender() {
        super("Message Sender Service");
    }

    // receive intents sent by sendBroadcast()
    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //display the SMS received in the TextView
        }
    };

    @Override
    protected void onHandleIntent(Intent intent) {
        thread.start();

        try {
            message = intent.getStringExtra("smsMsg");
            sendMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage()
    {
        sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);

        deliveredPI = PendingIntent.getBroadcast(this, 0, new
                Intent(DELIVERED), 0);

        //intent to filter the action for SMS messages received
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");

        //---register the receiver---
        registerReceiver(intentReceiver, intentFilter);

        send("5556", message);

    }
    //sends an SMS message to another device
    private void send(String phoneNumber, String message)
    {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //---unregister the receiver---
        unregisterReceiver(intentReceiver);
    }
}