package mx.gob.cdmx.app_territorial.service;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import mx.gob.cdmx.app_territorial.R;
import mx.gob.cdmx.app_territorial.db.DatabaseHandler;


public class SignupActivity extends AsyncTask<String, Void, String> {

	private Context context;
	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";
	String imei_num;
	DatabaseHandler usdbh;
	private SQLiteDatabase db;

	private Date yesterday() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}

	Calendar c = Calendar.getInstance();
	SimpleDateFormat df3 = new SimpleDateFormat("yyyMMdd");
	String formattedDateFecha = df3.format(c.getTime());
	SimpleDateFormat df5 = new SimpleDateFormat("HH:mm");
	String formattedDateHora = df5.format(c.getTime());
	SimpleDateFormat ayer = new SimpleDateFormat("yyyyMMdd");
	String formattedDateAyer = ayer.format(yesterday());

	int serverResponseCode = 0;

	static InputStream is2 = null;


	public SignupActivity(Context context) {
		this.context = context;
	}

	protected void onPreExecute() {

		imei_num = IMEI.get_dev_id(context);
		Log.i(null, "El chip: " + imei_num);

	}


	@Override
	protected String doInBackground(String... arg0) {

		String latitud = arg0[0];
		String longitud = arg0[1];
		String direccion = arg0[2];


		String newDireccion = direccion.replaceAll("\\s", "_");
		String laDireccion = newDireccion.trim();

		String link;
		String data = null;
		BufferedReader bufferedReader;
		String result;


		String NombreA = "GPS_" + formattedDateFecha + "_" + imei_num;

		String hora = formattedDateHora;
		String elImei = imei_num;
		String token = "ZJvI7PooUhZogIarOp8v";

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(this.context.getResources().getString(R.string.url_gps) + "/api/location/set?latitude=" + latitud + "&longitude=" + longitud + "&data=" + laDireccion + "&imei=" + elImei + "&token=" + token + "");

			HttpResponse response2 = httpclient.execute(httppost);
			HttpEntity entity2 = response2.getEntity();
			is2 = entity2.getContent();

			Log.i("log_tag", "connection success ");
			Log.i("log_tag", "connection: " + this.context.getResources().getString(R.string.url_gps) + "/api/location/set?latitude=" + latitud + "&longitude=" + longitud + "&data=" + laDireccion + "&imei=" + elImei + "&token=" + token + "");
//			Log.i("log_tag", "Imei "+elImei);

			usdbh = new DatabaseHandler(this.context);
			db = usdbh.getWritableDatabase();
			if (db != null) {

				db.execSQL("DELETE FROM ubicacion where fecha='" + formattedDateAyer + "'");
				Log.i("log_tag", "connection: " + "DELETE FROM ubicacion where fecha='" + formattedDateAyer + "'");
				db.execSQL("INSERT INTO ubicacion (fecha,hora,latitud,longitud,direccion) values (" + "'" + formattedDateFecha + "'" + "," + "'" + hora + "'" + "," + "'" + latitud + "'" + "," + "'" + longitud + "'" + "," + "'" + laDireccion + "');");
				Log.i("log_tag", "connection: " + "INSERT INTO ubicacion (fecha,hora,latitud,longitud,direccion) values (" + "'" + formattedDateFecha + "'" + "," + "'" + hora + "'" + "," + "'" + latitud + "'" + "," + "'" + longitud + "'" + "," + "'" + laDireccion + "');");
			} else {
				Log.i("log_tag", "mal connection: " + "INSERT INTO ubicacion (fecha,hora,latitud,longitud,direccion) values (" + "'" + formattedDateFecha + "'" + "," + "'" + hora + "'" + "," + "'" + latitud + "'" + "," + "'" + longitud + "'" + "," + "'" + laDireccion + "');");
			}


		} catch (Exception e) {
			String stackTrace = Log.getStackTraceString(e);
			Log.i("log_tag", "connection error" + stackTrace);
			Log.i("log_tag", "Error in http connection " + e.toString());

		}

		return null;


//			HttpClient httpclient = new DefaultHttpClient();
//		    HttpPost httpPost = new HttpPost("https://territorial.cdmx.gob.mx/api/location/set");
//		 
//		    List<NameValuePair> params = new ArrayList<NameValuePair>();
//		    params.add(new BasicNameValuePair("latitude", latitud));
//		    params.add(new BasicNameValuePair("longitude", longitud));
//		    params.add(new BasicNameValuePair("data", laDireccion));
//		    params.add(new BasicNameValuePair("imei", elImei));
//		    params.add(new BasicNameValuePair("token", token));
//		    try {
//				httpPost.setEntity(new UrlEncodedFormEntity(params));
//				HttpResponse response2 = httpclient.execute(httpPost);
//			    HttpEntity entity2 = response2.getEntity();
//				is2 = entity2.getContent();
//				
//				Log.i("log_tag", "connection por pares success ");
//							
//			} catch (UnsupportedEncodingException e) {
//				String stackTrace = Log.getStackTraceString(e);
//				Log.i("log_tag", "UnsupportedEncodingException error" + stackTrace);
//			} catch (ClientProtocolException e) {
//				String stackTrace = Log.getStackTraceString(e);
//				Log.i("log_tag", "ClientProtocolException error" + stackTrace);
//			} catch (IOException e) {
//				String stackTrace = Log.getStackTraceString(e);
//				Log.i("log_tag", "IOException error" + stackTrace);
//			}
//
//	
//
//		return null;


	}

	@Override
	protected void onPostExecute(String result) {


		//  	 Log.i(null, "Connection: "+ "hecho");

//        String jsonStr = result.toString();
//        Log.i(null, "el json: "+ jsonStr);
//        if (jsonStr != null) {
//            try {
//                JSONObject jsonObj = new JSONObject(jsonStr);
//                String query_result = jsonObj.getString("query_result");
//                if (query_result.equals("SUCCESS")) {
//                    Log.i(null, "Connection: "+ "Success");
//                } else if (query_result.equals("FAILURE")) {
//                     Log.i(null, "Connection: "+ "Falla");
//                } else {
//                     Log.i(null, "Connection: "+ "No se Puede conectar a la BD");
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//                Log.i(null, "Connection: "+ "Error parsing JSON data.");
//            }
//        } else {
////                Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_LONG).show();
//        }
	}

	public static class IMEI {

		public static String get_dev_id(Context ctx) {
			String Imei = "";
			//Getting the Object of TelephonyManager
			TelephonyManager tManager = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
			//Getting IMEI Number of Devide
			if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
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
    }
    
  
}
