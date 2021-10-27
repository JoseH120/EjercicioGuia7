package personal.app.ejerciciopersistenciadato;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import personal.app.ejerciciopersistenciadato.data.SharedPreferencesConfig;

public class JuegoActivity extends AppCompatActivity {
    SharedPreferencesConfig preferences;
    TextView textview2, textview3, textview10;
    EditText edtrespuesta;
    Button btnenviar;
    String nombre, partida;
    int numero, puntaje, intentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        if(savedInstanceState != null){
            intentos = savedInstanceState.getInt("intentos");
        }else{
            intentos = 10;
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        edtrespuesta = (EditText) findViewById(R.id.edtrespuesta);
        btnenviar = (Button) findViewById(R.id.btnenviar);
        textview2 = (TextView) findViewById(R.id.textview2);
        textview3 = (TextView) findViewById(R.id.textview3);
        textview10  = (TextView) findViewById(R.id.textview10);
        
        preferences = new SharedPreferencesConfig(getApplicationContext());

        nombre = getIntent().getStringExtra("user");
        numero = getIntent().getIntExtra("numero", 0);
        puntaje = getIntent().getIntExtra("puntaje", 0);

        textview10.setText("Usuario: \n"+nombre);
        textview2.setText("Puntaje: \n"+puntaje);
        textview3.setText("Intentos restantes: " + intentos);

        btnenviar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(verificar()){
                    Toast.makeText(JuegoActivity.this, "Has adivinado el numero!", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(JuegoActivity.this, MainActivity.class));
                }else{
                    restar();
                    if(intentos == 0){
                        Toast.makeText(JuegoActivity.this, "!No hay mas intentos!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });

    }

    void restar(){
        intentos -= 1;
        textview3.setText("Intentos restantes: " + intentos);
    }

    boolean verificar(){
        boolean bandera = false;
        if(!edtrespuesta.getText().toString().isEmpty()){
            if(numero == Integer.parseInt(edtrespuesta.getText().toString())){
                puntaje += intentos;
                SharedPreferences.Editor edit = preferences.getPreferences().edit();
                edit.putInt("numero", numero);
                edit.putString("partida", "nuevo");
                edit.putInt("puntaje", puntaje);
                edit.commit();
                bandera = true;
            }else if( numero > Integer.parseInt(edtrespuesta.getText().toString() )){
                edtrespuesta.setError("!El numero secreto es mayor!");
                bandera =false;
            }
            else if( numero < Integer.parseInt(edtrespuesta.getText().toString())){
                edtrespuesta.setError("!El numero secreto es menor!");
                bandera =false;
            }
        }else if(edtrespuesta.getText().toString().isEmpty()){
            edtrespuesta.setError("Campo requerido ");
            bandera = false;
        }
        return bandera;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
            break;
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("intentos", intentos);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //intentos = savedInstanceState.getInt("intentos");
    }

}