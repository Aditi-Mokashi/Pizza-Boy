package com.example.virtualpizzaboy;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Random;

import static com.example.virtualpizzaboy.payment_method_display.NOTIFICATION;

public class payment_gateway extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    Button proceed;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);

        Spinner spinner_date = (Spinner) findViewById(R.id.spinner_month);
        Spinner spinner_year = (Spinner) findViewById(R.id.spinner_year);
        Spinner spinner_card_type = (Spinner) findViewById(R.id.spinner_card_type);

        EditText accountHolderName = (EditText) findViewById(R.id.accountHolderName);
        EditText accountNumber = (EditText) findViewById(R.id.accountNumber);
        EditText cvv = (EditText) findViewById(R.id.cvv);

        // set values to spinner for date and year
        String[] dates = new String[32], years = new String[13],
                card_types = {"Visa/Mastercard","Credit card","Rupay",};
        for(int i = 0; i < 30; i++)
            dates[i] = Integer.toString(i+1);

        for(int i = 0; i < 12; i++)
            years[i] = Integer.toString(i+2021);

        // set card types to card type spinner
        ArrayAdapter<String> adapterCardType = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, card_types);
        adapterCardType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_card_type.setAdapter(adapterCardType);
        spinner_card_type.setOnItemSelectedListener(this);

        // set dates to date spinner
        ArrayAdapter<String> adapterDate = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, dates);
        adapterDate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_date.setAdapter(adapterDate);
        spinner_date.setOnItemSelectedListener(this);

        // set years to year spinner
        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, dates);
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_year.setAdapter(adapterYear);
        spinner_year.setOnItemSelectedListener(this);

        proceed = (Button) findViewById(R.id.proceed_payment_button);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textAccountHolderName = accountHolderName.getText().toString();
                String textAccountNumber = accountNumber.getText().toString();
                String textCvv = cvv.getText().toString();
                // if user has filled all the fields then proceed further
                if(textAccountNumber.equals("") || textAccountHolderName.equals("")
                        || textCvv.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please enter all the fields",Toast.LENGTH_LONG).show();
                }
                else{
                    startActivity(new Intent(payment_gateway.this,check_otp.class));
                    createNotificationChannels();
                    showNotification(v);
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
        // generating a random 4 digit number as OTP
        Random random = new Random();
        check_otp.sentOtp = String.format("%04d", random.nextInt(10000));

        String textOtp = "OTP for your order in VPB is " + check_otp.sentOtp;

        System.out.println("Inside show notification");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION)
                .setSmallIcon(R.drawable.logo_splash_screen)
                .setContentTitle("OTP")
                .setContentText(textOtp)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());
        System.out.println("Outside show notification");

    }
}