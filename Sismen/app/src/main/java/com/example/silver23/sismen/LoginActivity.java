package com.example.silver23.sismen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

public class LoginActivity extends AppCompatActivity {

     TextView etEmail;
     TextView etUser;
     TextView etPass;
    TextView tvPop;
    TextView tvReg;
    TextView tvAyuda;
    Button bIni;
    JSONArray ja;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = (EditText)findViewById(R.id.etEmail);
        etPass = (EditText)findViewById(R.id.etPass);
        tvAyuda = (TextView) findViewById(R.id.tvAyuda);

        tvAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAyuda = new Intent(LoginActivity.this, AyudaActivity.class);
                startActivity(intentAyuda);

            }
        });

        tvReg = (TextView) findViewById(R.id.tvRegistrar);
        tvReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentReg = new Intent(LoginActivity.this, RegistrarActivity.class);
                LoginActivity.this.startActivity(intentReg);

            }


        });




        bIni = (Button)findViewById(R.id.bIniciar);
        bIni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConsultaPass("http://192.168.111.1/consultarusuario.php?nombre="+etEmail.getText().toString());

            }
        });
    }

    private void ConsultaPass(String URL) {

        Log.i("url",""+URL);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest =  new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    ja = new JSONArray(response);
                    String contra = ja.getString(0);
                    if(contra.equals(etPass.getText().toString())){


                        Intent intentIniciar = new Intent(LoginActivity.this, PrincipalActivity.class);
                        startActivity(intentIniciar);

                    }else{
                        Toast.makeText(getApplicationContext(),"verifique su contraseña",Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(getApplicationContext(),"El usuario no existe o esta mal ingresado",Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);



    }
}
