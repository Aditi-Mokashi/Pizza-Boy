package com.example.virtualpizzaboy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class add_money_to_wallet extends AppCompatActivity {

    EditText pin, amount;
    Button add, checkPin;
    String uId, textPin;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money_to_wallet);

        pin = (EditText) findViewById(R.id.enter_pin_add_money);
        amount = (EditText) findViewById(R.id.add_amount);
        add = (Button) findViewById(R.id.add_money_button);
        checkPin = (Button) findViewById(R.id.check_pin_add_money);

        // user cannot enter amount till the pin is correct
        amount.setEnabled(false);
        add.setEnabled(false);

        uId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        cursor = MyWallet.databaseHelper.getRecord(uId);
        cursor.moveToFirst();
        textPin = cursor.getString(cursor.getColumnIndex("pin"));

        checkPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // if the pin entered by user is correct amount is enabled
                if(textPin.equals(pin.getText().toString().trim()))
                {
                    amount.setEnabled(true);
                    add.setEnabled(true);
                    add_amount();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "Invalid pin",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void add_amount()
    {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText amountEntered = (EditText) findViewById(R.id.add_amount);
                // get the existing wallet amount from database
                String databaseAmount = cursor.getString(cursor.getColumnIndex("amount"));

                // check is user has entered valid amount if yes add amount to wallet in database
                String tempAddAmount = amountEntered.getText().toString().trim();
                if(tempAddAmount.equals("") || Integer.parseInt(tempAddAmount) <= 0)
                {
                    Toast.makeText(getApplicationContext(),
                            "Enter valid amount",
                            Toast.LENGTH_LONG).show();
                    amountEntered.setText("");
                }
                else
                {
                    // convert existing wallet amount and entered amount to int then add them and
                    // convert to string again then set value
                    databaseAmount = String.valueOf(Integer.parseInt(tempAddAmount)+Integer.parseInt(databaseAmount));
                    MyWallet.databaseHelper.updateData(uId, textPin, databaseAmount);
                    startActivity(new Intent(getApplicationContext(), MyWallet.class));
                    finish();
                }
            }
        });




    }
}