package br.com.magazineluiza.storeprintml.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by matheusmendes on 12/04/17.
 */

public class Utils {

    public static void requestPermissions(Activity activity) {

        int permissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_WIFI_STATE);
        int permissionCheck2 = ContextCompat.checkSelfPermission(activity, Manifest.permission.INTERNET);
        int permissionCheck3 = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_NETWORK_STATE);
        int permissionCheck4 = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheck5 = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck == PackageManager.PERMISSION_DENIED ||
                permissionCheck2 == PackageManager.PERMISSION_DENIED ||
                permissionCheck3 == PackageManager.PERMISSION_DENIED ||
                permissionCheck4 == PackageManager.PERMISSION_DENIED ||
                permissionCheck5 == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(activity,
                    new String[]{
                            Manifest.permission.ACCESS_WIFI_STATE,
                            Manifest.permission.INTERNET,
                            Manifest.permission.ACCESS_NETWORK_STATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }, 0);
        }

    }

}
