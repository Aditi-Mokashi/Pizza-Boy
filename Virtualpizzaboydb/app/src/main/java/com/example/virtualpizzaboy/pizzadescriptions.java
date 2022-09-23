package com.example.virtualpizzaboy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class pizzadescriptions extends Fragment implements AdapterView.OnItemSelectedListener {
    String[] crusts = { "Cheese Burst", "Classic Hand Tossed", "Wheat Thin Crust", "Fresh Pan Pizza", "New Hand Tossed"};
    String name,price,purl,item,qty;
    TextView nameholder,priceholder;
    DatabaseReference cartitems;
    ArrayAdapter aa;
    Spinner spinner;
    NumberPicker np;
    FirebaseUser user;
    String uid;
    static boolean cartisempty = false;

    public pizzadescriptions(String name, String price, String purl) {
        this.name = name;
        this.price = price;
        this.purl = purl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.pizza_description, container, false);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        ImageView imgholder = view.findViewById(R.id.img2);
        nameholder = view.findViewById(R.id.name);
        priceholder = view.findViewById(R.id.price);
        Button addtocart = view.findViewById(R.id.addtocartfinal);
        nameholder.setText(name);
        priceholder.setText(price);
        Glide.with(getContext()).load(purl).into(imgholder);
         spinner = (Spinner)view.findViewById(R.id.spinner2);
        aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,crusts);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);
        spinner.setSelection(0,false);
        spinner.setOnItemSelectedListener(this);
        np = (NumberPicker)view.findViewById(R.id.numberpicker);
        np.setMinValue(1);
        np.setMaxValue(15);
        qty = String.valueOf(np.getValue());
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
                cartisempty = true;
                insertcartdata();
            }
        });

        return view;
    }
    public void insertcartdata() {
        String n = nameholder.getText().toString();
        String p = priceholder.getText().toString();
        String c = (String)spinner.getSelectedItem();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = crusts[position];
        Toast.makeText(getContext(),"Selected = " + item,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}