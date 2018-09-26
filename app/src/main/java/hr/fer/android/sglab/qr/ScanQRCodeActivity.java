package hr.fer.android.sglab.qr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoderResultPointCallback;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class ScanQRCodeActivity extends AppCompatActivity implements BarcodeCallback {

    private DecoratedBarcodeView barcodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr_code);

        barcodeView = findViewById(R.id.barcode_scanner);
        showCamera();
    }

    public void showCamera() {
        Set<BarcodeFormat> decodeFormats = EnumSet.of(BarcodeFormat.QR_CODE);
        DecoderResultPointCallback callback = new DecoderResultPointCallback();
        Map<DecodeHintType, Object> hints = new HashMap<>();
        hints.put(DecodeHintType.NEED_RESULT_POINT_CALLBACK, callback);

        MultiFormatReader reader = new MultiFormatReader();
        reader.setHints(hints);
        barcodeView.getBarcodeView().setDecoderFactory(new DefaultDecoderFactory(decodeFormats, hints, null, 0));
        barcodeView.decodeContinuous(this);
    }

    @Override
    public void barcodeResult(BarcodeResult barcodeResult) {
        barcodeView.pauseAndWait();

        if (barcodeResult.getText() != null) {
            Toast.makeText(this, "Scanned:" + barcodeResult.getText(), Toast.LENGTH_LONG).show();
            setContentView(R.layout.load_layout_temp);
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
    }

    @Override
    public void possibleResultPoints(final List<ResultPoint> resultPoints) {

    }

    @Override
    public void onResume() {
        super.onResume();
        barcodeView.resume();
        showCamera();
    }

    @Override
    public void onPause() {
        super.onPause();

        barcodeView.pause();
    }

}
