package app.moviles.retomaps;

public class Place {

    private String id;
    private String nombre;
    private String direccion;
    private double lat;
    private double lng;

    public Place(String id, String nombre, String telefono, double lat, double lng) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = telefono;
        this.lat = lat;
        this.lng = lng;
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
}
