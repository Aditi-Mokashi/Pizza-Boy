package com.example.virtualpizzaboy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class fragment_welcome_adapter_explore_menu extends BaseAdapter {

    private final String[] item_name;
    private final int[] item_image;
    Context context;

    public fragment_welcome_adapter_explore_menu(String[] item_names, int[] item_images, Context context) {
        this.item_name = item_names;
        this.item_image = item_images;
        this.context = context;
    }
    @Override
    public int getCount() {
        return item_image.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.fragment_welcome_single_item,null);

        ImageView img = view.findViewById(R.id.image);
        TextView city = view.findViewById(R.id.name);

        img.setImageResource(item_image[position]);
        city.setText(item_name[position]);

        return view;
    }
}
