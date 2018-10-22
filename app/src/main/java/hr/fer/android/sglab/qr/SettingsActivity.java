package hr.fer.android.sglab.qr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hr.fer.android.sglab.qr.utils.SharedPreferencesUtils;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText endpointUrl;
    private EditText username;
    private EditText password;

    private Button btnEdit;
    private Button btnSave;

    private SharedPreferencesUtils prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        prefs = SharedPreferencesUtils.get(this);

        endpointUrl = findViewById(R.id.endpoint_url);
        endpointUrl.setText(prefs.getWebServiceEndpoint());

        username = findViewById(R.id.username);
        username.setText(prefs.getUsername());

        password = findViewById(R.id.password);
        password.setText(prefs.getPassword());

        btnEdit = findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(this);

        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case (R.id.btn_edit) :
                endpointUrl.setEnabled(true);
                endpointUrl.requestFocus();

                username.setEnabled(true);
                password.setEnabled(true);

                btnEdit.setVisibility(View.INVISIBLE);
                btnSave.setVisibility(View.VISIBLE);
                break;
            case (R.id.btn_save) :
                if (TextUtils.isEmpty(endpointUrl.getText()) ||
                        TextUtils.isEmpty(username.getText()) ||
                        TextUtils.isEmpty(password.getText())) {
                    Toast.makeText(this, getResources().getString(R.string.message__fill_all_fields), Toast.LENGTH_LONG).show();
                    return;
                }

                prefs.setWebServiceEndpoint(endpointUrl.getText().toString());
                prefs.setUsername(username.getText().toString());
                prefs.setPassword(password.getText().toString());

                endpointUrl.setEnabled(false);
                username.setEnabled(false);
                password.setEnabled(false);

                btnEdit.setVisibility(View.VISIBLE);
                btnSave.setVisibility(View.GONE);
                break;
        }
    }
}
