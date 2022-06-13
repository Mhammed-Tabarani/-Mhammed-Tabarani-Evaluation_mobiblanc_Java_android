package com.example.cocktailapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText nom,prenom,email,password;
    Button signup;
    TextView signin;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nom=(EditText) findViewById(R.id.nom);
        prenom=(EditText) findViewById(R.id.prenom);
        email=(EditText) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);
        signup=(Button) findViewById(R.id.btnsignup);
        signin=findViewById(R.id.Logintextview);
        DB=new DBHelper(this);

        signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                String nomm= nom.getText().toString();
                String prenomm= prenom.getText().toString();
                String emaill=email.getText().toString();
                String pass=password.getText().toString();

                if (nomm.equals("") || pass.equals("") || prenomm.equals("") || emaill.equals(""))
                    Toast.makeText(MainActivity.this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkuser=DB.checkuseremail(emaill);
                    if(checkuser==false){
                        Boolean insert = DB.inserData(nomm,prenomm,emaill,pass);
                        if(insert==true)
                        {
                            Toast.makeText(MainActivity.this,"Enregistré avec succcès ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),BoissonActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(MainActivity.this,"Echec de l'inscription",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        signin.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, LoginActivity.class)));


    }
}