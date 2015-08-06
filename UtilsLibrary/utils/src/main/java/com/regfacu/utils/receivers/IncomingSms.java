package com.regfacu.utils.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Facundo Mengoni.
 * @author mengonifacundo@gmail.com
 */
public abstract class IncomingSms extends BroadcastReceiver {
    private static final String TAG = IncomingSms.class.getSimpleName();

    public static Logger mLogger;

    static {
        mLogger = Logger.getLogger(TAG);
        mLogger.setLevel(Level.OFF);
    }

    // Get the object of SmsManager
    // final SmsManager sms = SmsManager.getDefault();
    public void onReceive(Context context, Intent intent) {
        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                if (pdusObj != null)
                    for (Object pdus : pdusObj) {
                        SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdus);
                        String senderNum = currentMessage.getDisplayOriginatingAddress();
                        String message = currentMessage.getDisplayMessageBody();

                        mLogger.info(String.format("Sender: %s | Message: %s", senderNum, message));
                        analyzeSMS(context, senderNum, message);
                    }
            }
        } catch (Exception e) {
            mLogger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public abstract void analyzeSMS(Context context, String senderNum, String message);
}