package com.example.myapplication.classes;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Observable;

public class PokemonFetcher extends Observable {
        private String TAG = "PokemonFetcher";
        private ArrayList<Pokemon> pokemons;
        private RequestQueue queue;

        public PokemonFetcher(Context ctx) {
            this.pokemons = new ArrayList<>();
            this.queue = Volley.newRequestQueue(ctx);
        }

        public ArrayList<Pokemon> getPokemons() {
                return this.pokemons;
        }

        public void setPokemons(ArrayList<Pokemon> pokemons) {
                this.pokemons = pokemons;
                setChanged();
                notifyObservers(pokemons);
        }

        public RequestQueue getQueue() {
                return this.queue;
        }

        public void fetch(int offset, int limit) {
                Log.wtf(TAG, "Fetching...");
                RequestQueue queue = this.getQueue();
                String url = "https://pokeapi.co/api/v2/pokemon?offset=" + offset + "&limit=" + limit;
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                                Log.wtf(TAG, "Fetch Success");
                                try {
                                        ArrayList<Pokemon> results = new ArrayList<Pokemon>();
                                        JSONArray data = response.getJSONArray("results");
                                        for (int i = 0; i < data.length(); i++) {
                                                JSONObject obj = data.getJSONObject(i);
                                                Pokemon p = new Pokemon(obj.getString("name"));
                                                results.add(p);
                                        }
                                        Log.wtf(TAG, "Notifying...");
                                        setPokemons(results);
                                } catch (JSONException e) {
                                        e.printStackTrace();
                                }

                        }
                }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                                Log.wtf("TAG", error);
                        }
                });

                queue.add(request);
        }
}
