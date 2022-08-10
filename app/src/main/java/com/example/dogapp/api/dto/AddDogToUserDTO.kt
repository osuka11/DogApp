package com.example.dogapp.api.dto

import com.squareup.moshi.Json

/*
para agregar un perro a la colección de un usuario vamos a mandarle solamente el id del perro y como también vamos a
mandarle el token del usuario con ese token va a enlazarlo con el id del perro y lo agregará a la coleccion del usuario
 */
class AddDogToUserDTO(@field:Json(name = "dog_id")val id: Long) {
}