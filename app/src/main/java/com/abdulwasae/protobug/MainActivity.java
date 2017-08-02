package com.abdulwasae.protobug;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import protobug.v0.GetTimeReq;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = new Object() {}.getClass().getSimpleName();
    Client mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        this.findViewById(R.id.button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        try {
            this.getClient().getTime(GetTimeReq.getDefaultInstance());
        } catch (ServerException e) {
            Log.d(TAG, "onClick: " + e);
            e.printStackTrace();
        }
    }

    synchronized public Client getClient() {
        if (mClient == null) {
            mClient = new Client();
        }
        return mClient;
    }
}
