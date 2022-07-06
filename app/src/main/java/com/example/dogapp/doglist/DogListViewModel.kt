package com.example.dogapp.doglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogapp.Dog
import kotlinx.coroutines.launch

class DogListViewModel:ViewModel() {
    /*
    Declaraciones de LiveDatas
     */
    private val _dogList = MutableLiveData<List<Dog>>()
    val dogList:LiveData<List<Dog>>
        get() = _dogList

    private val repository = DogRepository()    //Creacion de objeto repositorio

    init {
        downloadDogs()
    }

    private fun downloadDogs() {
        viewModelScope.launch {
            _dogList.value = repository.downloadDogs()
        }
    }
}