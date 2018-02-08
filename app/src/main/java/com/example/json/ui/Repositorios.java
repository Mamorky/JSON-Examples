package com.example.json.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.json.R;
import com.example.json.adapter.RepoAdapter;
import com.example.json.network.ApiAdapter;
import com.example.json.pojo.Repo;
import com.example.json.network.GitHubClient;
import com.example.json.utils.RecyclerTouchListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repositorios extends AppCompatActivity implements View.OnClickListener{
    EditText nombreUsuario;
    Button botonDescarga;
    RecyclerView rvRepos;
    private RepoAdapter adapter;
    private ArrayList<Repo> repos;

    GitHubClient apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositorios);
        nombreUsuario = (EditText) findViewById(R.id.edtUserRepo);
        botonDescarga = (Button) findViewById(R.id.btnDescargaRepo);
        botonDescarga.setOnClickListener(this);
        rvRepos = (RecyclerView) findViewById(R.id.rcv_portada);
        adapter = new RepoAdapter();
        rvRepos.setAdapter(adapter);
        rvRepos.setLayoutManager(new LinearLayoutManager(this));

        //manage click
        rvRepos.addOnItemTouchListener(new RecyclerTouchListener(this,rvRepos, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
                Uri uri = Uri.parse((String) repos.get(position).getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                if (intent.resolveActivity(getPackageManager()) != null)
                    startActivity(intent);
                else
                    Toast.makeText(getApplicationContext(), "No hay un navegador",
                            Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(Repositorios.this, "Long press on position :"+position,
                        Toast.LENGTH_LONG).show();
            }
        }));
    }

    @Override
    public void onClick(View v) {
        if(v == botonDescarga){
            String username = nombreUsuario.getText().toString();
            if(username.isEmpty())
                Toast.makeText(this,"Debes introducir un nombre",Toast.LENGTH_SHORT).show();
            else{
                // Poner cuadro progreso
                retrofit2.Call<ArrayList<Repo>> call = ApiAdapter.getApiService().listRepos(username);
                call.enqueue(new Callback<ArrayList<Repo>>() {
                    @Override
                    public void onResponse(retrofit2.Call<ArrayList<Repo>> call, Response<ArrayList<Repo>> response) {
                        if(response.isSuccessful()){
                            repos = response.body();
                            adapter.setRepos(repos);
                            Toast.makeText(Repositorios.this,"Repositorios actualizados",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            StringBuilder message = new StringBuilder();
                            message.append("Error en la descarga: "+response.code());
                            if(response.body() != null){
                                message.append(response.body());
                            }
                            if(response.errorBody() != null){
                                message.append(response.errorBody());
                            }

                            Toast.makeText(Repositorios.this,message.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<ArrayList<Repo>> call, Throwable t) {
                        if(t != null){
                            Toast.makeText(Repositorios.this,"Fallo en la comunicación: "+t.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }
}
