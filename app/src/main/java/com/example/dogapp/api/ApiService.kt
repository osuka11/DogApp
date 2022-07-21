package com.example.dogapp.api

import com.example.dogapp.BASE_URL
import com.example.dogapp.SIGN_IN_URL
import com.example.dogapp.SIGN_UP_URL
import com.example.dogapp.api.dto.LoginDTO
import com.example.dogapp.api.dto.SignUpDTO
import com.example.dogapp.api.responses.DogListApiResponse
import com.example.dogapp.api.responses.AuthApiResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

private val retrofi = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create())    //Se agrega Moshi para parsear en objetos
    .build()
//Interface que se va a usar cada vez se hagan llamadas a la Api
interface ApiService {
    //Implementado el endpoint de tipo GET, que consultara todos los dogs
    @GET("dogs")
    suspend fun getAllDogs(): DogListApiResponse    //La llamada get Devuelve un Objeto DogListResponse, que es como esta estructurado el JSON y es como lo devuelve moshi(en forma de objeto)


    //la llamada inserta un Objeto que vamos a mandar para poder hacer el SigUp. El objeto
//será de tipo signUpDTO. Y el objeto que va a devolver la llamada será un SignUpApiResponse
//*Recuerda que todos los objetos que tengan que ver con enviar y recibir datos de internet seguiran el patrón de diseño DTO
    @POST(SIGN_UP_URL)
    suspend fun signUp(@Body signUpDTO: SignUpDTO): AuthApiResponse
//Lo unico que cambia de la llamada del signUp a la llamada del Login, es el objeto que almacena, el objeto
//loginDTO la única diferencia que tiene como SignUpDTO es la falta del atributo password_confirmation. Eso es todoo
    @POST(SIGN_IN_URL)
    suspend fun login(@Body loginDTO: LoginDTO ):AuthApiResponse

}

object DogsApi{
    /*Propieda Lazy en kotlin será materializado solo cuando sea necesitado, posponiendo
    la lógica de inicialización al momento en que crees una instancia de su clase contenedora.
    Osea que no va crear el método "retrofi.create()" hasta que sea llamado por primera vez

     */
    //Lo que estamos haciendo es crear un objeto que herede de la interface ApiService, para poder acceder a su metodos
    val retofitService: ApiService by lazy {
        retrofi.create(ApiService::class.java)
    }
}

