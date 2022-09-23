package com.example.virtualpizzaboy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyWallet extends AppCompatActivity {

    String walletMoney;
    String rupeeString = "\u20B9" + " ";
    int currentWalletMoney;
    public static DatabaseHelper databaseHelper;

    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_wallet);

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseHelper = new DatabaseHelper(MyWallet.this);
        Cursor cursor = databaseHelper.getRecord(uid);
        cursor.moveToFirst();

        if(cursor.getCount() <= 0)
        {
            Toast.makeText(getApplicationContext(),
                    "You don't have a wallet, create now!",
                    Toast.LENGTH_LONG).show();

            startActivity(new Intent(getApplicationContext(), CreateWallet.class));
            finish();
        }
        else
        {
            TextView moneyTextView = (TextView) findViewById(R.id.myWalletMoney);
            currentWalletMoney = Integer.parseInt(cursor.getString(cursor.getColumnIndex("amount")));
            walletMoney = rupeeString + String.valueOf(currentWalletMoney);
            moneyTextView.setText(walletMoney);
        }

        FloatingActionButton addMoney = (FloatingActionButton) findViewById(R.id.addMoney);
        addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), add_money_to_wallet.class));
                finish();
            }
        });
    }
}