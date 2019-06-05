package mx.gob.cdmx.app_territorial.model;

public class ReporteIncidente {
    private int id;
    private int idAlcaldia;
    private String colonia;
    private String calle;
    private String latitud;
    private String longitud;
    private String foto;
    private String descripcion;


    public ReporteIncidente(int id,int idAlcaldia, String colonia, String calle, String latitud, String longitud, String foto, String descripcion) {
        this.id = id;
        this.idAlcaldia = idAlcaldia;
        this.colonia = colonia;
        this.calle = calle;
        this.latitud = latitud;
        this.longitud = longitud;
        this.foto = foto;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAlcaldia() {
        return idAlcaldia;
    }

    public void setIdAlcaldia(int idAlcaldia) {
        this.idAlcaldia = idAlcaldia;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
