package mx.gob.cdmx.app_territorial.service;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

import mx.gob.cdmx.app_territorial.R;
import mx.gob.cdmx.app_territorial.db.DatabaseHandler;

public class gpsappwidget extends Activity {
/** Called when the activity is first created. */
	DatabaseHandler usdbh;
	private SQLiteDatabase db;
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);

usdbh = new DatabaseHandler(this);
db = usdbh.getReadableDatabase();

setContentView(R.layout.main);

File directory;
File file;
File sdCard;
sdCard = Environment.getExternalStorageDirectory();
FileOutputStream fout = null;
try {
	directory = new File(sdCard.getAbsolutePath() + "/Mis_archivos");
	directory.mkdirs();
} catch (Exception e) {
	String stackTrace = Log.getStackTraceString(e);
	Log.i("log_tag", "mkdir error" + stackTrace);
}

}
}
