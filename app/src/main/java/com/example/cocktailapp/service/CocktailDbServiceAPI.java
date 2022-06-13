package com.example.cocktailapp.service;

import com.example.cocktailapp.model.CocktailDbResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

// dans cette interface on declare touts ce qu'on veut chercher

// ces methode sont fournit par retrofit qui nous permet de consommer les api ,
// On a ajouter les dependences de retrofit dans build.gradle
public interface CocktailDbServiceAPI {
    // cette methode nour retourne une liste des cocktail dont le nom est similaire a celui qu'on a donner en parametre
    // et elle le stock dand l'objet CocktailDbResponse
    @GET("search.php")
    public Call<CocktailDbResponse> searchCocktails(@Query("s") String query);
    // cette methode nour retourne une liste qui contient un seul cocktail dont le id egale a celui qu'on a donner en parametre
    // et elle le stock dand l'objet CocktailDbResponse
    @GET("lookup.php")
    public Call<CocktailDbResponse> cocktailDetail(@Query("i") int id);
}
