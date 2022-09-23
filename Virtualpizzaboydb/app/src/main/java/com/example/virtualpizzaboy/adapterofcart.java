package com.example.virtualpizzaboy;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;


public class adapterofcart extends FirebaseRecyclerAdapter<datamodel,adapterofcart.myviewholder> {

    public adapterofcart(@NonNull FirebaseRecyclerOptions<datamodel> options) {
        super(options);
    }
    FirebaseUser user;

    @Override
    public void onBindViewHolder(@NonNull @NotNull myviewholder holder, int position, @NonNull datamodel model) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        holder.name.setText(model.getName());
        holder.qty.setText(model.getQty());
        holder.price.setText(model.getPrice());
        holder.deletefromcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.deletefromcart.getContext());
                builder.setTitle("Alert!");
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Cart items").child(getRef(position).getKey()).removeValue();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design2,parent,false);
        return new myviewholder(view);
    }


    static class myviewholder extends RecyclerView.ViewHolder{
        TextView name,qty,price;
        ImageView deletefromcart;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.headername);
            qty = (TextView)itemView.findViewById(R.id.quantity);
            price = (TextView)itemView.findViewById(R.id.pricetxt);
            deletefromcart = (ImageView)itemView.findViewById(R.id.deletefromcart);
        }
    }

}
