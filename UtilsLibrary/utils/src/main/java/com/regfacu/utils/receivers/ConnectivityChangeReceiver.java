package com.regfacu.utils.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.regfacu.utils.tools.ConnectionTools;

/**
 * @author Facundo Mengoni.
 * @author mengonifacundo@gmail.com
 */
public abstract class ConnectivityChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context aContext, Intent intent) {
        connectivityChange(ConnectionTools.isNetworkActive(aContext));
    }

    public abstract void connectivityChange(boolean isConnected);
}