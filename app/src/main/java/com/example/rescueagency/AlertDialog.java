package com.example.rescueagency;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

import androidx.fragment.app.FragmentActivity;

public class AlertDialog {
    public void showSettingsAlert(Context activity) {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(activity);
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                activity.startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }
}
