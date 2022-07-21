package com.example.dogapp.doglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogapp.R
import com.example.dogapp.model.Dog
import com.example.dogapp.api.ApiResponseStatus
import kotlinx.coroutines.launch

class DogListViewModel:ViewModel() {
    /*
    Declaraciones de LiveDatas
     */
    private val _dogList = MutableLiveData<List<Dog>>()
    val dogList:LiveData<List<Dog>>
        get() = _dogList
    private val _status = MutableLiveData<ApiResponseStatus<List<Dog>>>()
    val status:LiveData<ApiResponseStatus<List<Dog>>>
        get() = _status


    private val repository = DogRepository()    //Creacion de objeto repositorio

    init {
        downloadDogs()
    }

    private fun downloadDogs() {

        viewModelScope.launch {
            //_dogList.value = repository.downloadDogs()
            _status.value = ApiResponseStatus.Loading()
            handleResponseStatus(repository.downloadDogs())     //Dependiendo que objeto de la clase sealed retorne
            //vamos a hacer una funcion para manejar el objeto que retorne, ya sea un sucess o error.

        }
    }

    private fun handleResponseStatus(apiResponse: ApiResponseStatus<List<Dog>>) {
        if(apiResponse is ApiResponseStatus.Success){
            _dogList.value = apiResponse.data   //Si retorna succes, entonces dentro lleva la list<Dog>

        }else{
            _status.value = ApiResponseStatus.Error(R.string.there_was_an_error)//Catcheo hasta el error, entonces no retorna la lista y retorna un mensaje de error
        }

    }
}