package mx.gob.cdmx.app_territorial.db;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import mx.gob.cdmx.app_territorial.model.Alcaldia;

public class AlcaldiaRepository {
    private static final String TAG_LOG  = "AlcaldiaRepository";
    private final String TABLE_NAME = "alcaldia";
    private static String COLUMN_ID = "id";
    private static String COLUMN_NOMBRE = "nombre";

    private SQLiteDatabase m_appDB;

    public AlcaldiaRepository(SQLiteDatabase sqliteDB) {
        m_appDB = sqliteDB;
    }

    public List<Alcaldia> findAllAlcaldia(){
        List<Alcaldia> listAlcaldia = new ArrayList<>();

        try{
            Cursor cursor = m_appDB.rawQuery("SELECT * FROM " + TABLE_NAME , null);
            if (cursor.moveToFirst()){
                do {
                    listAlcaldia.add(
                            new Alcaldia(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                                    cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE))
                            )
                    );

                } while (cursor.moveToNext());

            }
        } catch (SQLException e){
            Log.e(TAG_LOG, e.getMessage());
        }
        return listAlcaldia;
    }
}
