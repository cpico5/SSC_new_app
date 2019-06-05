package mx.gob.cdmx.app_territorial.view;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.Settings.Secure;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Timer;

import cz.msebera.android.httpclient.Header;
import mx.gob.cdmx.app_territorial.R;
import mx.gob.cdmx.app_territorial.db.DatabaseHandler;
import mx.gob.cdmx.app_territorial.model.Nombre;
import mx.gob.cdmx.app_territorial.service.Crash;

public class MainActivityPantalla1 extends AppCompatActivity {


	final Context context = this;
	final static String TAG = "MainActivityPantalla1";

	private int id_user;
	private String email;
	private String token;
	private String cuadrante;
	private String nombre;
	private String manzana;

	public StringBuilder builder0;

	private Button btnGuardar;
	private Button btnAbandono;
	private Button btnRechazo;
	private Button btnAbrir;
	private Button btnSalir;
	public Button uploadButton, emailButton;

	private ImageButton camara;
	private ImageButton camara2;
	private ImageView imagen;
	private ImageView imagen2;
	private ImageView imagen3;
	private TextView Texto;
	Uri output;
	String foto;
	private File file;
	String foto2;
	private File file2;

	ProgressDialog progress;

	Random random = new java.util.Random();
	public int rand;
	public EditText editFin;
	public EditText editMotivo5, editMotivo8, editMotivo10, editMotivo12;
	public EditText editNombreCiudadano, editTelefonoCiudadano;

	public RadioGroup rdPregunta1, rdPregunta2, rdPregunta3, rdPregunta4, rdPregunta5, rdPregunta6, rdPregunta7,
			rdPregunta8, rdPregunta9, rdPregunta10, rdPregunta11, rdPregunta12;


	private static final int READ_BLOCK_SIZE = 100000;

	Nombre nom = new Nombre();
	String nombreEncuesta = nom.nombreEncuesta();

	DatabaseHandler usdbh;
	DatabaseHandler Udb;
	List<String> list;
	ArrayAdapter<String> adapter;
	ArrayAdapter<String> adapter2;
	private SQLiteDatabase db;


	// EditText editPrograma2;

	public EditText cumple;

	TextView textCual2;

	private Spinner spinnerDelegaciones;
	private Spinner spinnerAno,spinnerTipoVisita;

	Timer timer;

	public String op1 = "";
	public String op2 = "";
	public String op3 = "";
	public String op4 = "";
	public String op5 = "";
	public String op6 = "";
	public String op7 = "";
	public String op8 = "";
	public String op9 = "";
	public String op10 = "";
	public String op11 = "";
	public String op12 = "";

	public String opCamaras = "";
	public String opPolicia = "";
	public String opDrogas = "";
	public String opRobos = "";
	public String opConsumo = "";

	public String opLuminaria = "";
	public String opBacheo = "";
	public String opDesasolve = "";
	public String opVehiculos = "";
	public String opAgua = "";
	public String opBanquetas = "";
	public String opLimpieza = "";

	public Resources res;

	public TextView difusion;

	LinearLayout layPreg2, layPreg3a, layPreg5, layPreg6a, layPreg8, layPreg10, layPreg12;
	LinearLayout layPreg3, layPreg4, layPreg6, layPreg7, layPreg9, layPreg11;

	public CheckBox chkCamaras, chkPolicia, chkDrogas, chkRobos, chkConsumo;
	public CheckBox chkLuminaria, chkBacheo, chkDesasolve, chkVehiculos, chkAgua, chkBanquetas, chkLimpieza;

	public String str;
	public String variablePrueba;
	public String encuestador;
	public String tablet;
	public String hora;

	public String quien;

	String upLoadServerUri = null;
	ProgressDialog dialog = null;
	final String path = "/mnt/sdcard/Mis_archivos/";

	int serverResponseCode = 0;

	public String tiempo;


	Calendar c = Calendar.getInstance();

