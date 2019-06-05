package mx.gob.cdmx.app_territorial.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import mx.gob.cdmx.app_territorial.R;
import mx.gob.cdmx.app_territorial.db.DatabaseHandler;

public class MenuActivity extends AppCompatActivity {

    private static final String TAG  = "MenuActivity";

    private int id_user;
    private String email;
    private String token;
    private String cuadrante;
    private String nombre;

    private DatabaseHandler dbHandler;

    static final String EMAIL = "email";
    static final String USER_ID = "userId";
    static final String TOKEN = "token";
    static final String CUADRANTE = "cuadrante";
    static final String NOMBRE = "nombre";
    static final String FOTO = "foto";
    static final String LATITUD = "latitud";
    static final String LONGITUD = "longitud";
    static final String MANZANA = "manzana";

    Button visitaButton, incidenteButton, salirButton;
    TextView nombreTextView, placaTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Iniciando conección a la BD
        dbHandler = new DatabaseHandler(this.getApplicationContext());

        visitaButton = findViewById(R.id.visitaMenuButton);
        incidenteButton = findViewById(R.id.incidenteMenuButton);
        nombreTextView = findViewById(R.id.nombreTextView);
        placaTextView = findViewById(R.id.placaTextView);
        salirButton = findViewById(R.id.salirButton);

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
            email = savedInstanceState.getString(EMAIL);
            id_user = savedInstanceState.getInt(USER_ID);
            token = savedInstanceState.getString(TOKEN);
            cuadrante = savedInstanceState.getString(CUADRANTE);

        } else {
            // Probably initialize members with default values for a new instance
            email = startingIntent.getStringExtra(EMAIL);
            id_user = startingIntent.getIntExtra(USER_ID,0);
            token = startingIntent.getStringExtra(TOKEN);
            cuadrante = startingIntent.getStringExtra(CUADRANTE);
            nombre = startingIntent.getStringExtra(NOMBRE);
            nombreTextView.setText("¡ Hola " + nombre + " !");
            placaTextView.setText("Placa: " + email);
        }

        Log.d(TAG,  " onCreate --------------> cuadrante: " + cuadrante);

        visitaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MapaActivity.class);
                intent.putExtra(MenuActivity.TOKEN, token);
                intent.putExtra(MenuActivity.USER_ID, id_user);
                intent.putExtra(MenuActivity.EMAIL, email);
                intent.putExtra(MenuActivity.CUADRANTE, cuadrante);
                intent.putExtra(MenuActivity.NOMBRE, nombre);
                finish();
                startActivity(intent);
            }
        });

        salirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });

/*        AlcaldiaRepository alcaldiaRepository = new AlcaldiaRepository(dbHandler.getReadableDatabase());

        List<Alcaldia> alcaldiaList = alcaldiaRepository.findAllAlcaldia();

        for(Alcaldia alcaldia: alcaldiaList){
            Log.d(TAG, "e2lira ------------->" + alcaldia.getNombre());
        }*/

    }
}
