package mx.gob.cdmx.app_territorial.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import mx.gob.cdmx.app_territorial.R;
import mx.gob.cdmx.app_territorial.service.GPSTracker;
import mx.gob.cdmx.app_territorial.service.Imei;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {
    private static final String TAG            = "DIF-LoginActivity";
    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    Calendar c = Calendar.getInstance();

    SimpleDateFormat df3 = new SimpleDateFormat("yyyMMdd");
    String formattedDate3 = df3.format(c.getTime());
    SimpleDateFormat df5 = new SimpleDateFormat("HH:mm a");
    String formattedDate5 = df5.format(c.getTime());
    String latitud, longitud;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private TextView versionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        versionTextView = findViewById(R.id.versionTextView);

        File directory;
        File file;
        File sdCard;
        sdCard = Environment.getExternalStorageDirectory();
        FileOutputStream fout = null;
        try {

            directory = new File(sdCard.getAbsolutePath() + "/Mis_archivos");
            directory.mkdirs();
            directory = new File(sdCard.getAbsolutePath()+ "/Fotos/Fotos_SSC"+formattedDate3);
            directory.mkdirs();
            directory = new File(sdCard.getAbsolutePath()+ "/Fotos/Fotos_SSC"+formattedDate3+"N");
            directory.mkdirs();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
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
                    Toast.makeText(this, "¡Permisos autorizados!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        final String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }
/*        else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }*/

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);

            //mAuthTask = new UserLoginTask(email, password);
            //mAuthTask.execute((Void) null);
            Imei imei = new Imei(this);
            String device_info = imei.getDeviceInof();
            Log.d(TAG, device_info);
            RequestParams params = new RequestParams();
            params.put("email", email);
            params.put("password", password);
            params.put("project", getString(R.string.project_object));
            params.put("imei", imei.getImai());
            params.put("device_info", imei.getDeviceInof()); // adicionar de la clase IMEI
            GPSTracker gps = new GPSTracker(LoginActivity.this);
            double latitud = gps.getLatitude();
            double longitud = gps.getLongitude();

            Log.i(TAG, "----------> la latitud: "+ latitud);
            Log.i(TAG, "----------> la longitud: "+ longitud);
            params.put("latitude", latitud);
            params.put("longitude", longitud);
            AsyncHttpClient client = new AsyncHttpClient();


            // client.setBasicAuth("email","password", new AuthScope("example.com", 80, AuthScope.ANY_REALM));
            // client.get("https://example.com");

            RequestHandle requestHandle = client.post(getResources().getString(R.string.url_aplicacion) + "/api/auth/login", params, new AsyncHttpResponseHandler() {
                String jsonToken = "";
                int id_user;
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String nombreStr = "";
                    Log.d(TAG, "Realizo la conexión");

                    Log.d(TAG, "e2lira -----------> " + new String(responseBody));
                    String json = new String(responseBody);
                    try {

                        JSONObject jsonObject = new JSONObject(json);
                        Log.d(TAG, "e2lira -----------> Data: " + jsonObject.get("data"));
                        //
                        // para acceder al token
                        //
                        jsonToken = jsonObject.getJSONObject("data").getString("access_token");
                        JSONObject jsonUser = jsonObject.getJSONObject("data").getJSONObject("user");
                        int id_user = jsonUser.getInt("user_id");
                        nombreStr = jsonUser.getString("name");
                        Log.d(TAG, "e2lira -----------> Token: " + jsonToken );
                        Log.d(TAG, "e2lira ----------->  id: " + id_user);
                    } catch (JSONException e){
                        Log.e(TAG, e.getMessage());
                    }


                    showProgress(false);
                    // Abrir la nueva ventana
                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                    intent.putExtra(MenuActivity.TOKEN, jsonToken);
                    intent.putExtra(MenuActivity.USER_ID, id_user);
                    intent.putExtra(MenuActivity.EMAIL, email);
                    intent.putExtra(MenuActivity.CUADRANTE, password);
                    intent.putExtra(MenuActivity.NOMBRE, nombreStr);
                    finish();
                    startActivity(intent);

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.e(TAG, "existe un error en la conexión status code: " + statusCode);
                    showProgress(false);
                    AlertDialog.Builder bd = new AlertDialog.Builder(LoginActivity.this);
                    AlertDialog ad = bd.create();
                    ad.setTitle(getString(R.string.app_name));
                    ad.setMessage("Exite un error en la conexión con el sistema central, o la contraseña esta incorrecta");
                    ad.show();
                }
            });

        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }




}

