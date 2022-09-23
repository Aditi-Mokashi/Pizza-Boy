package com.example.virtualpizzaboy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.annotations.NotNull;


public class adapterofbse extends FirebaseRecyclerAdapter<datamodel,adapterofbse.myviewholder> {

    public adapterofbse(@NonNull FirebaseRecyclerOptions<datamodel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull myviewholder holder, int position, @NonNull datamodel model) {
        holder.name.setText(model.getName());
        holder.desc.setText(model.getDesc());
        holder.price.setText(model.getPrice());
        Glide.with(holder.img.getContext()).load(model.getPurl()).into(holder.img);
        holder.addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction().add(R.id.frame, new extrasdescription(model.getName(),model.getPrice(),model.getPurl()))
                        .addToBackStack(null).commit();

            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design,parent,false);
        return new myviewholder(view);
    }


    static class myviewholder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView name,desc,price,addtocart;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img=(ImageView) itemView.findViewById(R.id.img1);
            name = (TextView)itemView.findViewById(R.id.t1);
            desc = (TextView)itemView.findViewById(R.id.t2);
            price = (TextView)itemView.findViewById(R.id.pricetxt);
            addtocart = (TextView)itemView.findViewById(R.id.addtocarttxt);
        }
    }

}
