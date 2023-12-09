package com.example.calendario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class lista extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        Intent i=getIntent();
        String texto=i.getStringExtra("Nombre");
        String texto2=i.getStringExtra("Hora");
        String texto3=i.getStringExtra("Minuto");
        String texto4=i.getStringExtra("Fecha");
        TextView textView=findViewById(R.id.textView2);
        textView.setText(texto+"\n"+texto2+":"+texto3+"\n"+texto4);



    }


}