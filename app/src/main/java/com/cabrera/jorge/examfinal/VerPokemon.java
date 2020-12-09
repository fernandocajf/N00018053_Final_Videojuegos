package com.cabrera.jorge.examfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cabrera.jorge.examfinal.entities.Pokemones;
import com.google.gson.Gson;

public class VerPokemon extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_pokemon);

        ImageView imgView = findViewById(R.id.imgView);
        TextView txtNombre = findViewById(R.id.txtNombre);
        TextView txtTipo = findViewById(R.id.txtTipo);

        Button btnUbicacion = findViewById(R.id.btnUbicacion);

        Intent intent = getIntent();
        String pokemon = intent.getStringExtra("Pokemon");

        Pokemones pokemon1 = new Gson().fromJson(pokemon, Pokemones.class);

        String path = "https://upn.lumenes.tk";


        String urlImg = path + pokemon1.getImagen();

        txtNombre.setText(pokemon1.getNombre());
        txtTipo.setText(pokemon1.getTipo());
        Glide.with(this).load(urlImg).into(imgView);

        btnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerPokemon.this,UbicacionPokemon.class);
                intent.putExtra("Pokemon",new Gson().toJson(pokemon1));
                startActivity(intent);
            }
        });

    }
}