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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registrarme extends AppCompatActivity {

    private EditText nombre, correo, telefono, contraseña, confir;
    private Button registro;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private String nomb, corre, telefon, contraseñ, confi;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarme);
        inicializar();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase.getInstance();
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarDatos()){
                    corre = correo.getText().toString().trim();
                    contraseñ = contraseña.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(corre,contraseñ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                guardarDatos();
                                Toast.makeText(registrarme.this, "Se ha registrado", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent (registrarme.this, MainActivity.class));
                            }
                            else{
                                Toast.makeText(registrarme.this, "No se ha podido registrar, intente más tarde", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


    }

    private boolean validarDatos(){
        Boolean a = false;
        nomb = nombre.getText().toString();
        corre = correo.getText().toString();
        telefon = telefono.getText().toString();
        contraseñ = contraseña.getText().toString();
        confi = confir.getText().toString();
        if(nomb.isEmpty() || corre.isEmpty() || telefon.isEmpty() || contraseñ.isEmpty()){
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
        if(!contraseñ.equals(confi)){
            Toast.makeText(this, "Las contraseñas deben coincidir", Toast.LENGTH_SHORT).show();
        }
        else{
            a = true;
        }
        return a;
    }

    private void guardarDatos(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        persona per = new persona(nomb, corre, telefon, contraseñ);
        databaseReference.setValue(per);
    }
    public void inicializar(){
        nombre = findViewById(R.id.nombreReg);
        correo = findViewById(R.id.correoReg);
        telefono = findViewById(R.id.celularReg);
        contraseña = findViewById(R.id.contraReg);
        confir = findViewById(R.id.confirReg);
        registro = findViewById(R.id.complReg);
    }
    public void ingresoLogin(View view){
        Intent intent = new Intent( registrarme.this, MainActivity.class );
        startActivity(intent);
    }
}
