package hr.fer.android.sglab.qr;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

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

import hr.fer.android.sglab.qr.loaders.IDLoader;
import hr.fer.android.sglab.qr.loaders.result.WebServiceResult;
import hr.fer.android.sglab.qr.widgets.CustomLoadingProgressAnimation;

public final class ScanQRCodeActivity extends AppCompatActivity implements BarcodeCallback, LoaderManager.LoaderCallbacks<WebServiceResult<String>> {

    private static final String BARCODE_RESULT = "barcodeResult";

    private DecoratedBarcodeView barcodeView;

    private CustomLoadingProgressAnimation progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr_code);

        progress = new CustomLoadingProgressAnimation(this);

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
            Bundle args = new Bundle();
            args.putString(BARCODE_RESULT, barcodeResult.getText());
            getSupportLoaderManager().restartLoader(1, args, this);


            /*Intent nextIntent = new Intent(this, InterfaceActivity.class);
            nextIntent.putExtra("QRResult", barcodeResult.getText());
            startActivity(nextIntent);*/
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

    @NonNull
    @Override
    public Loader<WebServiceResult<String>> onCreateLoader(final int id, @Nullable final Bundle args) {
        progress.show();
        return new IDLoader(this, args.getString(BARCODE_RESULT));
    }

    @Override
    public void onLoadFinished(@NonNull final Loader<WebServiceResult<String>> loader, final WebServiceResult<String> data) {
        progress.dismiss();

        if (data == null) {
            // možda neki exception
            return;
        }

        if (data.getException() == null) {
            Intent nextIntent = new Intent(this, InterfaceActivity.class);
            nextIntent.putExtra("QRResult", data.getResult());
            startActivity(nextIntent);
        } else {
            //toast
            //CommonUtils.showDialog(getActivity(), data.getException().getMessage());
        }
    }

    @Override
    public void onLoaderReset(@NonNull final Loader<WebServiceResult<String>> loader) {
    }

}
