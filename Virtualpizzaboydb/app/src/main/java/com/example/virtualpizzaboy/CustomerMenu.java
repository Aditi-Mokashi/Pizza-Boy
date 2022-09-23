package com.example.virtualpizzaboy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.Timer;
import java.util.TimerTask;

import pl.droidsonroids.gif.GifImageView;

public class CustomerMenu extends Fragment {
    TabLayout tabLayout;
    TabItem vegpizza, beverage, extra, nonvegpizza;
    ViewPager viewPager;
    FloatingActionButton floatingActionButton;
    GifImageView gif;
    Timer timer;
    public CustomerMenu() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customer_menu, container, false);
        timer = new Timer();
        timer.schedule(new TimerTask() {
                           @Override
                           public void run() {
                               gif = view.findViewById(R.id.loading);
                               gif.setVisibility(View.INVISIBLE);
                           }
                       },
                5000);
        //setContentView(R.layout.customer_menu);
        tabLayout = view.findViewById(R.id.tablayout);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ffffff"));
        vegpizza = view.findViewById(R.id.vegpizza);
        nonvegpizza = view.findViewById(R.id.nonvegpizza);
        beverage = view.findViewById(R.id.beverage);
        extra = view.findViewById(R.id.extras);
        viewPager = view.findViewById(R.id.view_pager);
        floatingActionButton = view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerMenu menu = new CustomerMenu();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new CustomerCart())
                .addToBackStack(null).commit();
            }
        });
        PagerAdapter pagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

    private void onBackPressed() {
        startActivity(new Intent(getContext(), CustomerWelcome.class));
    }
}