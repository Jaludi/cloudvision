package com.google.sample.cloudvision

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View

import java.util.ArrayList

class SimilarActivity : AppCompatActivity(), RecycleViewAdapter.ItemClickListener {


    protected lateinit  var adapter: RecycleViewAdapter
    protected lateinit  var urlList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_similiar)
        urlList = intent.getStringArrayListExtra("UrlList")
        val recyclerView = findViewById(R.id.recycler) as RecyclerView
        val numberOfColumns = 2
        recyclerView.layoutManager = GridLayoutManager(this, numberOfColumns)
        adapter = RecycleViewAdapter(urlList, this)
        adapter.setClickListener(this)
        recyclerView.adapter = adapter
    }

    override fun onItemClick(view: View, position: Int) {
        Log.i("TAG", "You clicked number " + adapter.getItem(position) + ", which is at cell position " + position)
        val intent = Intent(this@SimilarActivity, DetailActivity::class.java)
        intent.putExtra("Url", urlList[position])
        startActivity(intent)

    }
}
