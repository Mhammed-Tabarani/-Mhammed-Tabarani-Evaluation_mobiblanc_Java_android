package com.example.cocktailapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CocktailDbResponse {
    // cette classe definie une liste de cocktail car l'api nous donne une list des drinks
    @SerializedName("drinks")
    public List<Cocktail> cocktails=new ArrayList<>();
}
