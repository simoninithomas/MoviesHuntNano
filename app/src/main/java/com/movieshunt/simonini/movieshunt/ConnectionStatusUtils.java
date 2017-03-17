package com.movieshunt.simonini.movieshunt;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;


public class ConnectionStatusUtils {

    public static Boolean verifyConnection(Context context, View view) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        alertUserNoConnection(isConnected, view);
        return isConnected;
    }

    // Showing the status in Snackbar
    private static void alertUserNoConnection(boolean isConnected, View view) {
        String message;
        int color;
        if (!isConnected) {
            message = "You're offline please turn on your internet connection.";
            Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
            snackbar.show();

        }
    }
}
