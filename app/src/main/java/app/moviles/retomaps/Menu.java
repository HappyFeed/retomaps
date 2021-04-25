package app.moviles.retomaps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Menu extends AppCompatActivity {

    private MenuFragment menuFragment;
    private MapsFragment mapsFragment;
    private SearchFragment searchFragment;
    private BottomNavigationView navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        }, 11);

        navigator = findViewById(R.id.navigator);

        menuFragment = MenuFragment.newInstance();
        mapsFragment = new MapsFragment();
        searchFragment = SearchFragment.newInstance();

        showFragment(menuFragment);

        navigator.setOnNavigationItemSelectedListener(
                (menuItem) ->{
                    switch (menuItem.getItemId()){
                        case R.id.addLocation:
                            showFragment(menuFragment);
                            break;
                        case R.id.mapLocation:
                            showFragment(mapsFragment);
                            break;
                        case R.id.listLocation:
                            showFragment(searchFragment);
                            break;
                    }

                    return true;
                }
        );
    }

    public void showFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }
}