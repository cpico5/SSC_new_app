package mx.gob.cdmx.app_territorial.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings.Secure;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import mx.gob.cdmx.app_territorial.R;
import mx.gob.cdmx.app_territorial.db.DatabaseHandler;
import mx.gob.cdmx.app_territorial.model.Nombre;
import mx.gob.cdmx.app_territorial.service.Crash;
import mx.gob.cdmx.app_territorial.service.GPSTracker;


public class FotoEvidencia extends Activity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    final static String TAG = "FotoEvidencia";

    private int id_user;
    private String email;
    private String token;
    private String cuadrante;
    private String nombre;
    private String manzana;


    public static String getHostName(String defValue) {
        try {
            Method getString = Build.class.getDeclaredMethod("getString", String.class);
            getString.setAccessible(true);
            return getString.invoke(null, "net.hostname").toString();
        } catch (Exception ex) {
            return defValue;
        }
    }


    Calendar c = Calendar.getInstance();

    SimpleDateFormat df1 = new SimpleDateFormat("yyyMMdd");
    String formattedDateFecha = df1.format(c.getTime());

    SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
    String formattedDate2 = df2.format(c.getTime());

    SimpleDateFormat df3 = new SimpleDateFormat("yyyMMdd");
    String formattedDate3 = df3.format(c.getTime());

    SimpleDateFormat df4 = new SimpleDateFormat("HH:mm:ss a");
    String formattedDate4 = df4.format(c.getTime());

    SimpleDateFormat df5 = new SimpleDateFormat("HH:mm:ss");
    String formattedDateHora = df5.format(c.getTime());

    private ImageButton camara;
    private ImageView imagen;
    private ImageView imagen2;
    private ImageView imagen3;
    private TextView Texto;
    Geocoder geocoder;
    List<Address> addresses;
    //	private EditText nombreImagen;
    private String nombreImagen;
    private String nombreImagen2;
    private String nombreImagen3;
    private Button Guarda;
    private Uri output;
    private String foto;
    private String foto2;
    private String foto3;
    private File file;
    private File file2;
    private File file3;

    private String video;
    private String nombreVideo;

    private String la_foto;
    private String nombreF;
    private String nombreD;

    private File file4;
    private File file5;
    private File file6;
    List<String> list;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    private Spinner spinnerPUBLICO;
    String alcaldia, laCasilla, distF, distL;

    DatabaseHandler usdbh;
    private Spinner spinner;
    private Spinner spinner2;
    // UsuariosSQLiteHelper Udb;
    private SQLiteDatabase db;
    double latitude;
    double longitude;
    String laLatitud;
    String laLongitud;
    public String tiempo;
    String date = formattedDateFecha.toString();
    public String maximo = "";
    int elMaximo;
    String laSeccion;

    public EditText comentario;
    public EditText editNombreFoto;
    public EditText editLat, editLong, editDireccion;

    // NORMAL
    Nombre nom = new Nombre();
    String nombreE = nom.nombreEncuesta();

    private static final int READ_BLOCK_SIZE = 100000;

    public String tablet;

    public String sacaChip() {
        String Imei = "4354353";
        String deviceId = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);
        TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        } else {
            Imei = tManager.getDeviceId();
        }
    	return Imei;
    }

    public String cachaNombre(){
        Bundle datos=this.getIntent().getExtras();
        String Nombre=datos.getString("Nombre");
        return Nombre;
    }
    
  

      

    public String cachaUsuario(){
        Bundle datos=this.getIntent().getExtras();
        String usuario=datos.getString("usuario");
        return usuario;
    }
 
 
 
    ///////    METODO PARA VERIFICAR LA CONEXI�N A INTERNET
    public static boolean verificaConexion(Context ctx) {
        boolean bConectado = false;
        ConnectivityManager connec = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // No s�lo wifi, tambi�n GPRS
        NetworkInfo[] redes = connec.getAllNetworkInfo();
        // este bucle deber�a no ser tan �apa
        for (int i = 0; i < 2; i++) {
            // �Tenemos conexi�n? ponemos a true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                bConectado = true;
            }
        }
        return bConectado;
    }

    
    //EVENTO AL PULSAR EL BOTON ATRAS
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // Esto es lo que hace mi botÛn al pulsar ir a atr·s
            Intent intent = new Intent(FotoEvidencia.this, MapaActivity.class);
            intent.putExtra(MenuActivity.TOKEN, token);
            intent.putExtra(MenuActivity.USER_ID, id_user);
            intent.putExtra(MenuActivity.EMAIL, email);
            intent.putExtra(MenuActivity.CUADRANTE, cuadrante);
            intent.putExtra(MenuActivity.NOMBRE, nombre);

            //finish();
            startActivity(intent);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.foto_evidencia);

        Thread.setDefaultUncaughtExceptionHandler(new Crash(this));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Intent startingIntent = getIntent();
        if(startingIntent == null) {
            Log.e(TAG,"No Intent?  We're not supposed to be here...");
            finish();
            return;
        }
        /*
         * Paso de parametros entre Intent
         */
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            email = savedInstanceState.getString(MenuActivity.EMAIL);
            id_user = savedInstanceState.getInt(MenuActivity.USER_ID);
            token = savedInstanceState.getString(MenuActivity.TOKEN);
            cuadrante = savedInstanceState.getString(MenuActivity.CUADRANTE);
        } else {
            // Probably initialize members with default values for a new instance
            email = startingIntent.getStringExtra(MenuActivity.EMAIL);
            id_user = startingIntent.getIntExtra(MenuActivity.USER_ID,0);
            token = startingIntent.getStringExtra(MenuActivity.TOKEN);
            cuadrante = startingIntent.getStringExtra(MenuActivity.CUADRANTE);
            nombre = startingIntent.getStringExtra(MenuActivity.NOMBRE);
            manzana = startingIntent.getStringExtra(MenuActivity.MANZANA);
        }
        
        imagen=(ImageView)findViewById(R.id.imagen);
        editLat=(EditText)findViewById(R.id.editLat);
        editLong=(EditText)findViewById(R.id.editLong);
        editDireccion=(EditText)findViewById(R.id.editDireccion);
          
        Ubica();
     
    	camara=(ImageButton)findViewById(R.id.camara);

		camara.setOnClickListener(new OnClickListener(){
		  @Override
		  public void onClick(View v) {
		              getCamara();
		  }
		});	




        Guarda=(Button)findViewById(R.id.guardar);

        //PARA VER SI ESTA O NO EST� CONECTADO


