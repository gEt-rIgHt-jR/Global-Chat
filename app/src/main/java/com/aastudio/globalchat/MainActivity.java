package com.aastudio.globalchat;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    MainFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        fragment = new MainFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame, fragment, "fragment");
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        final MainFragment fragment = (MainFragment) getSupportFragmentManager().findFragmentByTag("fragment");
        fragment.leave();
        Toast.makeText(MainActivity.this, "Leaving", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
}
