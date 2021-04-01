package app.moviles.retomaps;

public class Place {

    private String id;
    private String nombre;
    private String direccion;

    public Place(String id, String nombre, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = telefono;
    }

    public Place() {
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
