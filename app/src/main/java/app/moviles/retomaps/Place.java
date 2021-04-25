package app.moviles.retomaps;

import android.media.Image;

public class Place {

    private String id;
    private String nombre;
    private String direccion;
    private double calificacion;
    private String imageUrl;
    private double lat;
    private double lng;



    public Place(String id, String nombre, String telefono, double lat, double lng, double calificacion, String imageUrl) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = telefono;
        this.lat = lat;
        this.lng = lng;
        this.calificacion = calificacion;
        this.imageUrl = imageUrl;
    }

    public Place() {
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return direccion;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String direccion) {
        this.direccion = direccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
