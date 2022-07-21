package com.example.dogapp.api.responses

import com.squareup.moshi.Json
//Cuando devuelve el tipo de dato SignUpResponse, este contiene un objeto data que es de tipo UserResponse
//El objeto UserResponse a su vez contiene un objeto de tipo UserDTO que más adelante solo mapearemos
// para por fin tener nuestro objeto User

/*
*ACTUALIZACION: Para cuando antes de implementar el login esta clase se llamada SignUpResponse. Sin embargo
* la llamada al login implementaba casi el mismo proceso, así que reutilizamo esta misma clase para hacer lo que se hubiera
* llamado una loginResponse, pero le colocamos un nombre más general para que describa ambos procesos Login y SignUp
* Ahora cada vez que hable de objeto o de la clase SignUpResponse, me estoy refieriendo al objeto AuthApiResponse que es lo mismo
 */
class AuthApiResponse(
    val message:String,
    @field:Json(name = "is_success")val isSucces: Boolean,
    val data: UserResponse,
)