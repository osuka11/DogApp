package com.example.dogapp.dogdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import coil.load
import com.example.dogapp.model.Dog
import com.example.dogapp.R
import com.example.dogapp.databinding.ActivityDogDetailBinding

class DogDetailActivity : AppCompatActivity() {
    companion object{
        const val DOG_KEY = "dog"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dog = intent?.extras?.getParcelable<Dog>(DOG_KEY)
        if(dog == null){
            Toast.makeText(this,R.string.error_showing_dog_not_found,Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        binding.dog = dog
        binding.dogNameText.text = dog.name
        binding.dogIndex.text = getString(R.string.dog_index_format,dog.index)
        binding.lifeExpectancy.text = getString(R.string.dog_life_expectating_format,dog.lifeExpectancy)

        binding.dogImage.load(dog.imageUrl)
        binding.closeButton.setOnClickListener {
            finish()
        }


    }
}