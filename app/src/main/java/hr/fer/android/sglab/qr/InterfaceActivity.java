package hr.fer.android.sglab.qr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class InterfaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(this, "Scanned:" + getIntent().getStringExtra("QRResult"), Toast.LENGTH_LONG).show();

    }
}
