package com.example.dogapp.doglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogapp.Dog
import com.example.dogapp.databinding.ActivityDogListBinding

class DogListActivity : AppCompatActivity() {
    private val dogListViewModel: DogListViewModel by viewModels() //Alternativa de declarar un ViewModel(mucho mejor), gracias a cierta dependencia que tenemos en el gradle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recycler = binding.dogRecycler
        recycler.layoutManager = LinearLayoutManager(this)
        val adapter = DogAdapter()
        recycler.adapter = adapter
        dogListViewModel.dogList.observe(this, Observer {
            adapter.submitList(it)
        })

    }


}