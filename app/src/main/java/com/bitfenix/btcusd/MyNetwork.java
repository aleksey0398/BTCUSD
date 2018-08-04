package com.bitfenix.btcusd;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author Aleksey Mitkin
 * Класс для проверки наличия интернет соедиенения
 */

public class MyNetwork {

    public static boolean isWorking(Context context) {

        final ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr != null ? conMgr.getActiveNetworkInfo() : null;

        return activeNetwork != null && activeNetwork.isConnected();

    }
}