//            Toast.makeText(getBaseContext(),"Sin Conexion...!", Toast.LENGTH_LONG).show();
            Guarda.setOnClickListener(new OnClickListener(){


                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    GPSTracker gps = new GPSTracker(FotoEvidencia.this);
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();

                   if(imagen.getDrawable() != null){
                        Log.i("datos f", "Solo hay foto");

                        	 Intent intent = new Intent(FotoEvidencia.this, MainActivityPantalla1.class);
                             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                             Bundle bundle = new Bundle();
                             intent.putExtras(bundle);
                           intent.putExtra(MenuActivity.TOKEN, token);
                           intent.putExtra(MenuActivity.USER_ID, id_user);
                           intent.putExtra(MenuActivity.EMAIL, email);
                           intent.putExtra(MenuActivity.CUADRANTE, cuadrante);
                           intent.putExtra(MenuActivity.NOMBRE, nombre);
                           intent.putExtra(MenuActivity.FOTO, foto);
                           intent.putExtra(MenuActivity.LATITUD, String.valueOf(latitude));
                           intent.putExtra(MenuActivity.LONGITUD, String.valueOf(longitude));
                           intent.putExtra(MenuActivity.MANZANA, manzana);

                       Log.d(TAG, "--------------> Nombre: " + nombre  + " , manzana: " + manzana);
                       finish();
                       startActivity(intent);

         
                        
                    }else {
                        Toast.makeText(FotoEvidencia.this, "Debe Tomar Una Foto", Toast.LENGTH_LONG).show();
                        Log.i("datos f", "No hay Nada");
                    }
                }
            });
            

        }
   
    
    private void getCamara(){
       File sdCard = Environment.getExternalStorageDirectory();
		 Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			/* Creamos un fichero donde guardaremos la foto	 */
		 nombreD = date+"_"+sacaChip();
		 foto = sdCard.getAbsolutePath() + "/Fotos/Fotos_SSC"+formattedDate3+"N/" +nombreD.trim()+".jpg";
		 Log.d(TAG, "Ruta de imagen -----------> " + foto);
	        file=new File(foto);
			Uri output = Uri.fromFile(file);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
			/* Lanzamos el intent y recogemos el resultado en onActivityResult	 */
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE); // 1
        }
    }
	 
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {

			//super.onActivityResult(requestCode, resultCode, data);
         if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
             ImageView uno = (ImageView) findViewById(R.id.imagen);
             uno.setImageBitmap(BitmapFactory.decodeFile(foto));

             File file = new File(foto);
             if (file.exists()) {
                 //nuevaTarea.execute(foto);
             } else {
                 Toast.makeText(getApplicationContext(), "No se ha realizado la foto", Toast.LENGTH_SHORT).show();
             }
         }

    }
	
	 public void Ubica(){
			
			String address=null;
			
			if (!verificaConexion(this)) {
		        Toast.makeText(getBaseContext(),"Sin Conexion\nUltima Ubicaci�n", Toast.LENGTH_LONG).show();
			}else{

                GPSTracker gps = new GPSTracker(FotoEvidencia.this);
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
						
				Log.i(null, "la latitud: "+ latitude);
				Log.i(null, "la longitud: "+ longitude);
			    
			    
				
				editLat.setText(String.valueOf(latitude));
				editLong.setText(String.valueOf(longitude));
				
				

				geocoder = new Geocoder(this, Locale.getDefault());

				try {
					addresses = geocoder.getFromLocation(latitude, longitude, 1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // Here 1 represent max location result to returned, by documents it recommended 1 to 5
				
				
				try {
					address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
				} catch (Exception e) {
					String stackTrace = Log.getStackTraceString(e);
		            Log.i("OnActivityResuly","Error Valores"+ stackTrace);
				}

				
				
				Log.i(null, "la direcci�n: "+ address);
				
				editDireccion.setText(address);
				String direccion=editDireccion.getText().toString().toUpperCase();
				
				usdbh = new DatabaseHandler(this);
				db = usdbh.getWritableDatabase();

				if (db != null) {
					db.execSQL("update ubicacion set latitud='"+latitude+"' , longitud='"+longitude+"', direccion= '"+direccion+"' where id=1");
				}
				
				
				
			}
			
		}
		
	 
	 


}



