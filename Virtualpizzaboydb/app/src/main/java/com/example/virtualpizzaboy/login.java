package com.example.virtualpizzaboy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class login extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    private FirebaseAuth auth;
    String text;
    int i;
    String[] users = { "Customer", "Staff" };
    TextView register;
    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Spinner spin = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);

        auth = FirebaseAuth.getInstance();

        register = (TextView) findViewById(R.id.registerHere);
        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(login.this ,register.class);
                startActivity(intent);
            }
        });

        Button login = findViewById(R.id.login);
        email = (EditText) findViewById(R.id.lemail);
        password = (EditText) findViewById(R.id.lpassword);


        String text = "New to VPB? REGISTER.";
        SpannableString ss = new SpannableString(text);
        ClickableSpan clickableSpan1 = new ClickableSpan()
        {
            @Override
            public void onClick(@NonNull View widget)
            {
                Intent intent = new Intent(getApplicationContext(), register.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds)
            {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#6f2d3d"));
            }
        };
        ss.setSpan(clickableSpan1,12,20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        register.setText(ss);
        register.setMovementMethod(LinkMovementMethod.getInstance());


        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String textEmail = email.getText().toString().trim();
                String textPassword = password.getText().toString().trim();

                if(TextUtils.isEmpty(textEmail) || TextUtils.isEmpty(textPassword))
                    Toast.makeText(getApplicationContext(),
                            "Incorrect credentials :(",
                            Toast.LENGTH_SHORT).show();
                else
                    login(textEmail, textPassword);
            }
        });

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String textEmail = email.getText().toString();
                String textPassword = password.getText().toString();

                if(TextUtils.isEmpty(textEmail) || TextUtils.isEmpty(textPassword))
                    Toast.makeText(getApplicationContext(),
                            "Incorrect credentials :(",
                            Toast.LENGTH_SHORT).show();
                else
                    login(textEmail, textPassword);

               // finish();
            }
        });
    }

    private void login(String textEmail, String textPassword)
    {
        auth.signInWithEmailAndPassword(textEmail, textPassword)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>()
                {
                    @Override
                    public void onSuccess(AuthResult authResult)
                    {
                        Toast.makeText(getApplicationContext(),
                                "Login successful :)",
                                Toast.LENGTH_LONG).show();

                       // finish();
                        if(i==1){
                            startActivity(new Intent(getApplicationContext(), CustomerWelcome.class));
                        }
                        else if(i==2){
                            startActivity(new Intent(getApplicationContext(), AdminWelcome.class));
                        }
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id)
    {
        text = arg0.getItemAtPosition(position).toString();
        if(text.equals("Customer")){
                i =1;
        }
        else if(text.equals("Staff")){
            i = 2;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0)
    {
        // TODO - Custom Code
    }
}