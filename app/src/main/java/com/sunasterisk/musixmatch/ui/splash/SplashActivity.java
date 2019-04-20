package com.sunasterisk.musixmatch.ui.splash;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.sunasterisk.musixmatch.R;
import com.sunasterisk.musixmatch.ui.main.MainActivity;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by superme198 on 15,March,2019
 * in Music_Match_1.
 */
public class SplashActivity extends AppCompatActivity {
    public static final int TIME_DELAY = 2000;
    public static final String[] PERMISSIONS = {WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (isAllowPermission()) {
            goToHomeScreen();
        } else finish();
    }

    private void goToHomeScreen() {
        //start to main
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }, TIME_DELAY);
    }

    private boolean isAllowPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : PERMISSIONS) {
                if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(PERMISSIONS, 0);
                    return false;
                }
            }
        }
        return true;
    }
}
