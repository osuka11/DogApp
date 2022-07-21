package com.example.dogapp.api

import android.util.Log
import com.example.dogapp.R
import com.example.dogapp.api.dto.DogDTOMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
/*
No hay que complicarnos mucho aqui, simplemente esa suspend fun(que funcion de manera interna) recibe un lambda
o recibe una funcion, que vienen siendo los métodos{
            val dogListApiResponse = retofitService.getAllDogs()
            val dogDTOList = dogListApiResponse.data.dogs
            val dogDTOMapper = DogDTOMapper()
            dogDTOMapper.fromDogDTOListToDogDomainInList(dogDTOList)
            }
            Estos metodos, por medio de la lambda, se van a mandar a llamar en la invocacion de call()
            y hará un try cacth, para que lambda que le pasemos, entonces, nos devolvera un sucess o un error

 */
suspend fun <T> makeNetworkCall(
    call:suspend () -> T
):ApiResponseStatus<T> {
    return withContext(Dispatchers.IO) {

        try {

            ApiResponseStatus.Success(call())
        } catch (ex: UnknownHostException) {
            ApiResponseStatus.Error(R.string.unknow_host_exception_error)
        }catch (e: Exception){
            //Esta exception la cacheamos en caso de que el la llamada para el signUp regrese cualquiera de estos mensajes

            val errorMessage = when(e.message){
               "sign_up_error" -> R.string.error_sign_up
               "sign_in_error" ->  R.string.error_sign_in
                "user_already_exist" -> R.string.user_already_exists
                else -> R.string.unknown_error
            }
            Log.e("Error Message",errorMessage.toString())
            ApiResponseStatus.Error(errorMessage)
        }

    }
}