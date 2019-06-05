package mx.gob.cdmx.app_territorial.view;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import mx.gob.cdmx.app_territorial.R;
import mx.gob.cdmx.app_territorial.db.DatabaseHandler;
import mx.gob.cdmx.app_territorial.service.AndroidLocationServices;

public class BienvenidaActivity extends Activity {

    private static final String TAG = "Bienvenida";


    double latitude;
    double longitude;
    static InputStream is2 = null;
    String result3;
    String algo;
    public String seccion, count;
    //    ProgressDialog dialog = null;
//    private TransparentProgressDialog pd;
    private Handler h;
    private Runnable r;
    DatabaseHandler usdbh;
    private SQLiteDatabase db;

    Boolean bandera=false;

    Calendar c = Calendar.getInstance();

    SimpleDateFormat df3 = new SimpleDateFormat("yyyMMdd");
    String formattedDate3 = df3.format(c.getTime());

    SimpleDateFormat df4 = new SimpleDateFormat("yyyMMdd");
    String formattedDateFecha = df3.format(c.getTime());

    SimpleDateFormat df5 = new SimpleDateFormat("HH:mm a");
    String formattedDate5 = df5.format(c.getTime());

    public static String getHostName(String defValue) {
        try {
            Method getString = Build.class.getDeclaredMethod("getString", String.class);
            getString.setAccessible(true);
            return getString.invoke(null, "net.hostname").toString();
        } catch (Exception ex) {
            return defValue;
        }
    }

    public String sacaChip() {
        String Imei = "";

        String deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        // Getting IMEI Number of Devide

        if (ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //return TODO;
        } else {

            Imei = tManager.getDeviceId();
        }
        return Imei;
    }


    protected void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show();

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions(BienvenidaActivity.this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE ,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET,Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.READ_PHONE_STATE,Manifest.permission.RECORD_AUDIO,Manifest.permission.LOCATION_HARDWARE,Manifest.permission.ACCESS_WIFI_STATE},
                    1);
        }
        setContentView(R.layout.bienvenida);

        usdbh = new DatabaseHandler(this);
        db = usdbh.getReadableDatabase();


        bandera=    pregunta(AndroidLocationServices.class);
        if(bandera){
//            Toast.makeText(this, "EL SERVICIO YA EST¡ ARRRIBA", Toast.LENGTH_LONG).show();
//
//            finish();
        }
        else {
            startService(new Intent(this, AndroidLocationServices.class));

//            finish();
        }


        if (!verificaConexion(this)) {  //SI NO ES

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {


                    Toast toast = Toast.makeText(getBaseContext(), "Verifica tu conexion a Internet", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    finish();
//                            pd.dismiss();
//                            dialog.dismiss();

                }
            }, 3000);

        } else {


            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    String dato=sacaMaximo();

                    if(dato.matches("0")){

                        Toast toast = Toast.makeText(getBaseContext(), "No hay datos de Ubicación", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        finish();

                    }else{

                        Intent intent = new Intent(BienvenidaActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        Bundle bundle = new Bundle();
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();

                    }



                }
            }, 3000);
        }

    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(this, "Permisos asignados", Toast.LENGTH_SHORT).show();

                    //Intent i = new Intent(this, MainActivity.class); //start activity
                    //startActivity(i);
                    //startService(new Intent(this, AndroidLocationServices.class));
/*                    bandera=    pregunta(AndroidLocationServices.class);
                    if(bandera){
                        Toast.makeText(this, "EL SERVICIO YA ESTÁ ARRRIBA", Toast.LENGTH_LONG).show();

                        finish();
                    }
                    else {
                        Intent intent = new Intent();
                        ComponentName comp = new ComponentName(this.getPackageName(),
                                AndroidLocationServices.class.getName());
                        //  startService(new Intent(this, AndroidLocationServices.class));
                        //  AndroidLocationServices.enqueueWork(this, (intent.setComponent(comp)));

                        ContextCompat.startForegroundService(this,intent.setComponent(comp));
                        finish();
                    }*/

                }

                else {
                    Toast.makeText(this, "Permisos rechazados", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    @Override
    protected void onDestroy() {
//		h.removeCallbacks(r);
//		if (pd.isShowing() ) {
//			pd.dismiss();
//		}
        super.onDestroy();
    }


    /////// METODO PARA VERIFICAR LA CONEXION A INTERNET
    public static boolean verificaConexion(Context ctx) {
        boolean bConectado = false;
        ConnectivityManager connec = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        // No sÔøΩlo wifi, tambiÔøΩn GPRS
        NetworkInfo[] redes = connec.getAllNetworkInfo();
        // este bucle deberÌa no ser tan COMPLICADO
        for (int i = 0; i < 2; i++) {
            // ÔøΩTenemos conexiÔøΩn? ponemos a true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                bConectado = true;
            }
        }
        return bConectado;
    }


    private boolean pregunta(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private String sacaMaximo() {
        String maximo=null;
        Set<String> set = new HashSet<String>();
        final String F = "File dbfile";
        usdbh = new DatabaseHandler(this);
        db = usdbh.getReadableDatabase();
        String selectQuery = "SELECT count(*) FROM ubicacion where fecha='" + formattedDateFecha + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                maximo = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return maximo;
    }
}
