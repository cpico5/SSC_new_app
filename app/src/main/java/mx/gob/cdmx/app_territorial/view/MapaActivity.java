package mx.gob.cdmx.app_territorial.view;

//import com.example.semanal20190216.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.GeolocationPermissions;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import mx.gob.cdmx.app_territorial.R;


@SuppressLint("SetJavaScriptEnabled")
public class MapaActivity extends Activity {

	final static String TAG = "MapaActivity";

	private int id_user;
	private String email;
	private String token;
	private String cuadrante;
	private String nombre;
	private String manzana;


	private WebView pagina;
	private Button btnSiguiente;
	private Button btnRandom;
	static InputStream is2 = null;
	String result3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mapa);


		//final String manzanaAsignada=getManzana(cuadrante);

		Log.w(TAG, "la manzana Asignada: :  " + manzana);

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
		}

		//getManzana(cuadrante);
		 manzana = postData(cuadrante);
		Log.w(TAG, "la manzana Asignada: :  "+ manzana);

		Log.e(TAG,"---------------> Cuadrante: " + cuadrante);

			btnSiguiente = (Button) findViewById(R.id.btnSiguiente);
			btnRandom = (Button) findViewById(R.id.btnRandom);
		
		    pagina = (WebView) findViewById(R.id.webView1);
	        //habilitamos javascript y el zoom
	        pagina.getSettings().setJavaScriptEnabled(true);
	        pagina.getSettings().setBuiltInZoomControls(true);
	        pagina.setInitialScale(1);
	        pagina.getSettings().setLoadWithOverviewMode(true);
	        pagina.getSettings().setUseWideViewPort(true);
	        pagina.setScrollBarStyle(pagina.SCROLLBARS_OUTSIDE_OVERLAY);
	        pagina.setScrollbarFadingEnabled(false);

	        pagina.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
	        pagina.getSettings().setBuiltInZoomControls(true);
	       
	        // Below required for geolocation
	        pagina.getSettings().setJavaScriptEnabled(true);
	        pagina.setWebChromeClient(new GeoWebChromeClient());

		pagina.getSettings().setAppCacheEnabled(true);
		pagina.getSettings().setGeolocationEnabled(true);
		pagina.getSettings().setDatabaseEnabled(true);
	        pagina.getSettings().setDomStorageEnabled(true);

	        pagina.getSettings().setGeolocationDatabasePath(getFilesDir().getPath());

	    //    pagina.loadUrl("https://censo19s.mx/cgi-bin/php/geo/estoy.php");
	//        pagina.loadUrl("https://app.sies2018.org/");
	        
	        pagina.loadUrl("https://opinion.cdmx.gob.mx/cgi-bin/php/geo/mapa_sectores_cuadrantes.php?cuadrante=" + cuadrante);

	        pagina.setWebViewClient(new WebViewClient()
	        {
	    // evita que los enlaces se abran fuera nuestra app en el navegador de android
	            @Override
	            public boolean shouldOverrideUrlLoading(WebView view, String url)
	            {
	                return false;
	            }   
	             
	        });
	        
	        
	        
	        btnSiguiente.setOnClickListener(new OnClickListener() {
				@SuppressWarnings("null")
				@Override
				public void onClick(View v) {
//					// TODO Auto-generated method stub Aqui para generar el archivo vacio// hasta la 112
				
					 Intent intent = new Intent(MapaActivity.this, FotoEvidencia.class);
	                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                    Bundle bundle = new Bundle();
	                    intent.putExtras(bundle);
						intent.putExtra(MenuActivity.TOKEN, token);
						intent.putExtra(MenuActivity.USER_ID, id_user);
						intent.putExtra(MenuActivity.EMAIL, email);
						intent.putExtra(MenuActivity.CUADRANTE, cuadrante);
						intent.putExtra(MenuActivity.NOMBRE, nombre);
						intent.putExtra(MenuActivity.MANZANA, manzana);
						Log.d(TAG, "--------------> Nombre: " + nombre + " , Manzana: " + manzana);
	                    startActivity(intent);
//	                    pd.dismiss();
//	                    dialog.dismiss();
	                    finish();

				}
			});

		btnRandom.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("null")
			@Override
			public void onClick(View v) {
//					// TODO Auto-generated method stub Aqui para generar el archivo vacio// hasta la 112
				finish();
				startActivity(getIntent());

			}
		});
	        
	        
	    	 
	}
	
