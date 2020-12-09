package com.cabrera.jorge.examfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPokemos = findViewById(R.id.btnPokemos);
        Button btnCapturar = findViewById(R.id.btnCapturar);


        btnPokemos.setOnClickListener(view -> {
            Log.i("My_APP", "Abrir Registrar Pokemones");

            Intent intent = new Intent(MainActivity.this, MostrarPokemones.class);

            MainActivity.this.startActivity(intent);
        });


        btnCapturar.setOnClickListener(view -> {
            Log.i("My_APP", "Abrir Registrar Pokemones");

            Intent intent = new Intent(MainActivity.this, RegistrarPokemones.class);

            MainActivity.this.startActivity(intent);
        });


    }
}
