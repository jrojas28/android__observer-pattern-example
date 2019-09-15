package com.example.myapplication.classes;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> implements Observer {
    private String TAG = "PokemonAdapter";
    private ArrayList<Pokemon> pokemons;

    public PokemonAdapter() {
        this.pokemons = new ArrayList<>();
    }

    @NonNull
    @Override
    public PokemonAdapter.PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_pokemon, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonAdapter.PokemonViewHolder holder, int position) {
        final Pokemon pokemon = this.pokemons.get(position);
        holder.getPokemonName().setText(pokemon.getName());
    }

    @Override
    public int getItemCount() {
        return this.pokemons.size();
    }

    @Override
    public void update(Observable o, Object arg) {
        Log.wtf(TAG, "Updating..");
        PokemonFetcher fetcher = (PokemonFetcher) o;
        this.pokemons = fetcher.getPokemons();
        notifyDataSetChanged();
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder {
        private TextView pokemonName;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            this.pokemonName = itemView.findViewById(R.id.pokemon_name);
        }

        public TextView getPokemonName() {
            return this.pokemonName;
        }
    }
}