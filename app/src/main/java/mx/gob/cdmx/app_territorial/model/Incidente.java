package mx.gob.cdmx.app_territorial.model;

/**
 * Clase de transferencia para el llenado del catálogo de incidentes
 * e2lira
 */
public class Incidente {
    private int id;
    private String nombre;
    private int proyectoId;
    private int version;

    /***
     *  Constructor con todos los parametros
     * @param id
     * @param proyectoId
     * @param nombre
     * @param version
     */
    public Incidente(int id,  int proyectoId, String nombre, int version) {
        this.id = id;
        this.nombre = nombre;
        this.proyectoId = proyectoId;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(int proyectoId) {
        this.proyectoId = proyectoId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
