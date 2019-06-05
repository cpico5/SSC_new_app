package mx.gob.cdmx.app_territorial.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import mx.gob.cdmx.app_territorial.model.ReporteIncidente;

public class ReporteIncidenteRepository {
    private static final String TAG_LOG  = "AlcaldiaRepository";
    private final String TABLE_NAME = "reporte_incidente";
    private static String COLUMN_ID = "id";
    private static String COLUMN_ID_ALCALDIA = "id_alcaldia";
    private static String COLUMN_COLONIA = "colonia";
    private static String COLUMN_CALLE = "calle";
    private static String COLUMN_LATITUD = "latitud";
    private static String COLUMN_LONGITUD = "longitud";
    private static String COLUMN_FOTO = "foto";
    private static String COLUMN_DESCRIPCION = "descripcion";

    private SQLiteDatabase m_appDB;

    public ReporteIncidenteRepository(SQLiteDatabase m_appDB) {
        this.m_appDB = m_appDB;
    }

    public void insertReporteIncidente(ReporteIncidente ri){
        this.insertReporteIncidente(ri.getId(),ri.getIdAlcaldia(), ri.getColonia(), ri.getCalle(), ri.getLatitud(), ri.getLongitud(), ri.getFoto(), ri.getDescripcion());
    }

    public void insertReporteIncidente(int id, int idAlcaldia, String colonia, String calle, String latitud, String longitud, String foto, String descripcion){
        ContentValues val= new ContentValues();
        val.put(COLUMN_ID, id);
        val.put(COLUMN_ID_ALCALDIA, idAlcaldia);
        val.put(COLUMN_COLONIA, colonia );
        val.put(COLUMN_CALLE, calle);
        val.put(COLUMN_LATITUD, latitud);
        val.put(COLUMN_LONGITUD, longitud);
        val.put(COLUMN_FOTO, foto);
        val.put(COLUMN_DESCRIPCION, descripcion);

        m_appDB.insert(TABLE_NAME, null, val);
    }

}
