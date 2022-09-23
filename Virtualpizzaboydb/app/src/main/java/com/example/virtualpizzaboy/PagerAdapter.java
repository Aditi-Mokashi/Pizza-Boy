package com.example.virtualpizzaboy;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

public class PagerAdapter extends FragmentPagerAdapter {
    private final int numOfTabs;
    public PagerAdapter(FragmentManager fm, int numOfTabs){
        super(fm);
        this.numOfTabs = numOfTabs;
    }
    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new PizzaFragment();
            case 1 : return new NonvegFragment();
            case 2 : return new BeverageFragment();
            case 3 : return new ExtrasFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
