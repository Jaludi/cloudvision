package com.google.sample.cloudvision;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SimilarActivity extends AppCompatActivity implements RecycleViewAdapter.ItemClickListener {


    RecycleViewAdapter adapter;
    ArrayList<String> urlList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similiar);
        urlList = getIntent().getStringArrayListExtra("UrlList");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new RecycleViewAdapter(urlList, this);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i("TAG", "You clicked number " + adapter.getItem(position) + ", which is at cell position " + position);
        Intent intent = new Intent(SimilarActivity.this, DetailActivity.class);
        intent.putExtra("Url", urlList.get(position));
        Log.d("TAG", "onItemClick: testttt" );
        startActivity(intent);

    }
}
