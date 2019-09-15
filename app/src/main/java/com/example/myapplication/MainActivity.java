package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.classes.PokemonAdapter;
import com.example.myapplication.classes.PokemonFetcher;

public class MainActivity extends AppCompatActivity {
    private int offset = 0;
    private int limit = 20;
    private PokemonFetcher pokemonFetcher;
    private PokemonAdapter pokemonAdapter;
    private TextView pokemonPosition;
    private RecyclerView pokemonFeed;
    private Button pokemonFetchNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize variables
        pokemonFetcher = new PokemonFetcher(this);
        pokemonAdapter = new PokemonAdapter();
        pokemonPosition = findViewById(R.id.pokemon_position_text);
        pokemonFeed = findViewById(R.id.pokemon_feed);
        pokemonFetchNext = findViewById(R.id.pokemon_fetch_next);

        // Set the adapter & layout manager to the Recycler View
        pokemonFeed.setAdapter(pokemonAdapter);
        pokemonFeed.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Add the Adapter (implements Observer) as an observer for the Pokemon Fetcher (Extends Observable)
        // This results in the Adapter being notified and calling its update() method when notifyObservers()
        // is triggered on the Fetcher.
        pokemonFetcher.addObserver(pokemonAdapter);

        pokemonPosition.setText("FROM " + (offset + 1) + " TO " + (limit + offset));
        // Run the initial fetch. This will notify the adapter once it's done.
        pokemonFetcher.fetch(offset, limit);
        pokemonFetchNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set the new offset to search
                offset += limit;
                pokemonPosition.setText("FROM " + (offset + 1) + " TO " + (limit + offset));
                pokemonFeed.smoothScrollToPosition(0);
                // Run the fetch for the next set of pokemons based on the new offset & limit
                pokemonFetcher.fetch(offset, limit);
            }
        });
    }
}
