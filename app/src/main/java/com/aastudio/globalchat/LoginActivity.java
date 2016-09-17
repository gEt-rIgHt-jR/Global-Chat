package com.aastudio.globalchat;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class LoginActivity extends AppCompatActivity {

    private EditText mUsernameView;
    private String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MobileAds.initialize(getApplicationContext(), getString(R.string.banner_home_footer));
        AdView mAdView = (AdView) findViewById(R.id.home_banner_ad);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mUsernameView = (EditText) findViewById(R.id.username_input);
        mUsernameView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    checkInput();
                    return true;
                }
                return false;
            }
        });
    }

    public void join(View view) {
        if (!checkInput())
            return;
        if (!checkInternet()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.no_internet).setTitle("No Internet");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            return;
        }

        Intent intent = new Intent(getApplication(), MainActivity.class);
        intent.putExtra("username", mUsername);
        startActivity(intent);
    }

    private boolean checkInput() {
        mUsernameView.setError(null);

        String username = mUsernameView.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            mUsernameView.requestFocus();
            return false;
        }

        mUsername = username;
        return true;
    }

    private boolean checkInternet() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}



