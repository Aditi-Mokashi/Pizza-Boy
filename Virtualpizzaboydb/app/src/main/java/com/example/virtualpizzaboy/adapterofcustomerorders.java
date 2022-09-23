package com.example.virtualpizzaboy;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.annotations.NotNull;

import java.io.ObjectInputStream;


public class adapterofcustomerorders extends FirebaseRecyclerAdapter<datamodel,adapterofcustomerorders.myviewholder> {

    public adapterofcustomerorders(@NonNull FirebaseRecyclerOptions<datamodel> options) {
        super(options);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull myviewholder holder, int position, @NonNull datamodel model) {
        holder.name.setText(model.getName());
        holder.qty.setText(model.getQty());
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design3,parent,false);
        return new myviewholder(view);
    }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getRef().removeValue();
    }


    static class myviewholder extends RecyclerView.ViewHolder{
        TextView name,qty;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.headername);
            qty = (TextView)itemView.findViewById(R.id.quantity);
        }
    }

}
