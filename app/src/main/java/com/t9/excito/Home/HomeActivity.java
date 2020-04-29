package com.t9.excito.Home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.t9.excito.R;
import com.t9.excito.Stores.StoresActivity;
import com.t9.excito.Utils.FetchCityTask;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity implements FetchCityTask.OnTaskCompleted,NavigationView.OnNavigationItemSelectedListener {


    Location mLastLocation;
    private static final int REQUEST_LOCATION_PERMISSION=101;
    FusedLocationProviderClient fusedLocationProviderClient;
    TextView location;

    CardView groceries;
    LinearLayout general_stores,restaurants,medicines,bakery,electrical_stores,hardware_stores,gifts_stores,clothes_stores,stationery_stores,pet_stores,sports_stores,
    view_all_stores;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initialization();
        onClickListeners();


        location=(TextView)findViewById(R.id.location_display);

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(HomeActivity.this);
        checkPinCode();



        ImageView imageView=(ImageView)findViewById(R.id.experiment);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                HomeActivity.this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(HomeActivity.this);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });



    }

    private void onClickListeners() {
        general_stores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(HomeActivity.this, StoresActivity.class);
                intent.putExtra("type","General Store");
                startActivity(intent);
            }
        });
        restaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(HomeActivity.this, StoresActivity.class);
                intent.putExtra("type","Restaurant");
                startActivity(intent);
            }
        });
        medicines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(HomeActivity.this, StoresActivity.class);
                intent.putExtra("type","Medical Stores");
                startActivity(intent);
            }
        });
        bakery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(HomeActivity.this, StoresActivity.class);
                intent.putExtra("type","Bakery");
                startActivity(intent);
            }
        });
        electrical_stores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(HomeActivity.this, StoresActivity.class);
                intent.putExtra("type","Electrical Stores");
                startActivity(intent);
            }
        });
        hardware_stores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(HomeActivity.this, StoresActivity.class);
                intent.putExtra("type","Hardware Store");
                startActivity(intent);
            }
        });
        gifts_stores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(HomeActivity.this, StoresActivity.class);
                intent.putExtra("type","Gift Store");
                startActivity(intent);
            }
        });
        clothes_stores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(HomeActivity.this, StoresActivity.class);
                intent.putExtra("type","Clothes Store");
                startActivity(intent);
            }
        });
        stationery_stores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(HomeActivity.this, StoresActivity.class);
                intent.putExtra("type","Stationery");
                startActivity(intent);
            }
        });
        pet_stores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(HomeActivity.this, StoresActivity.class);
                intent.putExtra("type","Pet Store");
                startActivity(intent);
            }
        });
        sports_stores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(HomeActivity.this, StoresActivity.class);
                intent.putExtra("type","Sports Store");
                startActivity(intent);
            }
        });
        view_all_stores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(HomeActivity.this, StoresActivity.class);
                intent.putExtra("type","View All");
                startActivity(intent);
            }
        });
    }

    private void initialization() {
        groceries=(CardView)findViewById(R.id.groceries_card_view);
        general_stores=(LinearLayout)findViewById(R.id.general_stores_linear_layout);
        restaurants=(LinearLayout)findViewById(R.id.restaurants_linear_layout);
        medicines=(LinearLayout)findViewById(R.id.medicines_linear_layout);
        bakery=(LinearLayout)findViewById(R.id.bakery_linear_layout);
        electrical_stores=(LinearLayout)findViewById(R.id.electrical_stores_linear_layout);
        hardware_stores=(LinearLayout)findViewById(R.id.hardware_stores_linear_layout);
        gifts_stores=(LinearLayout)findViewById(R.id.gifts_linear_layout);
        clothes_stores=(LinearLayout)findViewById(R.id.clothes_linear_layout);
        stationery_stores=(LinearLayout)findViewById(R.id.stationery_linear_layout);
        pet_stores=(LinearLayout)findViewById(R.id.pets_linear_layout);
        sports_stores=(LinearLayout)findViewById(R.id.sports_linear_layout);
        view_all_stores=(LinearLayout)findViewById(R.id.view_all_linear_layout);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void checkPinCode() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(
                    new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                mLastLocation = location;
                                new FetchCityTask(HomeActivity.this,
                                        HomeActivity.this).execute(location);
                            }

                        }
                    });}
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                // If the permission is granted, get the location,
                // otherwise, show a Toast
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPinCode();
                } else {
                    Toast.makeText(this,
                            "Permission Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    @Override
    public void onTaskCompleted(String result) {
        location.setText(result);
    }



    }





