package com.example.virtualpizzaboy;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

public class CustomerLogout extends Fragment{
    FirebaseAuth mfirebaseauth = FirebaseAuth.getInstance();
    public CustomerLogout() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setMessage("Are you sure?")

                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        mfirebaseauth.signOut();
                        Intent intent = new Intent(getContext(),login.class);
                        startActivity(intent);
                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getContext(),CustomerWelcome.class));
            }
        });

        AlertDialog alert1 = alert.create();
        alert1.show();
        // Inflate the layout for this fragment
       return inflater.inflate(R.layout.customer_logout, container, false);

    }
}
