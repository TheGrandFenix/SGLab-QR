package hr.fer.android.sglab.qr;

import android.os.AsyncTask;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpAsyncTask extends AsyncTask<String, Integer, String> {

    private static final String DEFAULT_ENDPOINT = "http://10.0.2.2:8080/api/sglab/machines/";
    private static final String USERNAME = "sglabadmin";
    private static final String PASSWORD = "sglabadmin";

    private WeakReference<InterfaceActivity> activityReference;

    HttpAsyncTask(InterfaceActivity context) {
        //Get reference to the InterfaceActivity which executed the task
        activityReference = new WeakReference<>(context);
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            final URL url = new URL(DEFAULT_ENDPOINT + strings[0]);
            final HttpURLConnection con = (HttpURLConnection) url.openConnection();
            final String encoded = Base64.encodeToString((USERNAME + ":" + PASSWORD).getBytes(), Base64.NO_WRAP).trim();

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

    @Override
    protected void onPostExecute(String result) {
        InterfaceActivity activity = activityReference.get();

        //Check if InterfaceActivity is still active
        if (activity == null || activity.isFinishing()) return;

        //Send data to InterfaceActivity if it is still active
        activity.processData(result);
    }
}
