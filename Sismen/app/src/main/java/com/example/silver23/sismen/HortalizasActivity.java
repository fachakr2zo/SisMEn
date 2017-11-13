package com.example.silver23.sismen;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HortalizasActivity extends AppCompatActivity {

    Button  bBuscar, bGuardar, bAct, bEliminar;
    EditText etNom, etCant, etPeso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hortalizas);

        bBuscar = (Button)findViewById(R.id.bSerch);
        bGuardar= (Button)findViewById(R.id.bSave);
        bAct= (Button)findViewById(R.id.bActualizar);
        bEliminar= (Button)findViewById(R.id.bEliminar);
        etNom = (EditText)findViewById(R.id.etNombre);
        etCant = (EditText)findViewById(R.id.etCantidad);
        etPeso = (EditText)findViewById(R.id.etPeso);




        bBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new ConsultarDatos().execute("http://192.168.111.1/consultarhortalizas.php?nombre="+etNom.getText().toString());

            }
        });




        bGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new CargarDatos().execute("http://192.168.111.1/registrohortalizas.php?nombre="+etNom.getText().toString()+"&cantidad="+etCant.getText().toString()+"&peso="+etPeso.getText().toString());

            }
        });




    }

    private class CargarDatos extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(getApplicationContext(), "Se almacenaron los datos correctamente", Toast.LENGTH_LONG).show();

            Intent intentHortaliza = new Intent(HortalizasActivity.this, PrincipalActivity.class);
            startActivity(intentHortaliza);
        }
    }


    private class ConsultarDatos extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            JSONArray ja = null;
            try {
                ja = new JSONArray(result);
                etNom.setText(ja.getString(1));
                etCant.setText(ja.getString(2));
                etPeso.setText(ja.getString(3));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private String downloadUrl(String myurl) throws IOException {
        Log.i("URL",""+myurl);
        myurl = myurl.replace(" ","%20");
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("respuesta", "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
}





