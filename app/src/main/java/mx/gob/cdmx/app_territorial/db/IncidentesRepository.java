package mx.gob.cdmx.app_territorial.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import mx.gob.cdmx.app_territorial.model.Incidente;

public class IncidentesRepository {
    private static final String TAG_LOG  = "IncidenciaRepository";
    private static String TABLE_NAME = "incidencia";
    private static String COLUMN_ID = "id";
    private static String COLUMN_NOMBRE = "nombre";
    private static String COLUMN_PROYECTO_ID = "id_proyecto";
    private static String COLUMN_VERSION = "version";

    private SQLiteDatabase m_appDB;

    public IncidentesRepository(SQLiteDatabase sqliteDB) {
        m_appDB = sqliteDB;
    }

    public void insertIncidente(List<Incidente> incidentes){

        for(Incidente incidente: incidentes){
            insertIcidente(incidente);
        }

    }

    public void insertIcidente(Incidente  incidente){
        insertIncidente(incidente.getId(),incidente.getProyectoId(), incidente.getNombre(), incidente.getVersion());
    }

    public void insertIncidente(int id, int idProyecto, String nombre, int version){
        ContentValues val= new ContentValues();
        val.put(COLUMN_ID, id);
        val.put(COLUMN_PROYECTO_ID, idProyecto);
        val.put(COLUMN_NOMBRE, nombre);
        val.put(COLUMN_VERSION, version);

        m_appDB.insert(TABLE_NAME, null, val);
    }

    public List<Incidente> findAll(){
        List<Incidente> listInicidente = new ArrayList<>();

        try{
            Cursor cursor = m_appDB.rawQuery("SELECT * FROM " + TABLE_NAME , null);
            if (cursor.moveToFirst()){
                do {
                    listInicidente.add(
                            new Incidente(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                                                      cursor.getInt(cursor.getColumnIndex(COLUMN_PROYECTO_ID)),
                                                      cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE)),
                                                      cursor.getInt(cursor.getColumnIndex(COLUMN_VERSION))
                                                    )
                                      );

                } while (cursor.moveToNext());

            }
        } catch (SQLException e){
            Log.e(TAG_LOG, e.getMessage());
        }
        return listInicidente;
    }


}
