package app.moviles.retomaps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MapsFragment extends Fragment {

    private GoogleMap mMap;
    private LocationManager manager;
    private FirebaseFirestore db;
    //private DatabaseReference rDatabase;

    private List<Place> places;
    private Marker myMarker;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {


        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            LatLng posini = new LatLng(3.4,-72);
            myMarker = mMap.addMarker(new MarkerOptions().position(posini).icon(BitmapDescriptorFactory.fromResource(R.drawable.outline_accessible_forward_black_24dp)));
            requestLocation();

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        places = new ArrayList<Place>();
       // rDatabase = FirebaseDatabase.getInstance().getReference();

        db.collection("places").limit(10).get().addOnSuccessListener(
                command -> {

                    for(DocumentSnapshot doc: command.getDocuments()){
                        Place p = doc.toObject(Place.class);
                        places.add(p);
                        createdMarkers(p.getLat() ,p.getLng());
                    }
                }
        );
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        places = new ArrayList<Place>();


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
                        for(int i = 0; i<places.size(); i++){
                            proximidad(location.getLatitude(), location.getLongitude(), places.get(i).getLat(),places.get(i).getLng());
                        }
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

    public void createdMarkers(double latitud, double longitud){
            LatLng posini = new LatLng(latitud,longitud);
            mMap.addMarker(new MarkerOptions().position(posini).icon(BitmapDescriptorFactory.fromResource(R.drawable.outline_coronavirus_black_24dp)));
    }

    public void proximidad(double lat1, double lng1, double lat2, double lng2 ){
        double distance = distFrom(lat1, lng1, lat2, lng2);
        //Toast.makeText(getContext(), "el tamaño es " + distance, Toast.LENGTH_SHORT).show();
        if(distance <=100){
            calificarPlace(lat2, lng2);
        }
    }

    public double distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = (double) (earthRadius * c);

        return dist;
    }

    public void calificarPlace(double lat, double lng){
        final CharSequence[] opciones = {"1","2","3","4" ,"5"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Califica");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(opciones[i].equals("1")){
                    Place p = searchPlace(lat, lng);
                    p.setCalificacion(p.getCalificacion()+1);
                    db.collection("places").document(p.getId()).set(p);
                }else if(opciones[i].equals("2")){
                    Place p = searchPlace(lat, lng);
                    p.setCalificacion(p.getCalificacion()+2);
                    db.collection("places").document(p.getId()).set(p);
                }else if(opciones[i].equals("3")){
                    Place p = searchPlace(lat, lng);
                    p.setCalificacion(p.getCalificacion()+3);
                    db.collection("places").document(p.getId()).set(p);
                }else if(opciones[i].equals("4")){
                    Place p = searchPlace(lat, lng);
                    p.setCalificacion(p.getCalificacion()+4);
                    db.collection("places").document(p.getId()).set(p);
                }else if(opciones[i].equals("5")){
                    Place p = searchPlace(lat, lng);
                    p.setCalificacion(p.getCalificacion()+5);
                    db.collection("places").document(p.getId()).set(p);
                }
            }
        });
        builder.show();
    }


    public Place searchPlace(double lat, double lng){
        for (int i = 0; i< places.size(); i++){
            if(lat == places.get(i).getLat() && lng == places.get(i).getLng() ){
                return places.get(i);
            }
        }
        return null;
    }
}
