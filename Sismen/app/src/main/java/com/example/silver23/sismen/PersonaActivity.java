package com.example.silver23.sismen;

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

public class PersonaActivity extends AppCompatActivity {

    Button bBuscar, bGuardar;
    EditText etNom, etFech, etPeso, etAl, etSn, etEnf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persona);


        bBuscar = (Button)findViewById(R.id.bSerch);
        bGuardar = (Button)findViewById(R.id.bSave);
        etNom = (EditText)findViewById(R.id.etNombre);
        etFech = (EditText) findViewById(R.id.etFecha);
        etPeso = (EditText)findViewById(R.id.etPeso);
        etAl = (EditText)findViewById(R.id.etAltura);
        etSn = (EditText)findViewById(R.id.etSn);
        etEnf  = (EditText)findViewById(R.id.etEnf);



        bBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new ConsultarDatos().execute("http://192.168.111.1/consultapersona.php?nombre="+etNom.getText().toString());

            }
        });


        bGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new CargarDatos().execute("http://192.168.111.1/registropersona.php?nombre="+etNom.getText().toString()+"&fecha="+etFech.getText().toString()+"&peso="+etPeso.getText().toString()+"&altura="+etAl.getText().toString()+"&estado="+etSn.getText().toString()+"&enfermedad="+etEnf.getText().toString());

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
                etFech.setText(ja.getString(2));
                etPeso.setText(ja.getString(3));
                etAl.setText(ja.getString(4));
                etSn.setText(ja.getString(5));
                etEnf.setText(ja.getString(6));



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
