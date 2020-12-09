package com.cabrera.jorge.examfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cabrera.jorge.examfinal.adapters.PokemonAdapter;
import com.cabrera.jorge.examfinal.entities.Pokemones;
import com.cabrera.jorge.examfinal.services.PokemonService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MostrarPokemones extends AppCompatActivity {

    private Activity activity = this;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_pokemones);

        context = this.getApplicationContext();

        RecyclerView rvPokemones = findViewById(R.id.rvPokemones);


        rvPokemones.setHasFixedSize(true);
        rvPokemones.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://upn.lumenes.tk/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PokemonService service = retrofit.create(PokemonService.class);
        Call<List<Pokemones>> call = service.getByQuery("abc");


        call.enqueue(new Callback<List<Pokemones>>() {

            @Override
            public void onResponse(Call<List<Pokemones>> call, Response<List<Pokemones>> response) {
                if (response.code() == 200){
                    List<Pokemones> pokemons = response.body();
                    rvPokemones.setAdapter(new PokemonAdapter(pokemons, context, activity));
                }else {
                    Log.i("MY_APP" , "Falló");
                }
            }
            @Override
            public void onFailure(Call<List<Pokemones>> call, Throwable t) {
                Log.i("MY_APP" , "Falló");
            }
        });

    }

}