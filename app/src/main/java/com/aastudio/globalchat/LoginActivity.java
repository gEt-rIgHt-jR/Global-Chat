package com.aastudio.globalchat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.morphingbutton.MorphingButton;

public class LoginActivity extends Activity {

    private EditText mUsernameView;
    private String mUsername;

    MorphingButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
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

        button = (MorphingButton) findViewById(R.id.morphing_button);
        /*MorphingButton.Params original = MorphingButton.Params.create()
                .duration(10)
                .cornerRadius(10) // 56 dp
                .width(button.getWidth()) // 56 dp
                .height(button.getHeight()) // 56 dp
                .color(R.color.colorAccent) // normal state color
                .colorPressed(R.color.button_blue)
                .text("JOIN"); // pressed state color
        button.morph(original);*/
    }

    public void join(View view) {
        if (!checkInput())
            return;
        if (!checkInternet())
            return;

        MorphingButton.Params circle = MorphingButton.Params.create()
                .duration(300)
                .cornerRadius(150) // 56 dp
                .width(150) // 56 dp
                .height(150) // 56 dp
                .color(Color.GREEN) // normal state color
                .colorPressed(R.color.mb_blue) // pressed state color
                .icon(R.drawable.ic_done); // icon
        button.morph(circle);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username", mUsername);
        startActivity(intent);
    }

    private boolean checkInput() {
        // Reset errors.
        mUsernameView.setError(null);

        String username = mUsernameView.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            // Error
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



