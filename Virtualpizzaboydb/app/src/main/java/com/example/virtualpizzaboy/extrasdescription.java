package com.example.virtualpizzaboy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class extrasdescription extends Fragment {
    String[] crusts = { "Cheese Burst", "Classic Hand Tossed", "Wheat Thin Crust", "Fresh Pan Pizza", "New Hand Tossed"};
    String name,price,purl,qty=String.valueOf(1);
    TextView nameholder,priceholder;
    DatabaseReference cartitems;
    FirebaseUser user;
    String uid;

    public extrasdescription(String name, String price, String purl) {
        this.name = name;
        this.price = price;
        this.purl = purl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.extrasdescription, container, false);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        ImageView imgholder = view.findViewById(R.id.img2);
        nameholder = view.findViewById(R.id.name);
        priceholder = view.findViewById(R.id.price);
        TextView addtocart = view.findViewById(R.id.addtocartfinal);
        nameholder.setText(name);
        priceholder.setText(price);
        Glide.with(getContext()).load(purl).into(imgholder);

        NumberPicker np = (NumberPicker)view.findViewById(R.id.numberpicker);
        np.setMinValue(1);
        np.setMaxValue(15);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                qty = String.valueOf(np.getValue());
                priceholder.setText(String.valueOf(Integer.parseInt(price)*Integer.parseInt(qty)));
            }
        });
        cartitems = FirebaseDatabase.getInstance().getReference().child("Cart items");
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertcartdata();
            }
        });

        return view;
    }
    private void insertcartdata() {
        String n = nameholder.getText().toString();
        String p = priceholder.getText().toString();
        String c = null;
        String q = qty;
        datamodel2 datamodel2 = new datamodel2(n,c,p,q);
        cartitems.push().setValue(datamodel2);
        Toast.makeText(getContext(),"Added to cart",Toast.LENGTH_LONG).show();
    }

    public void onBackPressed(){
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, new PizzaFragment())
                .addToBackStack(null).commit();
    }

}