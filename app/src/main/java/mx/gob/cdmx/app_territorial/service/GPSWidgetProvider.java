package mx.gob.cdmx.app_territorial.service;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.widget.RemoteViews;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.gob.cdmx.app_territorial.R;
import mx.gob.cdmx.app_territorial.db.DatabaseHandler;
import mx.gob.cdmx.app_territorial.view.BienvenidaActivity;


public class GPSWidgetProvider extends AppWidgetProvider {
	public static String mLastUpdateTime;
	static int serverResponseCode = 0;
	static Calendar c = Calendar.getInstance();
	static SimpleDateFormat df3 = new SimpleDateFormat("yyyMMdd");
	static String formattedDateFecha = df3.format(c.getTime());
	private PendingIntent service = null;
	DatabaseHandler usdbh;
	private SQLiteDatabase db;

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
	}

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);

		//se implementa AlarmManager para actualizar divamicamente el widget
		final AlarmManager m = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		final Intent i = new Intent(context, GPSWidgetService.class);
		if (service == null) {
			service = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
		}

		final int N = appWidgetIds.length;

		//Iteramos la lista de widgets en ejecuci�n
		for (int j = 0; j < N; j++) {
			//ID del widget actual
			int appWidgetId = appWidgetIds[j];

			Intent intent = new Intent(context, BienvenidaActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.gpswidget);
			views.setOnClickPendingIntent(R.id.txtInfo, pendingIntent);

			appWidgetManager.updateAppWidget(appWidgetId, views);
		}

		context.startService(new Intent(context, GPSWidgetService.class));

		//tiempo de repeticiones
		m.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 300000, service);

	}

	public static class GPSWidgetService extends Service {
		private LocationManager manager = null;

		private LocationListener listener = new LocationListener() {
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
			}

			@Override
			public void onProviderEnabled(String provider) {
			}

			@Override
			public void onProviderDisabled(String provider) {
			}

			@Override
			public void onLocationChanged(Location location) {
				AppLog.logString("Service.onLocationChanged()");
				mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

				updateCoordinates(location.getLatitude(), location.getLongitude());

				stopSelf();
			}
		};

		@Override
		public IBinder onBind(Intent intent) {
			return null;
		}

		@Override
		public void onCreate() {
			super.onCreate();

			AppLog.logString("Service.onCreate()");

			manager = (LocationManager) getSystemService(LOCATION_SERVICE);
		}

		@Override
		public void onStart(Intent intent, int startId) {
			super.onStart(intent, startId);

			waitForGPSCoordinates();
		}

		@Override
		public void onDestroy() {
//			stopListening();

			AppLog.logString("Service.onDestroy()");

			super.onDestroy();
		}

		public int onStartCommand(Intent intent, int flags, int startId) {
			waitForGPSCoordinates();

			AppLog.logString("Service.onStartCommand()");

			return super.onStartCommand(intent, flags, startId);
		}

		private void waitForGPSCoordinates() {
			startListening();
		}

		private void startListening() {
			AppLog.logString("Service.startListening()");

			final Criteria criteria = new Criteria();

			criteria.setAccuracy(Criteria.ACCURACY_COARSE);
			criteria.setAltitudeRequired(false);
			criteria.setBearingRequired(false);
			criteria.setCostAllowed(true);
			criteria.setPowerRequirement(Criteria.POWER_LOW);

			final String bestProvider = manager.getBestProvider(criteria, true);

			if (bestProvider != null && bestProvider.length() > 0) {
				if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
					// TODO: Consider calling
					//    ActivityCompat#requestPermissions
					// here to request the missing permissions, and then overriding
					//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
					//                                          int[] grantResults)
					// to handle the case where the user grants the permission. See the documentation
					// for ActivityCompat#requestPermissions for more details.
					return;
				}
				manager.requestLocationUpdates(bestProvider, 30000, 10, listener);
			} else {
				final List<String> providers = manager.getProviders(true);

				for (final String provider : providers) {
					manager.requestLocationUpdates(provider, 30000, 10, listener);
				}
			}
		}

		private void stopListening() {
			try {
				if (manager != null && listener != null) {
					manager.removeUpdates(listener);
				}

				manager = null;
			} catch (final Exception ex) {

			}
		}

		private void updateCoordinates(double latitude, double longitude) {
	        String hora = mLastUpdateTime.toString();
	        String latitud = String.valueOf(latitude);
	        String longitud = String.valueOf(longitude);
	        String direccion = "";
			
			Geocoder coder = new Geocoder(this);
			List<Address> addresses = null;
			String info = "";
			
			AppLog.logString("Service.updateCoordinates()");
			AppLog.logString(info);

			try {
				addresses = coder.getFromLocation(latitude, longitude, 2);

				if (null != addresses && addresses.size() > 0) {
					int addressCount = addresses.get(0).getMaxAddressLineIndex();

					if (-1 != addressCount) {
						for (int index = 0; index <= addressCount; ++index) {
							info += addresses.get(0).getAddressLine(index);
							direccion += addresses.get(0).getAddressLine(index);

							if (index < addressCount)
								info += ", ";
							direccion += ", ";
						}
					} else {
						info += addresses.get(0).getFeatureName() + ", " + addresses.get(0).getSubAdminArea() + ", "
								+ addresses.get(0).getAdminArea();
						direccion += addresses.get(0).getFeatureName() + ", " + addresses.get(0).getSubAdminArea() + ", "
								+ addresses.get(0).getAdminArea();
					}
				}

				AppLog.logString(addresses.get(0).toString());
			} catch (Exception e) {
				e.printStackTrace();
			}

			coder = null;
			addresses = null;

			if (info.length() <= 0) {
				info = "lat " + latitude + ", lon " + longitude;
				direccion="";
			} else {
				info += ("\n" + "(lat " + latitude + ", lon " + longitude + ")");
				direccion += ("");
			}
			
			

			RemoteViews views = new RemoteViews(getPackageName(), R.layout.gpswidget);

			views.setTextViewText(R.id.txtInfo, info);
			new SignupActivity(this).execute(latitud,longitud,direccion);

			ComponentName thisWidget = new ComponentName(this, GPSWidgetProvider.class);
			AppWidgetManager.getInstance(this).updateAppWidget( thisWidget, views );
	
//				sube.subeArchivo();
				

		}

		
	}

	 public static class IMEI {

	        public static String get_dev_id(Context ctx){
				String Imei = "";

				String deviceId = Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);
				TelephonyManager tManager = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
				// Getting IMEI Number of Devide

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