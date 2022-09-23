package com.example.virtualpizzaboy;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class AdminWelcome extends AppCompatActivity{
    public AdminWelcome(){}
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    Toolbar tb;
    RecyclerView recyclerView;
    adapterofcustomerorders adapterofcustomerorders;
    FirebaseRecyclerOptions<datamodel> options;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_welcome);
        tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,tb,R.string.open,R.string.close);

        mToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        recyclerView = findViewById(R.id.rview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        FirebaseRecyclerOptions<datamodel> options  = new FirebaseRecyclerOptions.Builder<datamodel>()
                .setQuery(FirebaseDatabase.getInstance().getReference("Cart items"),datamodel.class).build();
        adapterofcustomerorders = new adapterofcustomerorders(options);
        recyclerView.setAdapter(adapterofcustomerorders);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapterofcustomerorders.deleteItem(viewHolder.getAdapterPosition());

            }
        }).attachToRecyclerView(recyclerView);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                //Fragment fragment =null ;
                switch (id)
                {
                    case R.id.menu:getSupportFragmentManager().beginTransaction().replace(R.id.frame,AdminEditMenu.class,null).commit();
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        tb.setTitle("Edit Menu");
                        break;

                    case R.id.special:getSupportFragmentManager().beginTransaction().replace(R.id.frame,AdminEditSpecial.class,null).commit();
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        tb.setTitle("Today's Special");
                        break;

                    case R.id.setting: getSupportFragmentManager().beginTransaction().replace(R.id.frame,SettingsFragment.class,null).commit();
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        tb.setTitle("Setting");
                        break;

                    case R.id.logout:getSupportFragmentManager().beginTransaction().replace(R.id.frame,CustomerLogout.class,null).commit();
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        tb.setTitle("Logout");
                        break;
                    default: return true;

                }
                return true;
            }
        });

    }

    ItemTouchHelper.SimpleCallback itemTouchHelper = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onStart() {
        super.onStart();
        adapterofcustomerorders.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterofcustomerorders.stopListening();
    }



}