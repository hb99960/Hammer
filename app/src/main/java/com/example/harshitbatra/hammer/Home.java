package com.example.harshitbatra.hammer;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Home extends AppCompatActivity
{

    TextView tvhellouser;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent receivedIntent = getIntent();

        String username = receivedIntent.getStringExtra("username");
        String password = receivedIntent.getStringExtra("password");

        tvhellouser = (TextView) findViewById(R.id.tvhellouser);
        tvhellouser.setText("Hello " + username);
        tvhellouser.setTextColor(Color.GREEN);
    }
}