/*	public void siguiente(){
		Toast.makeText(this, "BOTON SIGUIENTE :) ",Toast.LENGTH_LONG).show();

	}
	*/
	
	
	
	 public class GeoWebChromeClient extends android.webkit.WebChromeClient {
         @Override
         public void onGeolocationPermissionsShowPrompt(final String origin,
                                                        final GeolocationPermissions.Callback callback) {
             // Always grant permission since the app itself requires location
             // permission and the user has therefore already granted it
             callback.invoke(origin, true, false);

 //            final boolean remember = false;
 //            AlertDialog.Builder builder = new AlertDialog.Builder(WebViewActivity.this);
 //            builder.setTitle("Locations");
 //            builder.setMessage("Would like to use your Current Location ")
 //                    .setCancelable(true).setPositiveButton("Allow", new DialogInterface.OnClickListener() {
 //                public void onClick(DialogInterface dialog, int id) {
 //                    // origin, allow, remember
 //                    callback.invoke(origin, true, remember);
 //                }
 //            }).setNegativeButton("Don't Allow", new DialogInterface.OnClickListener() {
 //                public void onClick(DialogInterface dialog, int id) {
 //                    // origin, allow, remember
 //                    callback.invoke(origin, false, remember);
 //                }
 //            });
 //            AlertDialog alert = builder.create();
 //            alert.show();
         }
     }

	
	 public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {
        Log.i(null, "onGeolocationPermissionsShowPrompt()");

        final boolean remember = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Locations");
        builder.setMessage("Would like to use your Current Location ")
        .setCancelable(true).setPositiveButton("Allow", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
              // origin, allow, remember
              callback.invoke(origin, true, remember);
           }
        }).setNegativeButton("Don't Allow", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
              // origin, allow, remember
              callback.invoke(origin, false, remember);
           }
        });
        AlertDialog alert = builder.create();
        alert.show();
     }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void getManzana(String cuadrante){
		RequestParams params = new RequestParams();

		AsyncHttpClient client = new AsyncHttpClient();


		RequestHandle requestHandle = client.get("https://opinion.cdmx.gob.mx/cgi-bin/php/geo/mapa_sectores_cuadrantes.php?cuadrante="+cuadrante, params, new AsyncHttpResponseHandler() {
			String jsonToken = "";
			int id_user;
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				String nombreStr = "";
				Log.d(TAG, "e2lira -----------> Realizo la conexión para traer la manzan");

				Log.d(TAG, "e2lira -----------> " + new String(responseBody));
				String json = new String(responseBody);
				try {

					JSONObject jsonObject = new JSONObject(json);
					Log.d(TAG, "e2lira -----------> Data: " + jsonObject.get("data"));
					JSONArray jArray2 = new JSONArray(json);
					Log.w("Lengh", "" + jArray2.length());
					Log.w(TAG, "" + "Tengo más de un dato postdata:  ");
					Log.w(TAG, "lA LONGITUD" + jArray2.length());

					int tamano=jArray2.length();

					for (int i = 0; i < jArray2.length(); i++) {

						JSONObject json_data2 = jArray2.getJSONObject(i);
						manzana = json_data2.getString("num_manzana");
						Log.w(TAG, "La manzana: "+ manzana );
					}


				} catch (JSONException e){
					Log.e(TAG, "-------------> Error: " +  e.getMessage());
				}


			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

				if (statusCode != 200) {
					Log.e(TAG, "existe un error en la conexión -----> " + error.getMessage());
					Log.d(TAG, "e2lira -----------> " + new String(responseBody));

				}
			}
		});

	}

	public String postData(String cuadrante) {
		String manazanita="";

		try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("https://opinion.cdmx.gob.mx/cgi-bin/php/geo/mapa_sectores_cuadrantes.php?cuadrante="+cuadrante);
			HttpResponse response2 = httpclient.execute(httppost);
			HttpEntity entity2 = response2.getEntity();
			is2 = entity2.getContent();
			Log.e("log_tag", "connection success ");
		} catch (Exception e) {
			String stackTrace = Log.getStackTraceString(e);
			Log.e(TAG,"Error connection"+ stackTrace);
		}
		try {
			BufferedReader reader2 = new BufferedReader(new InputStreamReader(is2, "iso-8859-1"), 8);
			StringBuilder sb2 = new StringBuilder();
			String line = null;
			while ((line = reader2.readLine()) != null) {
				sb2.append(line + "\n");
			}
			is2.close();
			result3 = sb2.toString();
			Log.i(TAG, "Result PostData: " + result3+"\n");
		} catch (Exception e) {
			Log.e(TAG, "Error converting result " + e.toString());
		}
		try {

			JSONArray jArray2 = new JSONArray(result3);
			Log.w("Lengh", "" + jArray2.length());
			Log.w(TAG, "" + "Tengo más de un dato postdata:  ");
			Log.w(TAG, "lA LONGITUD" + jArray2.length());

			int tamano=jArray2.length();

			for (int i = 0; i < jArray2.length(); i++) {

				JSONObject json_data2 = jArray2.getJSONObject(i);
				manazanita = json_data2.getString("num_manzana");
				Log.w(TAG, "La manzana: "+ manazanita );
			}
		}
		catch (JSONException e) {
			Log.e(TAG, "Error parsing data:  " + e.toString());
		}
		return manazanita;
	}// end postData()

}
