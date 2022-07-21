package com.example.dogapp.doglist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dogapp.R
import com.example.dogapp.api.ApiResponseStatus
import com.example.dogapp.databinding.ActivityDogListBinding
import com.example.dogapp.dogdetail.DogDetailActivity
import com.example.dogapp.dogdetail.DogDetailActivity.Companion.DOG_KEY

class DogListActivity : AppCompatActivity() {
    private val dogListViewModel: DogListViewModel by viewModels() //Alternativa de declarar un ViewModel(mucho mejor), gracias a cierta dependencia que tenemos en el gradle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recycler = binding.dogRecycler
        val loadingWheel = binding.loadingWheel
        recycler.layoutManager = GridLayoutManager(this,3)
        val adapter = DogAdapter()
        recycler.adapter = adapter
        adapter.setOnItemClickListener {
            //Pasar el Dog a DogDetailActivity
            val intent = Intent(this, DogDetailActivity::class.java)
            intent.putExtra(DOG_KEY, it)
            startActivity(intent)
        }
        dogListViewModel.dogList.observe(this, Observer {
            adapter.submitList(it)
        })
        dogListViewModel.status.observe(this, Observer {
            status ->
            when(status){
                is ApiResponseStatus.Error ->{
                    loadingWheel.visibility = View.GONE
                    Toast.makeText(this, status.messageId,Toast.LENGTH_SHORT).show()
                }
                is ApiResponseStatus.Loading -> loadingWheel.visibility = View.VISIBLE
                is ApiResponseStatus.Success -> loadingWheel.visibility = View.GONE

            }

        })


    }


}