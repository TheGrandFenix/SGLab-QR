package hr.fer.android.sglab.qr;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.android.sglab.qr.loaders.IDLoader;
import hr.fer.android.sglab.qr.loaders.result.WebServiceResult;
import hr.fer.android.sglab.qr.utils.TimeoutHandler;
import hr.fer.android.sglab.qr.widgets.CustomLoadingProgressAnimation;

import static hr.fer.android.sglab.qr.ScanQRCodeActivity.LOADER_ID;
import static hr.fer.android.sglab.qr.ScanQRCodeActivity.QR_RESULT;

public class MenuActivity extends AppCompatActivity implements TimeoutHandler.TimeoutListener, LoaderManager.LoaderCallbacks<WebServiceResult<String>>{

    private TextView machineName;
    private TextView machineID;
    private TextView activePower;
    private TextView reactivePower;
    private TextView apparentPower;
    private TextView description;

    private CustomLoadingProgressAnimation progress;

    //id, name, description, active power, reactive power, apparent power
    private List<String> jsonElements;

    private TimeoutHandler timeoutHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        timeoutHandler = new TimeoutHandler(this, TimeoutHandler.EVENT_TIMEOUT, this);

        progress = new CustomLoadingProgressAnimation(this);

        String qrResultJSON = getIntent().getStringExtra(QR_RESULT);
        jsonElements = parseJson(qrResultJSON);

        machineName = findViewById(R.id.name);
        machineID = findViewById(R.id.machine_id);
        activePower = findViewById(R.id.active_power);
        reactivePower = findViewById(R.id.reactive_power);
        apparentPower = findViewById(R.id.apparent_power);
        description = findViewById(R.id.description);

        setInfo();

    }

    public List<String> parseJson(String json) {
        List<String> parsedJson = new ArrayList<>();

        json = json.replace("\"", "").replace("{", "").replace("}", "");
        String[] split = json.split(",");

        for(String element : split){
            String[] tmp = element.split(":");
            if(Objects.equals(tmp[1], "null")){
                parsedJson.add("Not available");
            }
            else {
                parsedJson.add(tmp[1]);
            }
        }

        return parsedJson;
    }

    public void setInfo() {
        machineName.setText(jsonElements.get(1));
        machineID.setText(jsonElements.get(0));
        activePower.setText(jsonElements.get(3));
        reactivePower.setText(jsonElements.get(4));
        apparentPower.setText(jsonElements.get(5));
        description.setText(jsonElements.get(2));
    }

    public void resetTimer() {
        this.timeoutHandler.resetTimer();
    }

    @Override
    public void onTimeout() {
        getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
        resetTimer();
    }

    @NonNull
    @Override
    public Loader<WebServiceResult<String>> onCreateLoader(final int id, @Nullable final Bundle args) {
        progress.show();
        return new IDLoader(this, jsonElements.get(0));
    }

    @Override
    public void onLoadFinished(@NonNull final Loader<WebServiceResult<String>> loader, final WebServiceResult<String> data) {
        progress.dismiss();

        if (data == null) {
            // možda neki exception
            return;
        }

        if (data.getException() == null) {
            jsonElements = parseJson(data.getResult());
            setInfo();

        } else {
            //toast
            //CommonUtils.showDialog(getActivity(), data.getException().getMessage());
        }
    }

    @Override
    public void onLoaderReset(@NonNull final Loader<WebServiceResult<String>> loader) {
    }

    @Override
    protected void onStop() {
        super.onStop();
        getSupportLoaderManager().destroyLoader(LOADER_ID);
    }
}
