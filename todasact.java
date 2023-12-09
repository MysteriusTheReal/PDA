package com.example.calendario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class todasact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todasact);
        Intent ij=getIntent();
        String texto=ij.getStringExtra("resultados");
        TextView textView=findViewById(R.id.textView3);
        textView.setText(texto);

    }

}