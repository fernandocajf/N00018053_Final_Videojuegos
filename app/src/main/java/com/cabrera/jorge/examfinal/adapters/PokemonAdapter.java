package com.cabrera.jorge.examfinal.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cabrera.jorge.examfinal.R;
import com.cabrera.jorge.examfinal.VerPokemon;
import com.cabrera.jorge.examfinal.entities.Pokemones;
import com.google.gson.Gson;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    private List<Pokemones> mData;
    private String pathImage = "https://upn.lumenes.tk";
    public Context context;
    private Activity activity;

    public PokemonAdapter(List<Pokemones> data, Context context, Activity activity){

        this.mData = data;
        this.context = context;
        this.activity = activity;
    }

    private @NonNull ViewGroup parent;

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon,parent,false);
        PokemonViewHolder pokemonViewHolder = new PokemonViewHolder(view);

        this.parent = parent;
        return pokemonViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {

        TextView textNombre = holder.itemView.findViewById(R.id.textNombre);
        TextView textTipo = holder.itemView.findViewById(R.id.textTipo);
        ImageView imgPokemon = holder.itemView.findViewById(R.id.imgPokemon);
        ImageButton ibtnDetalle = holder.itemView.findViewById(R.id.ibtnDetalle);


        textNombre.setText(mData.get(position).getNombre());
        textTipo.setText(mData.get(position).getTipo());

        String urlImg =  pathImage + mData.get(position).getImagen();
        Log.i("MY_APP", "sad" + urlImg);
        Glide.with(context).load(urlImg).into(imgPokemon);


        ibtnDetalle.setOnClickListener(view -> {
            Intent intent = new Intent(activity, VerPokemon.class);
            intent.putExtra("Pokemon",new Gson().toJson(mData.get(position)));
            activity.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {

        return mData.size();
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder{

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
