package hr.fer.android.sglab.qr.utils;

/*
 * Created by lracki on 12.10.2018..
 */

import android.content.Context;
import android.content.SharedPreferences;

import hr.fer.android.sglab.qr.BuildConfig;

import static hr.fer.android.sglab.qr.utils.SharedPreferencesUtils.PrefsKeys.ENDPOINT;
import static hr.fer.android.sglab.qr.utils.SharedPreferencesUtils.PrefsKeys.PASSWORD;
import static hr.fer.android.sglab.qr.utils.SharedPreferencesUtils.PrefsKeys.USERNAME;

public class SharedPreferencesUtils {

    static class PrefsKeys {
        static final String ENDPOINT = "endpoint";
        static final String USERNAME = "username";
        static final String PASSWORD = "password";
    }

    private final SharedPreferences preferenceManager;

    private SharedPreferencesUtils(Context context) {
        preferenceManager = context.getSharedPreferences(BuildConfig.DEFAULT_PREFS_CONTEXT, Context.MODE_PRIVATE);
    }

    public static SharedPreferencesUtils get(Context context) {
        return new SharedPreferencesUtils(context);
    }
    public void putString(String key, String string) {
        preferenceManager.edit().putString(key, string).apply();
    }
    public String getString(String key, String defaultValue) {
        return preferenceManager.getString(key, defaultValue);
    }

    // endpoint
    public void setWebServiceEndpoint(String endpoint) {
        preferenceManager.edit().putString(ENDPOINT, endpoint).apply();
    }
    public String getWebServiceEndpoint() {
        return preferenceManager.getString(ENDPOINT, BuildConfig.DEFAULT_ENDPOINT);
    }

    // username
    public String getUsername() {
        return preferenceManager.getString(USERNAME, BuildConfig.DEFAULT_USERNAME);
    }
    public void setUsername(String username) {
        preferenceManager.edit().putString(USERNAME, username).apply();
    }

    // password
    public String getPassword(){
        return preferenceManager.getString(PASSWORD, BuildConfig.DEFAULT_PASSWORD);
    }
    public void setPassword(String password){
        preferenceManager.edit().putString(PASSWORD, password).apply();
    }
}