	int year = c.get(Calendar.YEAR);
	int month = c.get(Calendar.MONTH);
	int day = c.get(Calendar.DAY_OF_MONTH);

	String latitud, longitud;

	SimpleDateFormat df1 = new SimpleDateFormat("yyy-MM-dd");
	String formattedDate1 = df1.format(c.getTime());

	SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
	String formattedDate2 = df2.format(c.getTime());

	SimpleDateFormat df3 = new SimpleDateFormat("yyyMMdd");
	String formattedDate3 = df3.format(c.getTime());

	SimpleDateFormat df6 = new SimpleDateFormat("MM");
	String formattedDate6 = df6.format(c.getTime());

	SimpleDateFormat df7 = new SimpleDateFormat("dd");
	String formattedDate7 = df7.format(c.getTime());

	SimpleDateFormat df4 = new SimpleDateFormat("HH:mm:ss a");
	String formattedDate4 = df4.format(c.getTime());

	SimpleDateFormat df5 = new SimpleDateFormat("HH:mm:ss");
	String formattedDate5 = df5.format(c.getTime());

	SimpleDateFormat df8 = new SimpleDateFormat("yyyMMdd");
	String formattedDateFecha = df1.format(c.getTime());

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


	public String cachaLatitud() {
		Bundle datos = this.getIntent().getExtras();
		String latitud = datos.getString("latitud");
		return latitud;
	}

	public String cachaLongitud() {
		Bundle datos = this.getIntent().getExtras();
		String longitud = datos.getString("longitud");
		return longitud;
	}

	public String cachaUsuario() {
		Bundle datos = this.getIntent().getExtras();
		String usuario = datos.getString("usuario");
		return usuario;
	}



	public String hora() {

		if (formattedDate5.matches("")) {
			formattedDate5 = df5.format(c.getTime());
		}
		return formattedDate5;
	}

	// EVENTO AL PULSAR EL BOTON ATRAS
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// Esto es lo que hace mi botÛn al pulsar ir a atr·s
			Intent intent = new Intent(MainActivityPantalla1.this, FotoEvidencia.class);
			intent.putExtra(MenuActivity.TOKEN, token);
			intent.putExtra(MenuActivity.USER_ID, id_user);
			intent.putExtra(MenuActivity.EMAIL, email);
			intent.putExtra(MenuActivity.CUADRANTE, cuadrante);
			intent.putExtra(MenuActivity.MANZANA, manzana);

