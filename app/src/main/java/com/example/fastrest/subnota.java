package com.example.fastrest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class subnota extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subnotas);
    }
    public void ingresoNotas(View view){
        Intent intent = new Intent(subnota.this, nota.class );
        startActivity(intent);
    }
}
