package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup factory stuff

        String userId = "1234";
        MediaApi mediaApi = new MediaApi();
        new MediaDataSourceFactory(mediaApi, userId);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MediaListAdapter mediaListAdapter = new MediaListAdapter();
        recyclerView.setAdapter(mediaListAdapter);
    }
}
