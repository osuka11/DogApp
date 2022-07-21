package com.example.dogapp.api.dto

import com.squareup.moshi.Json
//Objeto DTO para el objeto Usuario
class SignUpDTO(val email: String,
                val password:String,
                @field:Json(name = "password_confirmation") val passwordConfirmation:String) {

}