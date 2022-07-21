package com.example.dogapp.auth

import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogapp.R
import com.example.dogapp.api.ApiResponseStatus
import com.example.dogapp.model.Dog
import com.example.dogapp.model.User
import kotlinx.coroutines.launch
/*
Aplicando la arquitectura MVVM esta Activity como lo es el LoginActivity, tiene su propio ViewModel y su propio
repositorio, así como también cuenta con sus clases DTO para el transporte desde la capa de datos hasta el viewModel

 */
class AuthViewModel:ViewModel() {
    private val _user =  MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user
    private val _status = MutableLiveData<ApiResponseStatus<User>>()
    val status:LiveData<ApiResponseStatus<User>>
        get() = _status

    private val authRepository = AuthRepository()   //Creamos el objeto AuthRepository para pasarle los datos a su método que llamara a la Api y
    //Posteriormente mappeara el dato retornado

    /*
    Para la implementación de login, se hará la misma lógica, solamente que no pasaremos como dato el PasswordConfirmation
     */
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            handleResponseStatus(authRepository.login(email,password))
        }
    }

    fun sigUp(email: String, password:String, passwordConfirmation:String){
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            handleResponseStatus(authRepository.signUp(email,password,passwordConfirmation))
        }
    }
    private fun handleResponseStatus(apiResponse: ApiResponseStatus<User>) {
        if(apiResponse is ApiResponseStatus.Success){

            Log.d("_User Value:",apiResponse.data.toString())

            _user.value = apiResponse.data   //Si retorna succes, entonces dentro lleva el objeto User

        }else{
            //_status.value = ApiResponseStatus.Error(R.string.there_was_an_error)//Catcheo hasta el error, entonces no retorna la lista y retorna un mensaje de error
            _status.value = apiResponse
            Log.d("_status error",apiResponse.toString())

        }

    }


}