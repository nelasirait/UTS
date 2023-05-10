package com.unpam.utsnela;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.unpam.utsnela.model.VideoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailVideoActivity extends AppCompatActivity {

    String id;
    ImageView img;
    TextView judul, judul2, waktu, isi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_video);
        Bundle extras = getIntent().getExtras();
        id = extras.getString("id");
        judul = findViewById(R.id.judul);
        judul2 = findViewById(R.id.judul2);
        waktu = findViewById(R.id.date_time);
        img = findViewById(R.id.image);
        isi = findViewById(R.id.isi);
        volly(id);
    }

    public void volly(String id)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.themoviedb.org/3/movie/"+id+"?api_key=d0f700ff4b5b5e97ef9f9d4024d09829";
        JSONObject jsonObject = new JSONObject();
        final String requestBody = jsonObject.toString();
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jo = new JSONObject(response.toString());
                    judul.setText(jo.optString("original_title"));
                    judul2.setText(jo.optString("title"));
                    waktu.setText("release " + jo.optString("release_date"));
                    isi.setText(jo.optString("overview"));
                    Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w300"+jo.optString("poster_path")).into(img);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
            }
        }
        );
        queue.add(stringRequest);
    }

}