package com.example.virtualpizzaboy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class register extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    private FirebaseAuth auth;

    String[] users = { "Customer", "Staff" };
    String selectedUser = new String();
    TextView login;
    Button register;
    EditText email, password, confirmPassword, phone;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        login = (TextView) findViewById(R.id.loginHere);
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(register.this ,login.class);
                startActivity(intent);
            }
        });

        Spinner spin = findViewById(R.id.rspinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);

        register = (Button) findViewById(R.id.register);
        email = (EditText) findViewById(R.id.register_email);
        phone = (EditText) findViewById(R.id.register_phone);
        password = (EditText) findViewById(R.id.register_password);
        confirmPassword = (EditText) findViewById(R.id.register_confirm_password);

        phone.requestFocus();


        String text = "Already Registered to VPB? LOGIN.";
        SpannableString ss = new SpannableString(text);
        ClickableSpan clickableSpan1 = new ClickableSpan()
        {
            @Override
            public void onClick(@NonNull View widget)
            {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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

        ss.setSpan(clickableSpan1,15,20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        login.setText(ss);
        login.setMovementMethod(LinkMovementMethod.getInstance());

        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String textEmail, textPassword, textConfirmPassword, textPhone;
                textEmail = email.getText().toString().trim();
                textPassword = password.getText().toString().trim();
                textConfirmPassword = confirmPassword.getText().toString().trim();
                textPhone = phone.getText().toString();

                if(textEmail.length() == 0 || textPassword.length() == 0 ||
                        !textConfirmPassword.equals(textPassword))
                {
                    Toast.makeText(getApplicationContext(),
                            "Incorrect credentials",
                            Toast.LENGTH_SHORT).show();
                }
                else if(textPassword.length() < 6)
                {
                    Toast.makeText(getApplicationContext(),
                            "Password too short!",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    registerUser(textEmail, textPassword, textPhone, selectedUser);
                }
            }
        });
    }

    private void registerUser(String textEmail, String textPassword, String textPhone, String userType)
    {
        auth.createUserWithEmailAndPassword(textEmail, textPassword).addOnCompleteListener(
                register.this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),
                                    "Registration successful :)", Toast.LENGTH_LONG).show();

                            saveUserInformation(textEmail, textPhone, textPassword, userType);

                            if(selectedUser=="Customer"){
                                startActivity(new Intent(register.this, CustomerWelcome.class));
                            }
                            else
                                startActivity(new Intent(register.this, AdminWelcome.class));

                            //finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),
                                    "Registration failed :(",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    void saveUserInformation(String email, String phone, String password, String userType)
    {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        assert firebaseUser != null;
        id = firebaseUser.getUid();
        User user = new User(id, email, phone, password, userType);


        // create a firebase database reference for top node and get current instance
        database = FirebaseDatabase.getInstance();
        // get the reference of child node of the top node
        databaseReference = database.getReference("Users");
        // store user information by creating references of user inside Users node
        databaseReference.child(String.valueOf(id)).setValue(user);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id)
    {
        Toast.makeText(getApplicationContext(),
                "Selected User : " + users[position],
                Toast.LENGTH_SHORT).show();

        selectedUser = users[position];
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0)
    {
        // TODO - Custom Code
    }

}