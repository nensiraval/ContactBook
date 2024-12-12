package com.example.contactbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("MyData", MODE_PRIVATE);

                Boolean check = preferences.getBoolean("data", false);

                if (check) {
                    startActivity(new Intent(MainActivity.this, Userlist.class));
                } else {
                    startActivity(new Intent(MainActivity.this, Login_Page.class));
                    finish();
                }
            }
        }, 2000);
    }
}