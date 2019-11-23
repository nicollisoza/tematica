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
import com.google.firebase.auth.FirebaseAuth;

public class recuperar_clave extends AppCompatActivity {

    private EditText correo;
    private Button enviarR;
    private String corre;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_clave);
        firebaseAuth = FirebaseAuth.getInstance();
        inicializar();
        enviarR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                corre = correo.getText().toString();
                if(!corre.isEmpty()){
                    enviarCorreo();
                }
            }
        });

    }

    private void enviarCorreo() {
        firebaseAuth.setLanguageCode("es");
        firebaseAuth.sendPasswordResetEmail(corre).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(recuperar_clave.this, "Ya se envió el correo", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(recuperar_clave.this, "Intente más tarde", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void inicializar(){
        correo = findViewById(R.id.correoRecu);
        enviarR = findViewById(R.id.enviarRecu);
    }
}
