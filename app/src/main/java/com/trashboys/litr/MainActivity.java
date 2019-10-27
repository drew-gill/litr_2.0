package com.trashboys.litr;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ActionBar toolbar;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_user:
                    toolbar.setTitle("User Info");
                    fragment = new UserInfoFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_camera:
                    toolbar.setTitle("Litter Cam");
                    fragment = new CameraFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_map:

                    toolbar.setTitle("Litter Map");
                    //setContentView(R.layout.activity_maps);
                    /*
                    loadFragment((Fragment) Mfragment);
                    return true;
                    */
                    loadFragment(mapFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        toolbar.setTitle("User Info");
        mapFragment = SupportMapFragment.newInstance();
        mapFragment.getMapAsync(this);
        loadFragment(new UserInfoFragment());
    }


    private void loadFragment(Fragment fragment) {
        // load fragment
        if (getSupportFragmentManager().findFragmentById(R.id.frame_container) instanceof CameraFragment && fragment instanceof CameraFragment ||
                getSupportFragmentManager().findFragmentById(R.id.frame_container) instanceof UserInfoFragment && fragment instanceof UserInfoFragment ||
                getSupportFragmentManager().findFragmentById(R.id.frame_container) instanceof SupportMapFragment && fragment instanceof SupportMapFragment) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
