package com.example.virtualpizzaboy;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class CustomerCart extends Fragment{
    static int totalPrice;
    public String uid;
    RecyclerView recyclerView;
    adapterofcart adapterofcart;
    FirebaseUser firebaseUser;
    Button proceed;

    public CustomerCart(){}
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customer_cart, container, false);


        recyclerView = view.findViewById(R.id.rview);
        proceed = view.findViewById(R.id.proceedtopayment);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<datamodel> options = new FirebaseRecyclerOptions.Builder<datamodel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Cart items"),datamodel.class).build();
        adapterofcart = new adapterofcart(options);
        recyclerView.setAdapter(adapterofcart);
        TextView finalprice = view.findViewById(R.id.finalprice);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Cart items");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                int sum = 0;
                if(snapshot.exists()){
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Map<String,Object> map = (Map<String, Object>) ds.getValue();
                        Object price = map.get("price");
                        int pvalue = Integer.parseInt(String.valueOf(price));
                        sum += pvalue;
                        finalprice.setText(String.valueOf(sum));
                    }
                    totalPrice = sum;
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


       proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().startActivity(new Intent(getContext(),payment_method_display.class));
            }
        });


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        adapterofcart.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterofcart.stopListening();
    }

}
