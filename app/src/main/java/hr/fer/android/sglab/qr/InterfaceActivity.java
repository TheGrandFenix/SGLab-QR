package hr.fer.android.sglab.qr;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class InterfaceActivity extends AppCompatActivity {

    private static final String DEFAULT_ENDPOINT = "http://10.0.2.2:8080/api/sglab/machines/";
    private static final String USERNAME = "sglabadmin";
    private static final String PASSWORD = "sglabadmin";

    private static String encoded;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startLoading();

        encoded = Base64.encodeToString((USERNAME + ":" + PASSWORD).getBytes(), Base64.NO_WRAP).trim();

        Toast.makeText(this, "Scanned:" + getIntent().getStringExtra("QRResult"), Toast.LENGTH_LONG).show();

        new httpGet().execute(getIntent().getStringExtra("QRResult"));

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

    private class httpGet extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... urls) {
            try {
                final URL url = new URL(DEFAULT_ENDPOINT +  getIntent().getStringExtra("QRResult"));
                final HttpURLConnection con = (HttpURLConnection) url.openConnection();

                //When
                con.setRequestProperty("Authorization", "Basic " + encoded);
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestMethod("GET");


                StringBuilder result = new StringBuilder();
                BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                rd.close();
                return result.toString();
            } catch (Exception ignored) {
                return null;
            }
        }

        protected void onPostExecute(String result) {
            processData(result);
        }
    }

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
