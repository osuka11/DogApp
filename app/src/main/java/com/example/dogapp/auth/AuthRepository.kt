package com.example.dogapp.auth

import android.util.Log
import com.example.dogapp.model.User
import com.example.dogapp.api.ApiResponseStatus
import com.example.dogapp.api.DogsApi
import com.example.dogapp.api.dto.*
import com.example.dogapp.api.makeNetworkCall

class AuthRepository {
    /*Hacemos lo mismo que el DogRepository, agregamos un nuevo método al ApiService con el tipo de consulta @POST
    que será para insertar nuestros datos en el servicio de Api,

     */
    suspend fun signUp(email: String, password:String, passwordConfirmation:String): ApiResponseStatus<User> {
        return makeNetworkCall {
            Log.d("MakeCall SignUp",email)
            val signUpDTO = SignUpDTO(email,password,passwordConfirmation)  //Aqui creamos nuestro objeto signUpDTO que es el que va a recibir el servidor
            val signUpResponse = DogsApi.retofitService.signUp(signUpDTO)   //Aqui recibimos la respuesta del servidor que viene en forma en SignUpResponse

            //Log.d("Call Success SignUp",signUpResponse.message)

            if(!signUpResponse.isSucces){       //Dependiendo lo que responda el servidor el objeto SignUpResponse, si su su atributo isSuccess retorna false
                                                //Entonces mandamos al atributo de message el SignUpDTO la excepción
                throw Exception(signUpResponse.message)
            }
            //Si la llamada se ejecuto correctamente, solamento descargamos el UserDTO del objeto SignUpResponse y lo mapeamos

            val userDTO = signUpResponse.data.user
            val userDTOMapper = UserDTOMapper()

            Log.d("Mapper Success",userDTO.toString())

            //Al final después de mapearlo vamos a retornar en la sealed Class ApiResponseStatus<User> de tipo User
            userDTOMapper.fromUserDTODomain(userDTO)
        }

    }
//Para el método del Login....

    suspend fun login(email: String, password:String): ApiResponseStatus<User> {
        return makeNetworkCall {
            Log.d("MakeCall Login",email)
            val loginDTO = LoginDTO(email,password)  //Aqui creamos nuestro objeto signUpDTO que es el que va a recibir el servidor
            val loginResponse = DogsApi.retofitService.login(loginDTO)   //Aqui recibimos la respuesta del servidor que viene en forma en SignUpResponse

            Log.d("Call Success Login ",loginResponse.message)

            if(!loginResponse.isSucces){       //Dependiendo lo que responda el servidor el objeto SignUpResponse, si su su atributo isSuccess retorna false
                //Entonces mandamos al atributo de message el SignUpDTO la excepción
                throw Exception(loginResponse.message)
            }
            //Si la llamada se ejecuto correctamente, solamento descargamos el UserDTO del objeto SignUpResponse y lo mapeamos
            /*
            Para el proceso del login, llamada que devuelve, tiene la misma estructura que el SignUp así que solamente nos preocupamos
            por crear nuesto LoginDTO y en la clase que contendrá el retorno de la llamada(signUpResponse para el sign up) lo que hicimos es generalizar
            la clase SignUpResponse y usarla también para el proceso del login, ahora la clase se llama AuthApiResponse
             */
            val userDTO = loginResponse.data.user
            val userDTOMapper = UserDTOMapper()
            Log.d("Mapper Success",userDTO.toString())

            //Al final después de mapearlo vamos a retornar en la sealed Class ApiResponseStatus<User> de tipo User
            userDTOMapper.fromUserDTODomain(userDTO)
        }

    }
}