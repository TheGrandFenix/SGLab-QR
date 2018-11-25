package hr.fer.android.sglab.qr;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import hr.fer.android.sglab.qr.loaders.IDLoader;
import hr.fer.android.sglab.qr.loaders.result.WebServiceResult;
import hr.fer.android.sglab.qr.pojo.Machine;
import hr.fer.android.sglab.qr.utils.TimeoutHandler;

import static hr.fer.android.sglab.qr.ScanQRCodeActivity.LOADER_ID;
import static hr.fer.android.sglab.qr.ScanQRCodeActivity.QR_RESULT;
import static hr.fer.android.sglab.qr.utils.NumberUtils.formatDouble;
import static hr.fer.android.sglab.qr.utils.NumberUtils.formatInteger;

public class MenuActivity
        extends AppCompatActivity
        implements TimeoutHandler.TimeoutListener,
        LoaderManager.LoaderCallbacks<WebServiceResult<String>> {

    private TextView machineName;
    private TextView machineID;
    private TextView activePower;
    private TextView reactivePower;
    private TextView apparentPower;
    private TextView description;

    private Machine currentMachine;

    private TimeoutHandler timeoutHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        timeoutHandler = new TimeoutHandler(this, TimeoutHandler.EVENT_TIMEOUT, this);

        String qrResultJSON = getIntent().getStringExtra(QR_RESULT);
        currentMachine = parseJson(qrResultJSON);

        machineName = findViewById(R.id.name);
        machineID = findViewById(R.id.machine_id);
        activePower = findViewById(R.id.active_power);
        reactivePower = findViewById(R.id.reactive_power);
        apparentPower = findViewById(R.id.apparent_power);
        description = findViewById(R.id.description);

        setInfo();
    }

    public Machine parseJson(String json) {
        Machine machine = new Machine();
        try {
            JSONObject jsonObject = new JSONObject(json);
            machine.setId(jsonObject.getInt(Machine.ID));
            machine.setName(jsonObject.getString(Machine.NAME));
            machine.setDescription(jsonObject.getString(Machine.DESCRIPTION));
            machine.setActivePower(jsonObject.getDouble(Machine.ACTIVE_POWER));
            machine.setReactivePower(jsonObject.getDouble(Machine.REACTIVE_POWER));
            machine.setApparentPower(jsonObject.getDouble(Machine.APPARENT_POWER));

        } catch (JSONException e) {
            Log.e(MenuActivity.class.getSimpleName(), e.toString());
            e.printStackTrace();
        }

        return machine;
    }

    public void setInfo() {
        machineName.setText(currentMachine.getName());
        machineID.setText(formatInteger(currentMachine.getId()));
        activePower.setText(formatDouble(currentMachine.getActivePower()));
        reactivePower.setText(formatDouble(currentMachine.getReactivePower()));
        apparentPower.setText(formatDouble(currentMachine.getApparentPower()));
        description.setText(currentMachine.getDescription());
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
    public Loader<WebServiceResult<String>> onCreateLoader(
            final int id,
            @Nullable final Bundle args) {

        return new IDLoader(this, formatInteger(currentMachine.getId()));
    }

    @Override
    public void onLoadFinished(
            @NonNull final Loader<WebServiceResult<String>> loader,
            final WebServiceResult<String> data) {

        if (data == null) {
            Toast
                    .makeText(this, "Something went wrong with HTTP request", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        if (data.getException() == null) {
            currentMachine = parseJson(data.getResult());
            setInfo();
        } else {
            Toast
                    .makeText(this, data.getException().getMessage(), Toast.LENGTH_SHORT)
                    .show();
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
