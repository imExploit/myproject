package com.yadishot.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yadishot.myapplication.UserActions.LoginActivity;

public class MainActivity extends AppCompatActivity {

    FrameLayout loginUsers;
    androidx.appcompat.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginUsers = findViewById(R.id.loginUsers);
        toolbar = findViewById(R.id.toolbar);


        // set toolbar
        toolbar.setTitle("سامانه کارآفرینی روستایی");
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);

        // set on clickListener

        loginUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

    }
}
