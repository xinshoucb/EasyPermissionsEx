package com.douyoumi.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.douyoumi.utils.EasyPermissionsEx;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final int RC_SETTINGS_SCREEN = 1;

    private final String[] needPermissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final String rationale = "需要访问存储设置的权限";
    private static final String goSettingsRationale = "需要访问存储设备的权限，但此权限已被禁止，你可以到设置中更改";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the contacts-related task you need to do.
                    Log.w(TAG, "onRequestPermissionsResult: Permission granted.");
                } else {
                    // permission denied, boo! Disable the functionality that depends on this permission.
                    Log.d(TAG, "onRequestPermissionsResult: Permission denied.");

                    // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
                    // This will display a dialog directing them to enable the permission in app settings.
                    if (EasyPermissionsEx.somePermissionPermanentlyDenied(this, needPermissions)) {
                        EasyPermissionsEx.goSettings2Permissions(this, goSettingsRationale, "去设置", RC_SETTINGS_SCREEN);
                    }
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SETTINGS_SCREEN) {
            // Do something after user returned from app settings screen, like showing a Toast.
            Log.d(TAG, "onActivityResult: "+data);
            Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnClicked(View v){
        if (EasyPermissionsEx.hasPermissions(this, needPermissions)) {
            Log.w(TAG, "btnClicked: hasPermissions");
        }else{
            EasyPermissionsEx.requestPermissions(this, rationale, PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE, needPermissions);
        }
    }
}
