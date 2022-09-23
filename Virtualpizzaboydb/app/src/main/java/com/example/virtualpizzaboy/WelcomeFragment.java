package com.example.virtualpizzaboy;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


public class WelcomeFragment extends Fragment {
    String[] explore_menu_name = {"Veg Pizza","Non-Veg Pizza","Sides","Bevarages"};
    int[] explore_menu_images = {
            R.drawable.vegpizzahome,
            R.drawable.nonvegpizzahome,
            R.drawable.sideshome,
            R.drawable.drinkshome
    };
    String[] today_menu_name = {"Veg Pizza","Non-Veg Pizza","Sides","Bevarages"};
    int[] today_menu_images = {
//            R.drawable.veg,
//            R.drawable.non_veg,
//            R.drawable.sides,
//            R.drawable.drinks
    };
    GridView gridView_explore_menu,gridView_today_menu;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_welcome, container, false);
        gridView_explore_menu = view1.findViewById(R.id.explore_menu_list);

        gridView_explore_menu.setAdapter(new fragment_welcome_adapter_explore_menu(explore_menu_name,explore_menu_images,getContext()));

        gridView_explore_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment = new CustomerMenu();
                getFragmentManager().beginTransaction().replace(R.id.frame,fragment).commit();
            }
        });

        gridView_today_menu = view1.findViewById(R.id.today_special_list);
        gridView_today_menu.setAdapter(new fragment_welcome_adapter_today_menu(today_menu_name,today_menu_images,getContext()));

        gridView_today_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment = new CustomerSpecial();
                getFragmentManager().beginTransaction().replace(R.id.frame,fragment).commit();
            }
        });

        // Inflate the layout for this fragment
        return view1;

    }
}