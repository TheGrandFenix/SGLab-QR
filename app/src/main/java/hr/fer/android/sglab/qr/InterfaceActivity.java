package hr.fer.android.sglab.qr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.TextView;
import android.widget.Toast;

public class InterfaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startLoading();
        Toast.makeText(this, "Scanned:" + getIntent().getStringExtra("QRResult"), Toast.LENGTH_LONG).show();

        new HttpAsyncTask(this).execute(getIntent().getStringExtra("QRResult"));

    }

    private void startLoading() {
        setContentView(R.layout.load_interface);

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

    //Called when data is received by the HttpAsyncTask
    protected void processData(String data) {
        if (data != null) {
            setContentView(R.layout.hydro_interface);
            TextView details = findViewById(R.id.hydroDetails);
            details.setText(data);
        } else {
            Toast.makeText(this, "Failed...", Toast.LENGTH_LONG).show();
        }
    }
}
