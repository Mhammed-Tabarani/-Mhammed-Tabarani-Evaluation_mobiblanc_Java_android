package com.example.cocktailapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.cocktailapp.model.Cocktail;
import com.example.cocktailapp.model.CocktailDbResponse;
import com.example.cocktailapp.model.CocktailsListViemModel;
import com.example.cocktailapp.service.CocktailDbServiceAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BoissonActivity extends AppCompatActivity {
    //Notre model
    List<Cocktail> data=new ArrayList<>();
    public static final String COCKTAIL_ID_PARAM="cocktail.id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boisson);
        //donner la permission pour envoyer une requete dans le reseaux pour recuperer l'image a partir de son url
        StrictMode.ThreadPolicy policy =new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //recuperer nos chapms
        EditText editTextQuery =findViewById(R.id.editTextquery);
        Button buttonSearch =findViewById(R.id.buttonSearch);
        ListView listViewCocktails=findViewById(R.id.listviewCocktails);

       // ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,data);
        //notre adaptateur pour utiliser la listview
        CocktailsListViemModel listViemModel =new CocktailsListViemModel(this,R.layout.cocktail_listview_layout,data);
        listViewCocktails.setAdapter(listViemModel);
        Retrofit retrofit=new Retrofit.Builder()
                // specifier le domaine
                .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
                // utiliser la librarie Gson pour la deserialisation
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //declarer un objet de type CocktailDbServiceAPI car c'est lui qui va nous permettre d'acceder a les methode
        CocktailDbServiceAPI cocktailDbServiceAPI=retrofit.create(CocktailDbServiceAPI.class);
        //recuperer les cocktail dans le nom est similaire a margarita
        Call<CocktailDbResponse> callCocktails = cocktailDbServiceAPI.searchCocktails("Margarita");
        //executer l'appel avec un appel asynchrone avec enqueue
        callCocktails.enqueue(new Callback<CocktailDbResponse>() {
            @Override
            public void onResponse(Call<CocktailDbResponse> call, Response<CocktailDbResponse> response) {
                // test si il a pas de corp dans la reponse
                if(!response.isSuccessful()){
                    //afficher la valeur de code d'erreur
                    Log.i("indo",String.valueOf(response.code()));
                    return;
                }
                //sinon recuperer l'objet CocktailDbResponse
                CocktailDbResponse cocktailDbResponse=response.body();
                //supprimer les donnees existants
                data.clear();
                //parcourir la liste des cocktails et l'ajouter dans le model
                for(Cocktail cocktail:cocktailDbResponse.cocktails){
                    data.add(cocktail);
                }
                //les donnéé ont changer
                listViemModel.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CocktailDbResponse> call, Throwable t) {
                Log.e("error","Error");
            }
        });

        // lanceer cette methode a chaque fois l'utilisateur click sur le button search
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //recuperer le nom de cocktail donné par l'utilisateur
                String query=editTextQuery.getText().toString();
                //declarer un objet de type CocktailDbServiceAPI car c'est lui qui va nous permettre d'acceder a les methode
                CocktailDbServiceAPI cocktailDbServiceAPI=retrofit.create(CocktailDbServiceAPI.class);
                //recuperer les cocktail dans le nom est similaire a celui qui est donné par l'utilisateur
                Call<CocktailDbResponse> callCocktails = cocktailDbServiceAPI.searchCocktails(query);
                //executer l'appel avec un appel asynchrone avec enqueue
                callCocktails.enqueue(new Callback<CocktailDbResponse>() {
                    @Override
                    public void onResponse(Call<CocktailDbResponse> call, Response<CocktailDbResponse> response) {
                        // test si il a pas de corp dans la reponse
                        if(!response.isSuccessful()){
                            Log.i("indo",String.valueOf(response.code()));
                            return;
                        }
                        //sinon recuperer l'objet CocktailDbResponse
                        CocktailDbResponse cocktailDbResponse=response.body();
                        //supprimer les donnees existants
                        data.clear();
                        //parcourir la liste des cocktails et l'ajouter dans le model
                        for(Cocktail cocktail:cocktailDbResponse.cocktails){
                            data.add(cocktail);
                        }
                        listViemModel.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<CocktailDbResponse> call, Throwable t) {
                        Log.e("error","Error");
                    }
                });
            }

        });
        //a chaque click de l'utilisateur sur un cocktail en transmit le id de dernier a l'activiter cocktail detail pour afficher ces details
        listViewCocktails.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long i) {
                int id=data.get(position).id;
                Intent intent=new Intent(getApplicationContext(),CocktailDetails.class);
                intent.putExtra(COCKTAIL_ID_PARAM,id);
                startActivity(intent);
            }
        });

    }
}