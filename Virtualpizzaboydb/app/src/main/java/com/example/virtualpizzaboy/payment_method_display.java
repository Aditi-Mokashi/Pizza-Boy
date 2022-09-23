package com.example.virtualpizzaboy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

// display the cash or cart option
public class payment_method_display extends AppCompatActivity
{
    Button cash,wallet,netBanking;
    static final String NOTIFICATION = "custom notification channel";

    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    static int token = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method_display);

        // if cash is pressed directly show the token
        // else go to dummy payment gateway or launch gpay
        cash = (Button)findViewById(R.id.cashButton);
        wallet = (Button)findViewById(R.id.walletButton);
        netBanking = (Button)findViewById(R.id.netBankingButton);


        cash.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                // display the token
                createNotificationChannels();
                showNotification(v);
                startActivity(new Intent(getApplicationContext(),CustomerWelcome.class));
                finish();
            }
        });



        // Launch wallet
        wallet.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Cursor cursor = databaseHelper.getRecord(uid);
                cursor.moveToFirst();

                if(cursor.getCount() >= 0)
                {
                    startActivity(new Intent(payment_method_display.this, pay_using_wallet.class));
                    finish();
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                    builder.setCancelable(true);
                    builder.setMessage("You don't have a wallet.\nYou want to create one?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(getApplicationContext(), CreateWallet.class));
                            //finish();
                        }
                    })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getApplicationContext(),
                                            "Choose another option",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });

                    builder.show();
                }

            }
        });

        netBanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(payment_method_display.this,payment_gateway.class));

                finish();
            }
        });
    }

    private void createNotificationChannels() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NOTIFICATION, "Notifications", importance);
            channel.setDescription("Notification description");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void showNotification(View v) {

        token++;
        // display the token
        Random random = new Random();
        int counterNumber = 1;
        counterNumber = Integer.parseInt(String.valueOf(random.nextInt(5)));
        String order_token = "Token number : " + token + "\nCounter Number : " + counterNumber + " ";

        System.out.println("Inside show notification");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION)
                .setSmallIcon(R.drawable.logo_splash_screen)
                .setContentTitle("Your order is ready")
                .setContentText(order_token)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());
        System.out.println("Outside show notification");

    }


}