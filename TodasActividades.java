package com.example.calendario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TodasActividades extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todas_actividades);
        Intent ij=getIntent();
        String texto=ij.getStringExtra("resultados");
        TextView textView=findViewById(R.id.textView6);
        textView.setText(texto);
    }
}