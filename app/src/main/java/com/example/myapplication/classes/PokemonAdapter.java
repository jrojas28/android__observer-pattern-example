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

// The PokemonAdapter implements the Observer interface
// This allows the adapter to be added to an observable instance via addObserver
// And to trigger the update() method when it has been notified of a change within the observable instance
public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> implements Observer {
    private String TAG = "PokemonAdapter";
    private ArrayList<Pokemon> pokemons;

    public PokemonAdapter() {
        pokemons = new ArrayList<>();
    }

    @NonNull
    @Override
    public PokemonAdapter.PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_pokemon, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonAdapter.PokemonViewHolder holder, int position) {
        final Pokemon pokemon = pokemons.get(position);
        String name = pokemon.getName();
        String capitalizedName = name.substring(0, 1).toUpperCase() + name.substring(1);
        holder.getPokemonName().setText(capitalizedName);
    }

    @Override
    public int getItemCount() {
        return this.pokemons.size();
    }

    @Override
    public void update(Observable o, Object arg) {
        PokemonFetcher fetcher = (PokemonFetcher) o;
        pokemons = fetcher.getPokemons();
        notifyDataSetChanged();
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder {
        private TextView pokemonName;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            pokemonName = itemView.findViewById(R.id.pokemon_name);
        }

        public TextView getPokemonName() {
            return pokemonName;
        }
    }
}