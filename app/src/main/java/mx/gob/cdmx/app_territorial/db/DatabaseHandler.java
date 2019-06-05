package mx.gob.cdmx.app_territorial.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 3;
    private static final String DB_NAME = "dif_alimiento";
    private static final String TAG = "DatabaseHandler";

    private static final String TABLE_INCIDENTE = "incidente";
    private static final String TABLE_ALCALDIA = "alcaldia";
    private static final String TABLE_REPORTE_INCIDENTE = "reporte_incidente";


    public static class TablaDatos{
        public static String TABLA_DATOS = "datos";
        public static String COLUMNA_usuario= "usuario";
        public static String COLUMNA_latitud= "latitud";
        public static String COLUMNA_longitud= "longitud";
        public static String COLUMNA_direccion= "direccion";
        public static String COLUMNA_pregunta_1= "pregunta_1";
        public static String COLUMNA_pregunta_2= "pregunta_2";
        public static String COLUMNA_pregunta_3= "pregunta_3";
        public static String COLUMNA_pregunta_4= "pregunta_4";
        public static String COLUMNA_pregunta_5= "pregunta_5";
        public static String COLUMNA_pregunta_6= "pregunta_6";
        public static String COLUMNA_pregunta_7= "pregunta_7";
        public static String COLUMNA_pregunta_8= "pregunta_8";
        public static String COLUMNA_pregunta_9= "pregunta_9";
        public static String COLUMNA_pregunta_10= "pregunta_10";
        public static String COLUMNA_pregunta_11= "pregunta_11";
        public static String COLUMNA_pregunta_12= "pregunta_12";
        public static String COLUMNA_activo= "activo";


    }

    private static final String DATABASE_DATOS = "create table "
            + TablaDatos.TABLA_DATOS + "("
            + "id integer primary key autoincrement,"
            + TablaDatos.COLUMNA_usuario + " text , "
            + TablaDatos.COLUMNA_latitud + " text , "
            + TablaDatos.COLUMNA_longitud + " text , "
            + TablaDatos.COLUMNA_pregunta_1 + " text , "
            + TablaDatos.COLUMNA_pregunta_2 + " text , "
            + TablaDatos.COLUMNA_pregunta_3 + " text , "
            + TablaDatos.COLUMNA_pregunta_4 + " text , "
            + TablaDatos.COLUMNA_pregunta_5 + " text , "
            + TablaDatos.COLUMNA_pregunta_6 + " text , "
            + TablaDatos.COLUMNA_pregunta_7 + " text , "
            + TablaDatos.COLUMNA_pregunta_8 + " text , "
            + TablaDatos.COLUMNA_pregunta_9 + " text , "
            + TablaDatos.COLUMNA_pregunta_10 + " text , "
            + TablaDatos.COLUMNA_pregunta_11 + " text , "
            + TablaDatos.COLUMNA_pregunta_12 + " text , "
            + TablaDatos.COLUMNA_activo + " text ); ";

    public static class TablaUbicacion {
        public static String TABLA_UBICACION = "ubicacion";
        public static String COLUMNA_ID = "id";
        public static String COLUMNA_fecha = "fecha";
        public static String COLUMNA_hora = "hora";
        public static String COLUMNA_LATITUD = "latitud";
        public static String COLUMNA_LONGITUD = "longitud";
        public static String COLUMNA_DIRECCION = "direccion";
    }

    private static final String DATABASE_UBICACION = "create table "
            + TablaUbicacion.TABLA_UBICACION + "("
            + "id integer primary key autoincrement,"
            + TablaUbicacion.COLUMNA_fecha + " text, "
            + TablaUbicacion.COLUMNA_hora + " text, "
            + TablaUbicacion.COLUMNA_LATITUD + " text, "
            + TablaUbicacion.COLUMNA_LONGITUD + " text, "
            + TablaUbicacion.COLUMNA_DIRECCION + " text); ";



    /**
     * Constructor que utilizando el constructor de la clase SQLiteOpenHelper instancia una conexi&oacute;n a base de datos SQLite
     *
     * @param context Contexto de la aplicación
     */
    public DatabaseHandler(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DATABASE_DATOS);
        db.execSQL(DATABASE_UBICACION);

        db.execSQL("CREATE TABLE " + TABLE_INCIDENTE + "(" +
                " id integer primary key autoincrement," +
                " id_proyecto int," +
                " nombre varchar(50)," +
                " version int not null  default 0" +
                ")"
        );

        db.execSQL("CREATE TABLE " + TABLE_ALCALDIA + "(" +
                " id integer primary key autoincrement," +
                " nombre varchar(50)" +
                ")"
        );

        db.execSQL("CREATE TABLE " + TABLE_REPORTE_INCIDENTE + "(" +
                " id integer primary key autoincrement," +
                " id_alcaldia int," +
                " colonia varchar(50)," +
                " calle varchar(50)," +
                " latitud varchar(50)," +
                " logitud varchar(50)," +
                " foto varchar(50)," +
                " descipcion varchar(50)" +
                ")"
        );

        fillAlcaldia(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            // No añadir hasta que exista una tabla
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCIDENTE);


        } catch (SQLiteException e){
            Log.e(TAG,e.getMessage());
        }
        onCreate(db);
    }

    private void fillAlcaldia(SQLiteDatabase db){
        db.execSQL("insert into " + TABLE_ALCALDIA + " (id, nombre) values (2,'Azcapotzalco')");
        db.execSQL("insert into " + TABLE_ALCALDIA + " (id, nombre) values (3,'Coyoacán')");
        db.execSQL("insert into " + TABLE_ALCALDIA + " (id, nombre) values (4,'Cuajimalpa')");
        db.execSQL("insert into " + TABLE_ALCALDIA + " (id, nombre) values (5,'Gustavo A. Madero')");
        db.execSQL("insert into " + TABLE_ALCALDIA + " (id, nombre) values (6,'Iztacalco')");
        db.execSQL("insert into " + TABLE_ALCALDIA + " (id, nombre) values (7,'Iztapalapa')");
        db.execSQL("insert into " + TABLE_ALCALDIA + " (id, nombre) values (8,'Madalena Contreras')");
        db.execSQL("insert into " + TABLE_ALCALDIA + " (id, nombre) values (9,'Milpa Alta')");
        db.execSQL("insert into " + TABLE_ALCALDIA + " (id, nombre) values (10,'Albaro Obregón')");
        db.execSQL("insert into " + TABLE_ALCALDIA + " (id, nombre) values (11,'Tlahuac')");
        db.execSQL("insert into " + TABLE_ALCALDIA + " (id, nombre) values (12,'Tlapan')");
        db.execSQL("insert into " + TABLE_ALCALDIA + " (id, nombre) values (13,'Xochimilco')");
        db.execSQL("insert into " + TABLE_ALCALDIA + " (id, nombre) values (14,'Benito Juárez')");
        db.execSQL("insert into " + TABLE_ALCALDIA + " (id, nombre) values (15,'Cuauhtémoc')");
        db.execSQL("insert into " + TABLE_ALCALDIA + " (id, nombre) values (16,'Miguel Hidalgo')");
        db.execSQL("insert into " + TABLE_ALCALDIA + " (id, nombre) values (17,'Venustiano Carranza')");
    }



}
