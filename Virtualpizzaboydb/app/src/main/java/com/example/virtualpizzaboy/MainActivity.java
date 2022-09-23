package com.example.virtualpizzaboy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity
{
    FirebaseAuth auth = FirebaseAuth.getInstance();
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setting splash screen
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, CustomerWelcome.class));
                FirebaseUser firebaseUser = auth.getCurrentUser();
                // If user is already logged in, user will be redirected to menu else to signup
                if(auth.getCurrentUser() != null){
                    startActivity(new Intent(getApplicationContext(),CustomerWelcome.class));
                    finish();
                }
                else
                  startActivity(new Intent(MainActivity.this, login.class));
            }
        }, 1000);
        
    }
}