			//finish();
			startActivity(intent);

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pantalla1);
		// Crea Log cuando falla la app
		Thread.setDefaultUncaughtExceptionHandler(new Crash(this));

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
			nombre = savedInstanceState.getString(MenuActivity.NOMBRE);
			foto = savedInstanceState.getString(MenuActivity.FOTO);
			latitud = savedInstanceState.getString(MenuActivity.LATITUD);
			longitud = savedInstanceState.getString(MenuActivity.LONGITUD);
			manzana = savedInstanceState.getString(MenuActivity.MANZANA);

		} else {
			// Probably initialize members with default values for a new instance
			email = startingIntent.getStringExtra(MenuActivity.EMAIL);
			id_user = startingIntent.getIntExtra(MenuActivity.USER_ID,0);
			token = startingIntent.getStringExtra(MenuActivity.TOKEN);
			cuadrante = startingIntent.getStringExtra(MenuActivity.CUADRANTE);
			nombre = startingIntent.getStringExtra(MenuActivity.NOMBRE);
			foto = startingIntent.getStringExtra(MenuActivity.FOTO);
			latitud = startingIntent.getStringExtra(MenuActivity.LATITUD);
			longitud = startingIntent.getStringExtra(MenuActivity.LONGITUD);
			manzana = startingIntent.getStringExtra(MenuActivity.MANZANA);

		}
		Log.d(TAG, "--------------> Nombre: " + nombre + " , manzana: " + manzana);
		res = getResources();


		btnGuardar = (Button) findViewById(R.id.btnGuardar);

		rdPregunta1 = (RadioGroup) findViewById(R.id.rdPregunta1);
		rdPregunta2 = (RadioGroup) findViewById(R.id.rdPregunta2);
		rdPregunta3 = (RadioGroup) findViewById(R.id.rdPregunta3);
		rdPregunta4 = (RadioGroup) findViewById(R.id.rdPregunta4);
		rdPregunta5 = (RadioGroup) findViewById(R.id.rdPregunta5);
		rdPregunta6 = (RadioGroup) findViewById(R.id.rdPregunta6);
		rdPregunta7 = (RadioGroup) findViewById(R.id.rdPregunta7);
		rdPregunta9 = (RadioGroup) findViewById(R.id.rdPregunta9);

		editNombreCiudadano = (EditText) findViewById(R.id.editNombreCiudadano);
		editTelefonoCiudadano = (EditText) findViewById(R.id.editTelefonoCiudadano);
		editMotivo12 = (EditText) findViewById(R.id.editMotivo12);

		layPreg2 = (LinearLayout) findViewById(R.id.layPreg2);
		layPreg3 = (LinearLayout) findViewById(R.id.layPreg3);
		layPreg3a = (LinearLayout) findViewById(R.id.layPreg3a);
		layPreg4 = (LinearLayout) findViewById(R.id.layPreg4);
		layPreg5 = (LinearLayout) findViewById(R.id.layPreg5);
		layPreg6 = (LinearLayout) findViewById(R.id.layPreg6);
		layPreg6a = (LinearLayout) findViewById(R.id.layPreg6a);
		layPreg7 = (LinearLayout) findViewById(R.id.layPreg7);
		layPreg8 = (LinearLayout) findViewById(R.id.layPreg8);
		layPreg9 = (LinearLayout) findViewById(R.id.layPreg9);
		layPreg10 = (LinearLayout) findViewById(R.id.layPreg10);
		layPreg11 = (LinearLayout) findViewById(R.id.layPreg11);
		layPreg12 = (LinearLayout) findViewById(R.id.layPreg12);

		chkCamaras = (CheckBox) findViewById(R.id.chkCamaras);
		chkPolicia = (CheckBox) findViewById(R.id.chkPolicia);
		chkDrogas = (CheckBox) findViewById(R.id.chkDroga);
		chkRobos = (CheckBox) findViewById(R.id.chkRobos);
		chkConsumo = (CheckBox) findViewById(R.id.chkConsumo);

		chkLuminaria = (CheckBox) findViewById(R.id.chkLuminaria);
		chkBacheo = (CheckBox) findViewById(R.id.chkBacheo);
		chkDesasolve = (CheckBox) findViewById(R.id.chkDesasolve);
		chkVehiculos = (CheckBox) findViewById(R.id.chkVehiculos);
		chkAgua = (CheckBox) findViewById(R.id.chkAgua);
		chkBanquetas = (CheckBox) findViewById(R.id.chkBanquetas);
		chkLimpieza = (CheckBox) findViewById(R.id.chkLimpieza);

		layPreg2.setVisibility(View.GONE);
		layPreg3.setVisibility(View.GONE);
		layPreg4.setVisibility(View.GONE);
		layPreg5.setVisibility(View.GONE);
		layPreg6.setVisibility(View.GONE);
		layPreg7.setVisibility(View.GONE);
		layPreg8.setVisibility(View.GONE);
