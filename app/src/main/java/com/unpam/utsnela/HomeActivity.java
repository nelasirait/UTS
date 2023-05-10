package com.unpam.utsnela;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.unpam.utsnela.adapter.AdapterVideo;
import com.unpam.utsnela.adapter.AdapterVideoAll;
import com.unpam.utsnela.model.VideoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView video, list;
    private List<VideoModel> videonewlist = new ArrayList<>();
    private List<VideoModel> videonewlist2 = new ArrayList<>();
    private AdapterVideo adapterVideonew;
    private AdapterVideoAll adapterVideoAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        list = findViewById(R.id.list_all_movie);
        video = findViewById(R.id.list_new_movie);
        adapterVideonew = new AdapterVideo(videonewlist);
        adapterVideoAll = new AdapterVideoAll(videonewlist2);
        volly();
        volly1();
        adapterVideoAll.OnRecycleViewClickListener(
                new AdapterVideoAll.OnRecycleViewClickListener() {
                    @Override
                    public void OnItemClick(int position) {
                        Toast.makeText(getApplicationContext(), videonewlist2.get(position).getId(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(HomeActivity.this, DetailVideoActivity.class);
                        intent.putExtra("id", videonewlist2.get(position).getId());
                        startActivity(intent);
                    }
                }
        );
        adapterVideonew.OnRecycleViewClickListener(
                new AdapterVideo.OnRecycleViewClickListener() {
                    @Override
                    public void OnItemClick(int position) {
                        Toast.makeText(getApplicationContext(), videonewlist.get(position).getId(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(HomeActivity.this, DetailVideoActivity.class);
                        intent.putExtra("id", videonewlist.get(position).getId());
                        startActivity(intent);
                    }
                }
        );
    }

    public void volly1()
    {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(mLayoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setAdapter(adapterVideoAll);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://api.themoviedb.org/3/movie/now_playing?api_key=d0f700ff4b5b5e97ef9f9d4024d09829";
        JSONObject jsonObject = new JSONObject();
        final String requestBody = jsonObject.toString();
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jo = new JSONObject(response.toString());
                    JSONArray jsonArray = jo.getJSONArray("results");
                    for(int i=0; i < jsonArray.length(); i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.optString("id");
                        String judul = jsonObject.optString("original_title");
                        String gambar = jsonObject.optString("backdrop_path");
                        String time = jsonObject.optString("release_date");
                        String tagline = jsonObject.optString("overview");
                        videonewlist2.add(new VideoModel(id, judul, "http://image.tmdb.org/t/p/w300"+gambar, time, tagline));
                    }
                    adapterVideoAll.notifyDataSetChanged();
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
        videonewlist2.clear();
        queue.add(stringRequest);
    }

    public void volly()
    {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        video.setLayoutManager(mLayoutManager);
        video.setItemAnimator(new DefaultItemAnimator());
        video.setAdapter(adapterVideonew);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=d0f700ff4b5b5e97ef9f9d4024d09829";
        JSONObject jsonObject = new JSONObject();
        final String requestBody = jsonObject.toString();
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jo = new JSONObject(response.toString());
                    JSONArray jsonArray = jo.getJSONArray("results");
                    for(int i=0; i < jsonArray.length(); i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.optString("id");
                        String judul = jsonObject.optString("original_title");
                        String gambar = jsonObject.optString("backdrop_path");
                        String time = jsonObject.optString("release_date");
                        String tagline = jsonObject.optString("overview");
                        videonewlist.add(new VideoModel(id, judul, "http://image.tmdb.org/t/p/w300"+gambar, time,tagline));
                    }
                    adapterVideonew.notifyDataSetChanged();
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
        videonewlist.clear();
        queue.add(stringRequest);
    }
}