package app.moviles.retomaps;

import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.UUID;

public class SearchAdapter extends RecyclerView.Adapter<LocationView> {

    private ArrayList<Place> places;

    public SearchAdapter(){
        places = new ArrayList<Place>();

        places.add(new Place(UUID.randomUUID().toString(), "ICESI", "CAÑASGORDAS"));
    }

    public void addPlace(Place p){
        places.add(p);
    }

    @NonNull
    @Override
    public LocationView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.locationrow, null);
        ConstraintLayout rowroot = (ConstraintLayout) row;
        LocationView locationView = new LocationView(rowroot);
        return locationView;
    }

    @Override
    public void onBindViewHolder(@NonNull LocationView holder, int position) {
        holder.getNombre().setText(places.get(position).getNombre());
        holder.getDireccion().setText(places.get(position).getTelefono());
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

}
