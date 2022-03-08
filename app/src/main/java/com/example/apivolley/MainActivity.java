package com.example.apivolley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.PostProcessor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.*;
import com.example.apivolley.Model.Publicacion;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter arrayAdapter;
    ArrayList<String> datos =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listViewFotos);
        arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,datos);
        listView.setAdapter(arrayAdapter);
        obtenerDatos();
    }

    private void obtenerDatos(){
        String url="https://www.mmobomb.com/api1/games";
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //MANEJO DE JSON
                pasarJson(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }
    private void pasarJson(JSONArray array){

        for(int i=0;i<array.length();i++){

            Publicacion pub= new Publicacion();
            JSONObject json=null;

            try {
                json=array.getJSONObject(i);
                pub.setId(json.getInt("id"));
                pub.setTitle(json.getString("title"));
                pub.setThumbnail(json.getString("thumbnail"));
                pub.setShort_description(json.getString("short_description"));
                pub.setGame_url(json.getString("game_url"));
                pub.setGenre(json.getString("genre"));
                pub.setPlatform(json.getString("platform"));
                pub.setPublisher(json.getString("publisher"));
                pub.setDeveloper(json.getString("developer"));
                //pub.setRelease_date(json.getInt("release_date"));
                pub.setProfile_url(json.getString("profile_url"));
                datos.add(pub.getTitle());   //Agrego la informacion que voy a acargar en la lista.
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        arrayAdapter.notifyDataSetChanged();

    }
}