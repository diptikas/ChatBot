package com.diptika.chatbot.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class NetworkUtils {
    /**
     * Method using checking internet connection availability
     *
     * @return true if connection is available or false
     */
    public static boolean isInternetAvailable(Context context) {
        boolean stat = false;
        if (null != context) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (null != connectivityManager) {
                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                if (activeNetwork != null && activeNetwork.isAvailable() && activeNetwork.isConnected()) {
                    stat = true;
                }
            }

        }
        return stat;
    }
}
