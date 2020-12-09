package com.cabrera.jorge.examfinal;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.cabrera.jorge.examfinal.entities.Pokemones;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

public class UbicacionPokemon extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Double latitud;
    private Double longitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion_pokemon);

        Intent intent = getIntent();
        String pokemon = intent.getStringExtra("Pokemon");
        Pokemones pokemon1 = new Gson().fromJson(pokemon, Pokemones.class);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        latitud = pokemon1.getLatitude().doubleValue();
        longitud = pokemon1.getLongitude().doubleValue();


        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng pokemon = new LatLng(latitud, longitud);
        mMap.addMarker(new MarkerOptions().position(pokemon).title("Pokemon"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pokemon,28f));
    }
}