//		layPreg9.setVisibility(View.GONE);
		layPreg10.setVisibility(View.GONE);
		layPreg11.setVisibility(View.GONE);

        spinnerTipoVisita= (Spinner) findViewById(R.id.spinneTipoVisitar);


		chkCamaras.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//is chkIos checked?
				if (((CheckBox) v).isChecked()) {
					opCamaras="C·maras de vigilancia";
				}else{
					opCamaras="";
				}

			}
		});

		chkPolicia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//is chkIos checked?
				if (((CheckBox) v).isChecked()) {
					opPolicia="Presencia policial";
				}else{
					opPolicia="";
				}

			}
		});

		chkDrogas.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//is chkIos checked?
				if (((CheckBox) v).isChecked()) {
					opDrogas="Combatir la venta la venta de droga";
				}else{
					opDrogas="";
				}

			}
		});

		chkRobos.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//is chkIos checked?
				if (((CheckBox) v).isChecked()) {
					opRobos="Combatir los robos";
				}else{
					opRobos="";
				}

			}
		});

		chkConsumo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//is chkIos checked?
				if (((CheckBox) v).isChecked()) {
					opConsumo="Combatir el consumo de sustancias embriagantes y tÛxicas en vÌa p˙blica";
				}else{
					opConsumo="";
				}

			}
		});


		chkLuminaria.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//is chkIos checked?
				if (((CheckBox) v).isChecked()) {
					opLuminaria="Luminaria";
				}else{
					opLuminaria="";
				}

			}
		});

		chkBacheo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//is chkIos checked?
				if (((CheckBox) v).isChecked()) {
					opBacheo="Bacheo";
				}else{
					opBacheo="";
				}

			}
		});

		chkDesasolve.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//is chkIos checked?
				if (((CheckBox) v).isChecked()) {
					opDesasolve="Desasolve";
				}else{
					opDesasolve="";
				}

			}
		});

		chkVehiculos.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//is chkIos checked?
				if (((CheckBox) v).isChecked()) {
					opVehiculos="Retiro de VehÌculos abandonados";
				}else{
					opVehiculos="";
				}

			}
		});

		chkAgua.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//is chkIos checked?
				if (((CheckBox) v).isChecked()) {
					opAgua="Subministro de agua";
				}else{
					opAgua="";
				}

			}
		});

		chkBanquetas.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//is chkIos checked?
				if (((CheckBox) v).isChecked()) {
					opBanquetas="ReparaciÛn de banquetas";
				}else{
					opBanquetas="";
				}

			}
		});

		chkLimpieza.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//is chkIos checked?
				if (((CheckBox) v).isChecked()) {
					opLimpieza="Servicio de limpieza";
				}else{
					opLimpieza="";
				}

			}
		});








// *********** preg1

		rdPregunta1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				if (checkedId == R.id.radio1) {
					op1 = "Si";
					layPreg2.setVisibility(View.GONE);
					rdPregunta2.clearCheck();
					op2 = "No aplica";
					layPreg3.setVisibility(View.VISIBLE);
					layPreg4.setVisibility(View.VISIBLE);
					layPreg5.setVisibility(View.VISIBLE);
					layPreg6.setVisibility(View.VISIBLE);
					layPreg7.setVisibility(View.VISIBLE);
					layPreg8.setVisibility(View.VISIBLE);
					layPreg10.setVisibility(View.GONE);
					layPreg11.setVisibility(View.GONE);
				} else if (checkedId == R.id.radio2) {
					op1 = "No";
					layPreg2.setVisibility(View.VISIBLE);
					layPreg3.setVisibility(View.GONE);
					layPreg4.setVisibility(View.GONE);
					layPreg5.setVisibility(View.GONE);
					layPreg6.setVisibility(View.GONE);
					layPreg7.setVisibility(View.GONE);
					layPreg8.setVisibility(View.GONE);
					layPreg10.setVisibility(View.GONE);
					layPreg11.setVisibility(View.GONE);
				}
			}
		});


// *********** preg2

		rdPregunta2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				if (checkedId == R.id.radio1) {
					op2 = "No hay nadie";
				} else if (checkedId == R.id.radio2) {
					op2 = "No hay mayores de edad";
				} else if (checkedId == R.id.radio3) {
					op2 = "No me interesa";
				} else if (checkedId == R.id.radio4) {
					op2 = "No tiene tiempo";
				}
			}
		});


