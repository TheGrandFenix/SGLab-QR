package hr.fer.android.sglab.qr;

/*
 * Created by lracki on 22.09.2018..
 */

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.ResultPoint;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
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
