package com.cabrera.jorge.examfinal.services;

import com.cabrera.jorge.examfinal.entities.Pokemones;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PokemonService {
    @GET()
    Call<List<Pokemones>> getPokemones();

    @POST("pokemons/N00018053/crear")
    Call<Pokemones> createPokemones(@Body Pokemones pokemon);

    @GET("pokemons/N00018053/")
    Call<List<Pokemones>> getByQuery(@Query("query") String query);
}
