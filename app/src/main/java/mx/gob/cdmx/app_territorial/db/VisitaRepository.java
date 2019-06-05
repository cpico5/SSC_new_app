package mx.gob.cdmx.app_territorial.db;

import android.database.sqlite.SQLiteDatabase;

public class VisitaRepository {
    private SQLiteDatabase m_appDB;

    public VisitaRepository(SQLiteDatabase sqliteDB) {
        m_appDB = sqliteDB;
    }
}
