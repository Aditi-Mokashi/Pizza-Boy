package com.example.virtualpizzaboy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class CreateWallet extends AppCompatActivity {
DatabaseHelper databaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wallet);

        final EditText pin = (EditText) findViewById(R.id.enterPin);
        final EditText reEnterPin = (EditText) findViewById(R.id.reEnterPin);
        final Button proceed = (Button) findViewById(R.id.proceed_pin_button);

        // after user clicks proceed button check whether pins are valid and create wallet
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textPin = pin.getText().toString().trim();
                String textReEnterPin = reEnterPin.getText().toString().trim();

                if(textPin.equals("") || textReEnterPin.equals(""))
                {
                    Toast.makeText(getApplicationContext(),
                            "Fill out all fields",
                            Toast.LENGTH_LONG).show();
                }

                else {
                    if (textPin.equals(textReEnterPin)) {
                        String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        if (databaseHelper.insertData(uId, textPin, "0")) {
                            Toast.makeText(getApplicationContext(),
                                    "Wallet created!",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MyWallet.class));
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Wallet not created",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),
                                "Invalid pin",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
}