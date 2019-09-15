package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.classes.PokemonAdapter;
import com.example.myapplication.classes.PokemonFetcher;

public class MainActivity extends AppCompatActivity {
    private PokemonFetcher pokemonFetcher;
    private PokemonAdapter pokemonAdapter;
    private RecyclerView pokemonFeed;
    private TextView pokemonFeedEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.pokemonFetcher = new PokemonFetcher(this);
        this.pokemonAdapter = new PokemonAdapter();
        this.pokemonFeed = findViewById(R.id.pokemon_feed);
        this.pokemonFeedEmpty = findViewById(R.id.pokemon_feed_empty);

        this.pokemonFeed.setAdapter(this.pokemonAdapter);
        this.pokemonFeed.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        this.pokemonFetcher.addObserver(this.pokemonAdapter);

        this.pokemonFetcher.fetch(0, 151);
    }
}
