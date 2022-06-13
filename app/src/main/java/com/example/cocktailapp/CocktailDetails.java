package com.example.cocktailapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cocktailapp.model.Cocktail;
import com.example.cocktailapp.model.CocktailDbResponse;
import com.example.cocktailapp.service.CocktailDbServiceAPI;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CocktailDetails extends AppCompatActivity {
    //List<Cocktail> data=new ArrayList<>();
    Cocktail data=new Cocktail();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail_details);
        //donner la permission pour envoyer une requete dans le reseaux pour recuperer l'image a partir de son url
        StrictMode.ThreadPolicy policy =new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Intent intent=getIntent();
        //recuperer ID de cocktail qui est transmit dans intent
        int id=intent.getIntExtra(BoissonActivity.COCKTAIL_ID_PARAM,0);
        setTitle("Cocktail Detail");
        //recuperer nos chapms
        TextView textViewCocktailName=findViewById(R.id.textViewCocktailNameD);
        TextView textViewCocktailDetails=findViewById(R.id.textViewDescriptionD);
        ImageView imageViewCocktail=findViewById(R.id.imageViewCocktailD);
        textViewCocktailName.setText(String.valueOf(id));

        Retrofit retrofit=new Retrofit.Builder()
                // specifier le domaine
                .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
                // utiliser la librarie Gson pour la deserialisation
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //declarer un objet de type CocktailDbServiceAPI car c'est lui qui va nous permettre d'acceder a les methode
        CocktailDbServiceAPI cocktailDbServiceAPI=retrofit.create(CocktailDbServiceAPI.class);
        //recuperer une liste de cocktails qui contien un seul cocktail dans le id est similaire a celui qui est transmis dans le intent
        Call<CocktailDbResponse> callCocktail = cocktailDbServiceAPI.cocktailDetail(id);
        //executer l'appel avec un appel asynchrone avec enqueue
        callCocktail.enqueue(new Callback<CocktailDbResponse>() {
            @Override
            public void onResponse(Call<CocktailDbResponse> call, Response<CocktailDbResponse> response) {
                // test si il a pas de corp dans la reponse
                if(!response.isSuccessful()){
                    Log.i("indo",String.valueOf(response.code()));
                    return;
                }
                //sinon recuperer l'objet CocktailDbResponse
                CocktailDbResponse cocktailDbResponse=response.body();
                //parcourir la liste des cocktails qui contient un seul objet et stocker dans data(un objet de type cocktail
                for(Cocktail cocktail:cocktailDbResponse.cocktails){
                    data=cocktail;
                }
                //afficher le nom de cocktail
                textViewCocktailName.setText(data.name);
                //afficher la discreption de cocktail
                textViewCocktailDetails.setText(data.descreption);
                //afficher l'image de cocktail
                try {
                    URL url =new URL(data.Image);
                    Bitmap bitmap= BitmapFactory.decodeStream(url.openStream());
                    imageViewCocktail.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CocktailDbResponse> call, Throwable t) {
                Log.e("error","Error");
            }
        });

    }
}