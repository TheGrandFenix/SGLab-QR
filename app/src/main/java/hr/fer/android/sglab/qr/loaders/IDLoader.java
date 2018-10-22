package hr.fer.android.sglab.qr.loaders;

/*
 * Created by lracki on 12.10.2018..
 */

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import hr.fer.android.sglab.qr.loaders.result.WebServiceResult;
import hr.fer.android.sglab.qr.utils.SharedPreferencesUtils;

public class IDLoader extends BaseLoader<WebServiceResult<String>>{

    private final String machineID;

    private SharedPreferencesUtils prefsManager;

    public IDLoader(final Context context, final String machineID) {
        super(context);

        prefsManager = SharedPreferencesUtils.get(context);

        this.machineID = machineID;
    }

    @Nullable
    @Override
    public WebServiceResult<String> loadInBackground() {
        synchronized(this) {
            if(result == null) {
                try {
                    final URL url = new URL(prefsManager.getWebServiceEndpoint() + machineID);
                    final HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    final String encoded = Base64.encodeToString((prefsManager.getUsername() + ":" + prefsManager.getPassword()).getBytes(), Base64.NO_WRAP).trim();

                    con.setRequestProperty("Authorization", "Basic " + encoded);
                    con.setRequestProperty("Content-Type", "application/json");
                    con.setRequestMethod("GET");

                    StringBuilder resultString = new StringBuilder();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String line;
                    while ((line = rd.readLine()) != null) {
                        resultString.append(line);
                    }
                    rd.close();

                    result = new WebServiceResult<>(resultString.toString());

                } catch (Exception e) {
                    result = new WebServiceResult<>(e);
                }
            }
        }

        return result;
    }
}
