package com.example.cocktailapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    Button btnlogin;
    DBHelper DB;
    TextView signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        btnlogin=(Button) findViewById(R.id.btnsignin);
        signup=findViewById(R.id.Logintextview);
        DB=new DBHelper(this);
        btnlogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String user=email.getText().toString();
                String pass=password.getText().toString();
                if(user.equals("")||pass.equals(""))
                    Toast.makeText(LoginActivity.this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
                     else{
                        Boolean checkuserpass = DB.checkusernamepassword(user, pass);
                        if(checkuserpass==true){
                            Toast.makeText(LoginActivity.this, "Sign in successfull", Toast.LENGTH_SHORT).show();
                            Intent intent  = new Intent(getApplicationContext(), BoissonActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        signup.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, MainActivity.class)));
    }
}
