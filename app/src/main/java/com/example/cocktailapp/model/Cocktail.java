package com.example.cocktailapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;
// notre class Cocktail

public class Cocktail {
    //une annotaion indique que cet attribut doit etre serialiser avec le meme nom dans l'obket JSON

    @SerializedName("idDrink")
    public int id;
    @SerializedName("strDrink")
    public String name;
    @SerializedName("strDrinkThumb")
    public String Image;
    @SerializedName("strInstructions")
    public String descreption;





}
