package hr.fer.android.sglab.qr.widgets;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import hr.fer.android.sglab.qr.utils.SharedPreferencesUtils;

public class SendReferenceAsyncTask extends AsyncTask<Object, Integer, Boolean> {

    private SharedPreferencesUtils prefsManager;
    private Integer machineId;

    public SendReferenceAsyncTask(Context context, Integer machineId) {
        this.prefsManager = SharedPreferencesUtils.get(context);
        this.machineId = machineId;
    }

    @Override
    protected Boolean doInBackground(Object... objects) {
        final String command = (String) objects[0];
        final Object value = objects[1];
        try {
            final URL url = new URL(prefsManager.getWebServiceEndpoint() + machineId + "/" + command);
            final HttpURLConnection con = (HttpURLConnection) url.openConnection();
            final String encoded = Base64.encodeToString(
                    (prefsManager.getUsername() + ":" + prefsManager.getPassword()).getBytes(),
                    Base64.NO_WRAP
            ).trim();

            con.setRequestProperty("Authorization", "Basic " + encoded);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestMethod("POST");

            JSONObject body = new JSONObject();
            body.put("value", value);

            con.setDoInput(true);
            con.setInstanceFollowRedirects(false);
            con.connect();

            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
            writer.write(body.toString());
            writer.close();

            StringBuilder resultString = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                resultString.append(line);
            }
            rd.close();
            Log.d(SendReferenceAsyncTask.class.getSimpleName(), resultString.toString());
            return true;
        } catch (Exception e) {
            Log.e(SendReferenceAsyncTask.class.getSimpleName(), e.toString());
            return false;
        }
    }
}