// *********** preg3

		rdPregunta3.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				if (checkedId == R.id.radio1) {
					op3 = "Si";
				} else if (checkedId == R.id.radio2) {
					op3 = "No";
				}
			}
		});

// *********** preg4

		rdPregunta4.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				if (checkedId == R.id.radio1) {
					op4 = "Si";
				} else if (checkedId == R.id.radio2) {
					op4 = "No";
				}
			}
		});

// *********** preg5

		rdPregunta5.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				if (checkedId == R.id.radio1) {
					op5 = "Si";
				} else if (checkedId == R.id.radio2) {
					op5 = "No";
				}
			}
		});


// *********** preg6

		rdPregunta6.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				if (checkedId == R.id.radio1) {
					op6 = "Si";
				} else if (checkedId == R.id.radio2) {
					op6 = "No";
				}
			}
		});


// *********** preg7

		rdPregunta7.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				if (checkedId == R.id.radio1) {
					op7 = "Si";
				} else if (checkedId == R.id.radio2) {
					op7 = "No";
				}
			}
		});




	}

	////// FIN ONCREATE/////////////////////////////

	@Override
	protected void onPause() {
		super.onPause();

	}

	///////////////////////////////////////////////////////////////////////////////////////////
	public void onClick(View arg0) {

		// TODO Auto-generated method stub
		File sdCard, directory, file = null;

		try {
			// validamos si se encuentra montada nuestra memoria externa
			if (Environment.getExternalStorageState().equals("mounted")) {

				// Obtenemos el directorio de la memoria externa
				sdCard = Environment.getExternalStorageDirectory();

				if (arg0.equals(btnGuardar)) {

				}

				if (arg0.equals(btnAbrir)) {

				}


			} else {
				Toast.makeText(getBaseContext(), "El almacenamineto externo no se encuentra disponible",
						Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			// TODO: handle exception

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void valores() {

		String str = "";

		String seg = formattedDate5.substring(7);
		System.out.println("El segundo: " + seg);

		String mes = formattedDate6.toString();
		System.out.println("El mes" + mes);

		String dia = formattedDate7.toString();
		System.out.println("El dia" + dia);

		sacaChip();


		String strId = String.valueOf(rand + 1);



		String strText8 = editNombreCiudadano.getText().toString().trim();
		String strText9 = editTelefonoCiudadano.getText().toString().trim();
		String strText10 = "|"+opCamaras+"|"+opPolicia+"|"+opDrogas+"|"+opRobos+"|"+opConsumo;
		String strText11 = "|"+opLuminaria+"|"+opBacheo+"|"+opDesasolve+"|"+opVehiculos+"|"+opAgua+"|"+opBanquetas+"|"+opLimpieza;
		//String strText12 = editMotivo12.getText().toString().trim();
        String strText12 = spinnerTipoVisita.getSelectedItem().toString();

		String strUsuario = cachaUsuario();
		String strLat = cachaLatitud();
		String strLong = cachaLongitud();
		String str1 = op1;
		String str2 = op2;
		String str3 = op3;
		String str4 = op4;
		String str5 = op5;
		String str6 = op6;
		String str7 = op7;
		String str8 = strText8;
		String str9 = strText9;
		String str10 = strText10;
		String str11 = strText11;
		String str12 = strText12;
		String strActivo = "1";


		try {
			// INSERTA EN LA BASE DE DATOS //

			final String F = "File dbfile";

			// Abrimos la base de datos 'DBUsuarios' en modo escritura

			// NORMAL
			Nombre nom = new Nombre();
			String nombreE = nom.nombreEncuesta();


			usdbh = new DatabaseHandler(this);
			db = usdbh.getWritableDatabase();

			if (db != null) {
				ContentValues values = new ContentValues();

				values.put("usuario",strUsuario);
				values.put("latitud",latitud);
				values.put("longitud",longitud);
				values.put("pregunta_1",str1);
				values.put("pregunta_2",str2);
				values.put("pregunta_3",str3);
				values.put("pregunta_4",str4);
				values.put("pregunta_5",str5);
				values.put("pregunta_6",str6);
				values.put("pregunta_7",str7);
				values.put("pregunta_8",str8);
				values.put("pregunta_9",str9);
				values.put("pregunta_10",str10);
				values.put("pregunta_11",str11);
				values.put("pregunta_12",str12);
				values.put("activo",strActivo);
				db.insert("datos", null, values);
			}

			db.close();




			System.out.println(" pregunta_1   " + str1);
			System.out.println(" pregunta_2   " + str2);
			System.out.println(" pregunta_3   " + str3);
			System.out.println(" pregunta_4   " + str4);
			System.out.println(" pregunta_5   " + str5);
			System.out.println(" pregunta_6   " + str6);
			System.out.println(" pregunta_7   " + str7);
			System.out.println(" pregunta_8   " + str8);
			System.out.println(" pregunta_9   " + str9);
			System.out.println(" pregunta_10   " + str10);
			System.out.println(" pregunta_11   " + str11);
            System.out.println(" pregunta_12   " + str12);

			// FIN INSERTA BASE DE DATOS //

			JSONObject jsonObj = new JSONObject();
			jsonObj.put(" pregunta_1   " , str1);
			jsonObj.put(" pregunta_2   " , str2);
			jsonObj.put(" pregunta_3   " , str3);
			jsonObj.put(" pregunta_4   " , str4);
			jsonObj.put(" pregunta_5   " , str5);
			jsonObj.put(" pregunta_6   " , str6);
			jsonObj.put(" pregunta_7   " , str7);
			jsonObj.put(" pregunta_8   " , str8);
			jsonObj.put(" pregunta_9   " , str9);
			jsonObj.put(" pregunta_10   " , str10);
			jsonObj.put(" pregunta_11   " , str11);
            jsonObj.put(" pregunta_12   " , str12);

			sendData(jsonObj.toString(),latitud, longitud);

		} catch (Exception e) {
			String stackTrace = Log.getStackTraceString(e);
			Log.i("UPdate Encuestas", "Error todo OnCreate" + stackTrace);
		}

	}

	public void guardar(View v) {
		valores();
	}

	@Override
	public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
		super.onSaveInstanceState(outState, outPersistentState);

		outState.putSerializable(MenuActivity.EMAIL, email);
		outState.putSerializable(MenuActivity.USER_ID, id_user);
		outState.putSerializable(MenuActivity.TOKEN, token);
		outState.putSerializable(MenuActivity.CUADRANTE, cuadrante);
		outState.putSerializable(MenuActivity.NOMBRE, nombre);
		outState.putSerializable(MenuActivity.FOTO, foto);
		outState.putSerializable(MenuActivity.LATITUD, latitud);
		outState.putSerializable(MenuActivity.LONGITUD, longitud);
		outState.putSerializable(MenuActivity.MANZANA, manzana);

	}

	private void sendData(String data, String lat, String lng){

		progress = ProgressDialog.show(this, getResources().getString(R.string.title_dialog), "Cargando", true);

		progress.setCancelable(false);
		if (lat == null ){
			lat = "0";
		}
		if (lng == null){
			lng = "0";
		}
		if ((manzana == null) || (manzana.equals(""))){
			manzana = "0";
		}
		Log.d(TAG, "-------------> Manzana : " + manzana + ", datos: "  + data);
		RequestParams params = new RequestParams();
		params.put("latitude", lat);
		params.put("longitude", lng);
		params.put("square", manzana);
		params.put("questions", data);
		file = new File(foto);
		try{
			params.put("photo", file);
		}catch(FileNotFoundException e){
			Log.e(TAG, e.getMessage());
		}

		AsyncHttpClient client = new AsyncHttpClient();
		// JWT security
		Log.d(TAG, "Token JWT: " + token);
		client.addHeader("Authorization", "Bearer " + token);

		RequestHandle requestHandle = client.post(getResources().getString(R.string.url_aplicacion) + "/api/ssc_visita/setVisit", params, new AsyncHttpResponseHandler() {
			String jsonToken = "";
			int id_user;
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				String nombreStr = "";
				Log.d(TAG, "e2lira -----------> Realizo la conexión para subir los datos");

				Log.d(TAG, "e2lira -----------> " + new String(responseBody));
				String json = new String(responseBody);
				try {

					JSONObject jsonObject = new JSONObject(json);
					Log.d(TAG, "e2lira -----------> Data: " + jsonObject.get("data"));

				} catch (JSONException e){
					Log.e(TAG, e.getMessage());
				}

				progress.dismiss();
				showAlertDialog(getResources().getString(R.string.title_dialog), "Se envió con éxito la información al sistema central");

/*				AlertDialog.Builder bd = new AlertDialog.Builder(MainActivityPantalla1.this);
				AlertDialog ad = bd.create();
				ad.setTitle(getString(R.string.app_name));
				ad.setMessage("Se envió con éxito la información");
				ad.show();

				Intent intent = new Intent(MainActivityPantalla1.this, MenuActivity.class);
				intent.putExtra(MenuActivity.TOKEN, token);
				intent.putExtra(MenuActivity.USER_ID, id_user);
				intent.putExtra(MenuActivity.EMAIL, email);
				intent.putExtra(MenuActivity.CUADRANTE, cuadrante);
				intent.putExtra(MenuActivity.NOMBRE, nombre);
				//finish();
				startActivity(intent);*/

			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				progress.dismiss();
				if (statusCode != 200) {
					Log.e(TAG, "existe un error en la conexión -----> " + error.getMessage());
					Log.d(TAG, "e2lira -----------> " + new String(responseBody));

					AlertDialog.Builder bd = new AlertDialog.Builder(MainActivityPantalla1.this);
					AlertDialog ad = bd.create();
					ad.setTitle(getResources().getString(R.string.title_dialog));
					ad.setMessage("Exite un error en la conexión con el sistema central, la información se guardo en el dispositivo");
					ad.show();
				}
			}
		});

	}

	public void showAlertDialog(String mensaje, String descripcion){

		//builder.setTitle("Éxito");
		AlertDialog alertDialog;
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivityPantalla1.this);
		builder.setMessage(mensaje);


		LinearLayout diagLayout = new LinearLayout(this);
		diagLayout.setOrientation(LinearLayout.VERTICAL);
		TextView text = new TextView(this); text.setText(descripcion);
		text.setPadding(10, 10, 10, 10);
		text.setGravity(Gravity.CENTER);
		text.setTextSize(15);
		text.setTextColor(Color.parseColor("#B9B9B9"));
		diagLayout.addView(text);
		builder.setView(diagLayout);


		LayoutInflater inflater = this.getLayoutInflater();

		View titleView = inflater.inflate(R.layout.custom_alert, null);

		builder.setCustomTitle(titleView);


		builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(MainActivityPantalla1.this, MenuActivity.class);
				intent.putExtra(MenuActivity.TOKEN, token);
				intent.putExtra(MenuActivity.USER_ID, id_user);
				intent.putExtra(MenuActivity.EMAIL, email);
				intent.putExtra(MenuActivity.CUADRANTE, cuadrante);
				intent.putExtra(MenuActivity.NOMBRE, nombre);
				finish();
				startActivity(intent);
			}
		});
/*		builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});*/
		alertDialog =builder.create();
		alertDialog.show();

		//TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
		//textView.setTextSize(20);

	}

    public void CargaSpinnerVisita() {
        String var = "Selecciona";
        if (var == null) {var = "";}
        final String[] datos = new String[] { "" + var + "",
                "Casa Habitación",
                "Comercio / Negocio",
                "Mixto"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoVisita.setAdapter(adaptador);

    }


}
