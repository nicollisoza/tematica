package com.example.fastrest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class asignatura extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignatura);
    }
    public void ingresoSubNotas(View view){
        Intent intent = new Intent(asignatura.this, subnota.class );
        startActivity(intent);
    }
}
