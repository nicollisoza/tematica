package com.example.fastrest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText correo, contra;
    private Button login;
    private Button btn_registrarme;
    private String corre, contr;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_registrarme = (Button)findViewById(R.id.btn_registro);
        inicializar();
        firebaseAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                corre = correo.getText().toString();
                contr = contra.getText().toString();
                if(!corre.isEmpty() && !contr.isEmpty()){
                    iniciarSesion();
                }
                else{
                    Toast.makeText(MainActivity.this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    private void inicializar(){
        correo = findViewById(R.id.correoLog);
        contra = findViewById(R.id.contraLog);
        login = findViewById(R.id.Ingreso);
    }

    private void iniciarSesion(){
        firebaseAuth.signInWithEmailAndPassword(corre, contr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this, asignatura.class));
                    Toast.makeText(MainActivity.this, "Ha iniciado sesión", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "No ha podido iniciar sesión, intente más tarde", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void ingresoRegistrarme(View view){
        Intent intent = new Intent(MainActivity.this, registrarme.class );
        startActivity(intent);
    }
    public void ingresoRecuperarClave(View view){
        Intent intent = new Intent(MainActivity.this, recuperar_clave.class );
        startActivity(intent);
    }

}
