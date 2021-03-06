package app.moviles.retomaps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment {

    private GoogleMap mMap;
    private LocationManager manager;

    private Marker myMarker;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {


        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            LatLng posini = new LatLng(3.4,-72);
            myMarker = mMap.addMarker(new MarkerOptions().position(posini));
            requestLocation();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
        manager = (LocationManager) (LocationManager)getActivity().getSystemService(view.getContext().LOCATION_SERVICE);
    }

    @SuppressLint("MissingPermission")
    public void requestLocation(){
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {

                        LatLng pos= new LatLng(location.getLatitude(), location.getLongitude());
                        myMarker.setPosition(pos);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 16));
                        //mMap.addMarker(new MarkerOptions().position(pos));
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(@NonNull String provider) {

                    }

                    @Override
                    public void onProviderDisabled(@NonNull String provider) {

                    }
                });
    }
}