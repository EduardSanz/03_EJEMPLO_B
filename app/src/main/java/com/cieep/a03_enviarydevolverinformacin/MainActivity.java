package com.cieep.a03_enviarydevolverinformacin;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cieep.a03_enviarydevolverinformacin.modelos.Direccion;
import com.cieep.a03_enviarydevolverinformacin.modelos.Usuario;

public class MainActivity extends AppCompatActivity {

    private EditText txtPassword;
    private EditText txtEmail;
    private Button btnDescifrar;
    private Button btnCrearDireccion;

    // Constantes de Identificación de Actividades
    private final int DIRECCIONES = 3;
    private final int CAMIONES = 4;

    // ActivityResultLaunchers
    private ActivityResultLauncher<Intent> launcherDirecciones;
    private ActivityResultLauncher<Intent> launcherCamiones;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar Vistas
        inicializaVistas();
        // Inicializar Launchers
        inicializaLaunchers();



        btnDescifrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = txtPassword.getText().toString();
                String email = txtEmail.getText().toString();
                Intent intent = new Intent(MainActivity.this, DescifrarActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("USER", new Usuario(email, password));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btnCrearDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateDireccionActivity.class);
               //  startActivityForResult(intent, DIRECCIONES);
                launcherDirecciones.launch(intent);
            }
        });
    }

    private void inicializaLaunchers() {
        // RegisterForActivityResult
        // 1. Modo en que se lanza el Intent
        // 2. Acciones a realizar DESPUES de que se cierre el intent
        launcherDirecciones = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK){
                            if (result.getData() != null) {
                                Bundle bundle = result.getData().getExtras();
                                Direccion dir = (Direccion) bundle.getSerializable("DIR");
                                Toast.makeText(MainActivity.this, dir.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(MainActivity.this, "ACCION CANCELADA", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void inicializaVistas() {
        txtEmail = findViewById(R.id.txtEmailMain);
        txtPassword = findViewById(R.id.txtPasswordMain);
        btnDescifrar = findViewById(R.id.btnDescifrarMain);
        btnCrearDireccion = findViewById(R.id.btnCrearDireccionMain);
    }

    /**
     *          (DEPRECATED)
     * Método que se dispara cuando lanzo una actividad con startActivityForResult
     * @param requestCode identificado de la Actividad que esta retornando datos
     * @param resultCode estado en el que ha terminado la actividad de los datos
     * @param data el intent que, en caso de existir, contiene los datos
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DIRECCIONES){
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    Direccion dir = (Direccion) bundle.getSerializable("DIR");
                    // Lógica para trabajar con la dirección
                    Toast.makeText(this, dir.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this, "ACCIÓN CANCELADA", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == CAMIONES) {

        }
    }
}