package com.example.silver23.sismen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GestionesActivity extends AppCompatActivity {

        Button bMenus, bEnferm, bNutri, bUsers, bIng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestiones);
        bMenus = (Button) findViewById(R.id.bMenus);
        bEnferm = (Button) findViewById(R.id.bEnferm);
        bNutri = (Button) findViewById(R.id.bNutri);
        bUsers = (Button) findViewById(R.id.bUsers);
        bIng = (Button) findViewById(R.id.bIng);





        bMenus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMenus = new Intent(GestionesActivity.this, MenusActivity.class);
                startActivity(intentMenus);

            }
        });



        bEnferm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentEnferm = new Intent(GestionesActivity.this, EnfermedadActivity.class);
                startActivity(intentEnferm);

            }
        });




        bNutri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentNutri = new Intent(GestionesActivity.this, NutricionistaActivity.class);
                startActivity(intentNutri);

            }
        });




        bUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentUsers = new Intent(GestionesActivity.this, PersonaActivity.class);
                startActivity(intentUsers);

            }
        });



        bIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentIngre = new Intent(GestionesActivity.this, IngredientesActivity.class);
                startActivity(intentIngre);

            }
        });




    }
}
