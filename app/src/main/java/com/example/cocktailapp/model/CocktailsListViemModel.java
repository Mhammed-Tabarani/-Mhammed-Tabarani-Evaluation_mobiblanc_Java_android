package com.example.cocktailapp.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cocktailapp.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
// creer un model pour la listview une classe qui herite de arrayadapter de type generique Cocktail

public class CocktailsListViemModel extends ArrayAdapter<Cocktail> {
    private int resource;
    //Constructeur qui prend 3 parametres  le context de l'activité et le resource est le layout cocktail list viewlayout
    // la liste des cocktail qu'on veut afficher
    public CocktailsListViemModel(@NonNull Context context, int resource, List<Cocktail> data) {
        super(context, resource,data);
        this.resource=resource;
    }

    @NonNull
    @Override
    // redefinire la methode getview qui retourne le view a fficher dans liste item

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // declarer un objet de type View
        View listViewItem=convertView;
        //test si il est null
        if(listViewItem==null){
            // si il est null ill fut le creer
            //instancieer un layout avec layoutinflater
            listViewItem= LayoutInflater.from(getContext()).inflate(resource,parent,false);
        }
        //recuperer nos champs de layout
        ImageView imageViewCocktail=listViewItem.findViewById(R.id.imageViewCocktail);
        TextView textViewCocktailName=listViewItem.findViewById(R.id.textViewCocktailName);
        TextView textViewDescription=listViewItem.findViewById(R.id.textViewDescription);
        //position c'est l'index de l'element de la liste a afficher
        //afficher le nom de cocktail
        textViewCocktailName.setText(getItem(position).name);
        //afficher la description et tester s'elle superieur a 100 on affiche juste 50 caractere
        if(getItem(position).descreption.length()>100){
            textViewDescription.setText(getItem(position).descreption.substring(0,100)+"...");
        }
        //sinon si la taille de description est inferieur a 100 caractere on l'affiche
        else{
            textViewDescription.setText(getItem(position).descreption);}
        //affichage de l'image de cocktail
        try {
            //URL de l'image
            URL url =new URL(getItem(position).Image);
            Bitmap bitmap= BitmapFactory.decodeStream(url.openStream());
            imageViewCocktail.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listViewItem;
    }
}
