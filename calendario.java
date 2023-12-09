package com.example.calendario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;

import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

public class calendario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);
    }
////metodos de guardado
    //preferencias de aplicacion
    private SharedPreferences obtenerSharedPreferences() {
        return getSharedPreferences("MisPreferencias", MODE_PRIVATE);
    }
//contador de eventos, cada que se interactua con la app
    private int obtenerContadorEventos() {
        SharedPreferences sharedPreferences = obtenerSharedPreferences();
        return sharedPreferences.getInt("ContadorEventos", 0);
    }
///guardado del contador de eventos para no sobreescribir
    private void guardarContadorEventos(int nuevoContador) {
        SharedPreferences sharedPreferences = obtenerSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("ContadorEventos", nuevoContador);
        editor.apply();
    }
////metodo para agregar una actividad
    public void agregar(View view) {
        EditText editText = findViewById(R.id.editTextText);
        TimePicker tp = findViewById(R.id.tp);
        EditText edd = findViewById(R.id.edd);

        String texto = editText.getText().toString();
        String texto0=edd.getText().toString();
        int texto2 = tp.getHour();
        int texto3 = tp.getMinute();
        String tphora = Integer.toString(texto2);
        String tpminuto = Integer.toString(texto3);

        // Recupera el contador actual de eventos almacenado
        int contadorEventos = obtenerContadorEventos();

        // Incrementa el contador
        contadorEventos++;

        // Guarda el nuevo contador en SharedPreferences
        guardarContadorEventos(contadorEventos);

        // Usa el contador como parte de las claves para evitar la sobrescritura
        SharedPreferences.Editor editor = obtenerSharedPreferences().edit();
        editor.putString("Nombre" + contadorEventos, texto);
        editor.putString("Hora" + contadorEventos, tphora);
        editor.putString("Minuto" + contadorEventos, tpminuto);
        editor.putString("Fecha" + contadorEventos, texto0);

        // Aplica los cambios
        editor.apply();
/////inicia una actividad
        Intent i = new Intent(this, lista.class);
        i.putExtra("Nombre", texto);
        i.putExtra("Hora", tphora);
        i.putExtra("Minuto", tpminuto);
        i.putExtra("Fecha", texto0);
        startActivity(i);
    }
///metodo del boton de busqueda
    public void buscar(View view) {
        // Obtén el nombre que el usuario desea buscar
        EditText editTextBuscar = findViewById(R.id.editTextBuscar);
        String nombreABuscar = editTextBuscar.getText().toString();

        // Recupera el contador actual de eventos almacenado
        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        int contadorEventos = sharedPreferences.getInt("ContadorEventos", 0);

        StringBuilder resultados = new StringBuilder();

        // Realiza la búsqueda en los eventos usando el contador
        for (int i = 1; i <= contadorEventos; i++) {
            String nombreEvento = sharedPreferences.getString("Nombre" + i, "");

            // Verifica si el nombre coincide
            if (!nombreEvento.isEmpty() && nombreEvento.equals(nombreABuscar)) {
                // Concatena los detalles del evento al StringBuilder
                String horaEvento = sharedPreferences.getString("Hora" + i, "");
                String minutoEvento = sharedPreferences.getString("Minuto" + i, "");
                String fechaEvento = sharedPreferences.getString("Fecha" + i, "");

                resultados.append("Nombre: ").append(nombreEvento)
                        .append("\nHora: ").append(horaEvento).append(":").append(minutoEvento)
                        .append("\nFecha: ").append(fechaEvento).append("\n\n");
            }
        }

        // Muestra los resultados usando una nueva actividad
        if (resultados.length() > 0) {
            Intent ij = new Intent(this, todasact.class);
            ij.putExtra("resultados", resultados.toString());
            startActivity(ij);
        } else {
            Toast.makeText(this, "No se encontraron coincidencias", Toast.LENGTH_SHORT).show();
        }
    }
/////boton de ver actividades
    public void ver(View view) {
        // Recupera todas las actividades almacenadas
        List<String> todasLasActividades = verTodasAct();

        // Construye un mensaje con todas las actividades
        StringBuilder mensaje = new StringBuilder("Actividades ingresadas:\n");
        for (String actividad : todasLasActividades) {
            mensaje.append(actividad).append("\n\n");
        }

        // Inicia una nueva actividad para mostrar todas las actividades
        Intent intent = new Intent(this, TodasActividades.class);
        intent.putExtra("resultados", mensaje.toString());
        startActivity(intent);
    }
///lógica para ver todas las actividades en una nueva actividad de antroid studio
    private List<String> verTodasAct() {
        List<String> actividades = new ArrayList<>();

        // Recupera el contador actual de eventos almacenado
        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        int contadorEventos = sharedPreferences.getInt("ContadorEventos", 0);

        // Recorre todos los eventos y agrega sus detalles a la lista
        for (int i = 1; i <= contadorEventos; i++) {
            String nombreEvento = sharedPreferences.getString("Nombre" + i, "");
            String horaEvento = sharedPreferences.getString("Hora" + i, "");
            String minutoEvento = sharedPreferences.getString("Minuto" + i, "");
            String fechaEvento = sharedPreferences.getString("Fecha" + i, "");

            String detalleEvento = "Nombre: " + nombreEvento + "\nHora: " + horaEvento + ":" + minutoEvento + "\nFecha: " + fechaEvento;
            actividades.add(detalleEvento);
        }

        return actividades;
    }

    // ...
    public void eliminartodo(View view) {
        // Elimina todas las actividades almacenadas
        SharedPreferences.Editor editor = obtenerSharedPreferences().edit();
        editor.clear(); // Elimina todas las preferencias
        editor.apply();

        // Reinicia el contador de eventos a cero
        guardarContadorEventos(0);

        Toast.makeText(this, "Todas las actividades han sido eliminadas", Toast.LENGTH_SHORT).show();
    }

    public void eliminar(View view) {
        // Obtén el nombre que el usuario desea eliminar
        EditText editTextEliminar = findViewById(R.id.editTextEliminar);
        String nombreAEliminar = editTextEliminar.getText().toString();

        // Verifica que se haya proporcionado un nombre
        if (!nombreAEliminar.isEmpty()) {
            // Llama a la función eliminarActividad para eliminar la actividad por nombre
            eliminarActividad(nombreAEliminar);
            Toast.makeText(this, "Actividad eliminada: " + nombreAEliminar, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Por favor, ingresa el nombre de la actividad a eliminar", Toast.LENGTH_SHORT).show();
        }
    }
    public void eliminarActividad(String nombreActividad) {
        // Recupera el contador actual de eventos almacenado
        int contadorEventos = obtenerContadorEventos();

        // Recorre todos los eventos y busca la actividad por nombre
        for (int i = 1; i <= contadorEventos; i++) {
            String nombreEvento = obtenerSharedPreferences().getString("Nombre" + i, "");

            if (nombreEvento.equals(nombreActividad)) {
                // Elimina los detalles de la actividad
                SharedPreferences.Editor editor = obtenerSharedPreferences().edit();
                editor.remove("Nombre" + i);
                editor.remove("Hora" + i);
                editor.remove("Minuto" + i);
                editor.remove("Fecha" + i);

                // Decrementa el contador
                contadorEventos--;

                // Guarda el nuevo contador en SharedPreferences
                guardarContadorEventos(contadorEventos);

                // Aplica los cambios
                editor.apply();
                break; // Una vez eliminada, sal del bucle
            }
        }
    }



}


