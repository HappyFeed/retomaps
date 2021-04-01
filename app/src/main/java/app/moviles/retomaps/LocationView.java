package app.moviles.retomaps;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class LocationView extends RecyclerView.ViewHolder {

    private ConstraintLayout root;
    private TextView nombre;
    private TextView direccion;

    public LocationView(ConstraintLayout root) {

        super(root);
        this.root = root;
        nombre = root.findViewById(R.id.placeName);
        direccion = root.findViewById(R.id.placeAddress);
    }

    public ConstraintLayout getRoot() {
        return root;
    }

    public TextView getNombre() {
        return nombre;
    }

    public TextView getDireccion() {
        return direccion;
    }
}
