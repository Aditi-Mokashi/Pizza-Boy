package com.example.virtualpizzaboy;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class NonvegFragment extends Fragment {
    RecyclerView recyclerView;
    adapterofpizza myadapter;
    public NonvegFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_nonveg, container, false);
        recyclerView = view.findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<datamodel> options = new FirebaseRecyclerOptions.Builder<datamodel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Nonvegpizzas"),datamodel.class).build();
        myadapter = new adapterofpizza(options);
        recyclerView.setAdapter(myadapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        myadapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        myadapter.stopListening();
    }

}