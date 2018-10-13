package hr.fer.android.sglab.qr;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.Toast;

import hr.fer.android.sglab.qr.R;
import hr.fer.android.sglab.qr.widgets.CustomLoadingProgressAnimation;

public class StartupActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CAMERA = 1234;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_startup);

        Button btnScanQR = this.findViewById(R.id.btn_scan);
        btnScanQR.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        startLoading();
    }

    private void startLoading() {
        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(1400);
        findViewById(R.id.logo_circle1).startAnimation(anim);

        RotateAnimation anim2 = new RotateAnimation(0.0f, -360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim2.setInterpolator(new LinearInterpolator());
        anim2.setRepeatCount(Animation.INFINITE);
        anim2.setDuration(3000);
        findViewById(R.id.logo_circle2).startAnimation(anim2);

        RotateAnimation anim3 = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim3.setInterpolator(new LinearInterpolator());
        anim3.setRepeatCount(Animation.INFINITE);
        anim3.setDuration(2200);
        findViewById(R.id.logo_circle3).startAnimation(anim3);
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.btn_scan:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestCameraPermission();
                }
                else {
                    showScanner();
                }
                break;
            case R.id.btn_settings:
                Intent nextIntent = new Intent(this, SettingsActivity.class);
                nextIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(nextIntent);
                break;
        }
    }

    private void requestCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        }
    }

    private void showScanner() {
        Intent nextIntent = new Intent(this, ScanQRCodeActivity.class);
        nextIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(nextIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showScanner();
            } else {
                Toast.makeText(this, "Permission denied. Qr code scanner can not be started.", Toast.LENGTH_LONG).show();            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
