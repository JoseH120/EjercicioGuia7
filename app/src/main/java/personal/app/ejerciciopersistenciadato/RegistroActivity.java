package personal.app.ejerciciopersistenciadato;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import personal.app.ejerciciopersistenciadato.data.SharedPreferencesConfig;
import personal.app.ejerciciopersistenciadato.utils.Utils;

public class RegistroActivity extends AppCompatActivity {

    private SharedPreferencesConfig preferences;
    private EditText edtnombre;
    private Button btnguardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();

        edtnombre = (EditText) findViewById(R.id.edtnombre);
        btnguardar = (Button) findViewById(R.id.btnguardar);

        preferences = new SharedPreferencesConfig(getApplicationContext());

        Intent intent = new Intent(RegistroActivity.this, MainActivity.class);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnguardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Utils.verifyEditText(edtnombre)) {
                    addUser();
                    startActivity(intent);
                    Toast.makeText(RegistroActivity.this, "usuario registrado", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }

    private void addUser() {
        SharedPreferences.Editor editor = preferences.getPreferences().edit();
        editor.putString("user", edtnombre.getText().toString());
        editor.putInt("puntaje", 0);
        editor.commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

}

