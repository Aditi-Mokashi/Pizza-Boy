package com.example.virtualpizzaboy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import pl.droidsonroids.gif.GifImageView;

import static com.example.virtualpizzaboy.payment_method_display.NOTIFICATION;

// sends and checks OTP
// displays order token
public class check_otp extends AppCompatActivity {

    public check_otp(){}

    EditText otp_textbox_one, otp_textbox_two, otp_textbox_three, otp_textbox_four;
    Button verify, resend_otp;
    boolean otpValidation;
    int token = 0;
    public static String sentOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_otp);

        verify = (Button)findViewById(R.id.verify_otp);

        resend_otp = (Button) findViewById(R.id.resend_otp_button);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkOtp();
            }
        });

        resend_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNotificationChannels();
                showNotification(v);
                checkOtp();
            }
        });


    }

    void checkOtp()
    {

        otp_textbox_one = findViewById(R.id.otp_edit_box1);
        otp_textbox_two = findViewById(R.id.otp_edit_box2);
        otp_textbox_three = findViewById(R.id.otp_edit_box3);
        otp_textbox_four = findViewById(R.id.otp_edit_box4);
//        verify = (Button)findViewById(R.id.verify_otp);


        // after text is changed, send all edit text to generic text watcher which will find
        // the field where OTP is entered and shift focus to next one
        EditText[] edit = {otp_textbox_one, otp_textbox_two, otp_textbox_three, otp_textbox_four};

        otp_textbox_one.addTextChangedListener(new GenericTextWatcher(otp_textbox_one, edit));
        otp_textbox_two.addTextChangedListener(new GenericTextWatcher(otp_textbox_two, edit));
        otp_textbox_three.addTextChangedListener(new GenericTextWatcher(otp_textbox_three, edit));
        otp_textbox_four.addTextChangedListener(new GenericTextWatcher(otp_textbox_four, edit));


        String enteredOtp = otp_textbox_one.getText().toString() +
                otp_textbox_two.getText().toString() +
                otp_textbox_three.getText().toString() +
                otp_textbox_four.getText().toString();

        if(sentOtp.equals(enteredOtp))
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

            showOrderToken();
        }
        else
        {
            Toast.makeText(getApplicationContext(),
                    "Invalid OTP",
                    Toast.LENGTH_LONG).show();
        }

    }

    public void showOrderToken()
    {
        int token = 0;
        token++;
        // display the token
        Random random = new Random();
        int counterNumber = 1;
        counterNumber = Integer.parseInt(String.valueOf(random.nextInt(5)));
        String order_token = "Token number : " + token + "\nCounter Number : " + counterNumber + " ";

       Toast.makeText(this, order_token, Toast.LENGTH_LONG).show();
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