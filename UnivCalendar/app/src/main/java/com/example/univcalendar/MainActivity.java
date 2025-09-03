package com.example.univcalendar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.time.DayOfWeek;

//import pour le recyclerview
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.app.ActionBarDrawerToggle;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private RecyclerView recyclerView;
    private CoursAdapter adapter;
    private LocalDate date = LocalDate.now();

    private Button buttonGauche;
    private Button buttonDroite;

    private List<Cours> coursList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
    
        
        //récupération du menu et initialisation du menu
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        // Activer le bouton hamburger
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Actions sur clic dans le menu
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                Toast.makeText(this, "Accueil", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_settings) {
                Toast.makeText(this, "Paramètres", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_about) {
                Toast.makeText(this, "À propos", Toast.LENGTH_SHORT).show();
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        //Récupération des buttons
        buttonDroite = findViewById(R.id.btnDroite);
        buttonGauche = findViewById(R.id.btnGauche);
        setOnclickButton();
        //on récupére le recyclerview
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/") // 127.0.0.1 ne fonctionne pas sur émulateur et sur téléphone 192.168.15.1:5000/
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EdtApi api = retrofit.create(EdtApi.class);

        // Appel de l'API
        Call<List<Cours>> call = api.getEdt();
        call.enqueue(new Callback<List<Cours>>() {
            @Override
            public void onResponse(Call<List<Cours>> call, Response<List<Cours>> response) {
                if (response.isSuccessful()) {
                    coursList = response.body();
                    // Ici : on rempli le RecyclerView avec les cours d'aujourd'hui
                    List<Cours> todayList = getToday(coursList);
                    adapter = new CoursAdapter(todayList);
                    recyclerView.setAdapter(adapter);


                    //Mettre les dates sur les boutons à jour

                    buttonDroite.setText(date.plusDays(1).toString());
                    buttonGauche.setText(date.minusDays(1).toString());

                }
            }
            @Override
            public void onFailure(Call<List<Cours>> call, Throwable t) {
                Log.d("erreur", "onFailure: ici");
                t.printStackTrace();
            }
        });
    }
    
    
    public List<Cours> getToday(List<Cours>coursList){

        String today = LocalDate.now().toString(); //

        List<Cours> todayCours = new ArrayList<Cours>();
        for(Cours cours: coursList){
            String dateOnly = cours.getStart().split("T")[0];
            if(dateOnly.equals(today)){
                cours.setColor();
                todayCours.add(cours);
            }
        }
        Collections.sort(todayCours);
        return todayCours;
    }


    public List<Cours> getDay(LocalDate date){

        List<Cours> day = new ArrayList<Cours>();
        for(Cours cours: coursList){
            String dateOnly = cours.getStart().split("T")[0];
            if(dateOnly.equals(date.toString())){
                cours.setColor();
                day.add(cours);
            }
        }
        Collections.sort(day);
        return day;
    }

    public void setOnclickButton(){

        buttonGauche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = date.minusDays(1);
                buttonDroite.setText(date.plusDays(1).toString());
                buttonGauche.setText(date.minusDays(1).toString());

                List<Cours> day =  getDay(date);
                adapter.updateData(day);
            }
        });
        buttonDroite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = date.plusDays(1);
                buttonDroite.setText(date.plusDays(1).toString());
                buttonGauche.setText(date.minusDays(1).toString());

                List<Cours> day =  getDay(date);
                adapter.updateData(day);
            }
        });
    }

    public boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }
}


