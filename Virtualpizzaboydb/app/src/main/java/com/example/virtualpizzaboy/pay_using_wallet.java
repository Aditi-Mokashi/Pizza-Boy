package com.example.virtualpizzaboy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import pl.droidsonroids.gif.GifImageView;

import static com.example.virtualpizzaboy.payment_method_display.NOTIFICATION;

public class pay_using_wallet extends AppCompatActivity {

    EditText pin_textbox_one, pin_textbox_two, pin_textbox_three, pin_textbox_four;
    Button verifyPin;
    String pin, uid;
    int amount;
    DatabaseHelper databaseHelper = new DatabaseHelper(pay_using_wallet.this);
    check_otp checkOtp;
    static int token = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_using_wallet);

        checkPin();
    }

    private void checkPin()
    {
        pin_textbox_one = findViewById(R.id.pin_edit_box1);
        pin_textbox_two = findViewById(R.id.pin_edit_box2);
        pin_textbox_three = findViewById(R.id.pin_edit_box3);
        pin_textbox_four = findViewById(R.id.pin_edit_box4);
        verifyPin = findViewById(R.id.verify_pin);

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Cursor cursor = databaseHelper.getRecord(uid);
            cursor.moveToFirst();
            if(cursor.getCount()>0){
                pin = cursor.getString(cursor.getColumnIndex("pin"));
                amount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("amount")));
            }
            else{
                Toast.makeText(getApplicationContext(),"You don't have a wallet. Create one",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),CreateWallet.class));
            }

        }
        else{
            Toast.makeText(getApplicationContext(),"No user signed in",Toast.LENGTH_LONG).show();
        }


        // after text is changed, send all edit text to generic text watcher which will find
        // the field where OTP is entered and shift focus to next one
        EditText[] edit = {pin_textbox_one, pin_textbox_two, pin_textbox_three, pin_textbox_four};

        pin_textbox_one.addTextChangedListener(new GenericTextWatcher(pin_textbox_one, edit));
        pin_textbox_two.addTextChangedListener(new GenericTextWatcher(pin_textbox_two, edit));
        pin_textbox_three.addTextChangedListener(new GenericTextWatcher(pin_textbox_three, edit));
        pin_textbox_four.addTextChangedListener(new GenericTextWatcher(pin_textbox_four, edit));

        verifyPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String enteredPin = pin_textbox_one.getText().toString() +
                        pin_textbox_two.getText().toString() +
                        pin_textbox_three.getText().toString() +
                        pin_textbox_four.getText().toString();


                if (!pin.equals(enteredPin)) {
                    Toast.makeText(getApplicationContext(),
                            "Invalid pin",
                            Toast.LENGTH_SHORT).show();
                }
                // if your wallet doesn't have enough money
                else if(amount < CustomerCart.totalPrice) {

                    AlertDialog.Builder alertDialogBuilder
                            = new AlertDialog.Builder(pay_using_wallet.this);
                    alertDialogBuilder.setTitle("Not enough amount");
                    alertDialogBuilder.setMessage("You don't have enough money in VPB wallet!\n" +
                            "would you like to check VPB wallet?");

                    // ask user to check wallet, if yes go to wallet else display payment methods
                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(getApplicationContext(), MyWallet.class));
                            finish();
                        }
                    })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(getApplicationContext(), payment_method_display.class));
                                    finish();
                                }
                            });

                    alertDialogBuilder.show();
                }
                // User has entered correct pin and has enough money
                else
                {
                    GifImageView gif = findViewById(R.id.success);
                    gif.setVisibility(View.VISIBLE);
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            gif.setVisibility(View.INVISIBLE);
                            startActivity(new Intent(getApplicationContext(),CustomerWelcome.class));
                            finish();
                        }
                    }, 2500);
                    //checkOtp.showOrderToken();
                    createNotificationChannels();
                    showNotification(view);
                    amount = amount - CustomerCart.totalPrice;
                    databaseHelper.updateData(uid,pin,String.valueOf(amount));
                }
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