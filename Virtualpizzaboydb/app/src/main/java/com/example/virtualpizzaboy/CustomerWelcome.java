package com.example.virtualpizzaboy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class CustomerWelcome extends AppCompatActivity {
    Toolbar tb;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_welcome);

        tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,tb,R.string.open,R.string.close);

        mToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.frame,new WelcomeFragment()).commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                //Fragment fragment=null;
                switch (id)
                {
                    case R.id.home : getSupportFragmentManager().beginTransaction().replace(R.id.frame,WelcomeFragment.class,null).commit();
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        tb.setTitle("Home");
                        break;
                    case R.id.menu: getSupportFragmentManager().beginTransaction().replace(R.id.frame,CustomerMenu.class,null).commit();
                        //startActivity(new Intent(getApplicationContext(), CustomerMenu.class));
                            //startActivity(new Intent(getApplicationContext(), CustomerMenu.class));
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        tb.setTitle("Menu");
                        break;
                    case R.id.special: getSupportFragmentManager().beginTransaction().replace(R.id.frame,CustomerSpecial.class,null).commit();

                        //fragment = new com.example.virtualpizzaboy.CustomerSpecial();
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        tb.setTitle("Today's Special");
                        //getSupportFragmentManager().beginTransaction().replace(R.id.mainn,fragment).commit();
                        break;

                    case R.id.cart: getSupportFragmentManager().beginTransaction().replace(R.id.frame,CustomerCart.class,null).commit();
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        tb.setTitle("Cart");
                        break;

                    case R.id.wallet:
                        startActivity(new Intent(getApplicationContext(), MyWallet.class));
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        tb.setTitle("Wallet");
                        break;

                    case R.id.setting:getSupportFragmentManager().beginTransaction().replace(R.id.frame,SettingsFragment.class,null).commit();
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        tb.setTitle("Setting");
                        break;
                    case R.id.logout:getSupportFragmentManager().beginTransaction().replace(R.id.frame,CustomerLogout.class,null).commit();
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        tb.setTitle("Logout");
                        break;
                    case R.id.contact:String phone = "7757070299";
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                        startActivity(intent);
                        break;
                    default: return true;
                }
                //getSupportFragmentManager().beginTransaction().replace(R.id.frame,fragment).commit();
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
