package com.example.apiconnectionapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Definisemo varijable u koju cemo spremiti RecyclerView I ProgressBar iz activity_main.xml (tamo smo im dali id-jeve pomocu kojih cemo ih nanisaniti isto js i html)
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    // Ovdje cemo spremiti Postove koje budemo fetchali (Bice Lista objekata Klase Post)
    private List<Post> postList;

    // Deklarisemo PostAdapter
    private PostAdapter adapter;

    // URL S kojeg Fetchamo podatke
    private static final String API_URL = "https://jsonplaceholder.typicode.com/posts";

    @Override
    // Ova funkcija se pokrece kad god se screen kreira
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // OVO JE BITNA LINIJA, koja loada visualni layout iz activity_main.xml (nalazi se u res/layout) i prikazuje na screen-u
        setContentView(R.layout.activity_main);
        // Ovdje nalazimo po id-ju elemente iz activity_main.xml i spremamo u varijable
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        // Definisemo postList kao ArrayList (Ovo moram skontat u cemu je fazon)
        postList = new ArrayList<>();

        // RecyclerView treba ovaj LayoutManager, LinearLayoutManager kaze da zelimo vertikalnu listu (MORAAAA SE SETUPOVATI)
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new PostAdapter(postList);

        recyclerView.setAdapter(adapter);

        // Pozivamo fetchPosts() metodu (bitno da se ona definise vam ove onCreate metode), jer me izjebala
        fetchPosts();




        // OVO NE ZNAM STA JE
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void fetchPosts() {
        // Kad se Pozove funkciija setamo spinner da bude visible (Tamo u activity_main.xml smo ga stavili na "gone") Isto u JS-u display:none
        progressBar.setVisibility(View.VISIBLE);

        // Nisam siguran sta radi, al izgleda kao da pravi que za network request-e
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Kreira StringRequest (Subklasa Klase Request iz import com.android.volley.Request;)
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                API_URL,
                // Ovo ispod shvati kao callback za Successful response
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Kod Ispod Runna Kad je uspjesan Response

                        // Sakrijemo Spinner
                        progressBar.setVisibility(View.GONE);
                        //Prikazujemo RecyclerView
                        recyclerView.setVisibility(View.VISIBLE);

                        // Koristimo Gson da parsamo JSON koji dobijemo kao response
                        Gson gson = new Gson();
                        // Kazemo Gson-u da je response ArrayLista objekata Post Klase
                        Type postListType = new TypeToken<ArrayList<Post>>(){}.getType();
                        // Uzima JSON (String) i automatski pravi listu objekata Post klase
                        List<Post> posts = gson.fromJson(response, postListType);


                        int startPosition = postList.size();
                        // Dodaje postove u post list
                        postList.addAll(posts);

                        // Loga (Otvorimo Logcat i gore ukucamo MainActivity)
                        Log.d("MainActivity", "Data fetched! " + postList.size() + " posts loaded.");
                        Log.d("MainActivity", String.valueOf(postList.get(0)));

                        // Dobijam neki warning - haman da je brute force neki
                        // adapter.notifyDataSetChanged();


                        //Bolji Metod
                        adapter.notifyItemRangeInserted(startPosition, posts.size());


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Kod Ispod runna ako request faila (Sakrije se progressBar i loga error)
                        progressBar.setVisibility(View.GONE);
                        Log.e("MainActivity", "Error fetching data: " + error.getMessage());
                    }
                }
        );

        // Da bu runnali request moramo ga dodat ui ovaj queue
        requestQueue.add(stringRequest);
    }
}