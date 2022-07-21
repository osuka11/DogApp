package com.example.dogapp.api

/*
Es decir, tenemos un clase con un número específico de subclases. Lo que obtenemos al final es un concepto muy similar al de un enumerado. La diferencia radica en que en los enumerados sólo tenemos
un único objeto por tipo, mientras que en las sealed classes podemos tener varios objetos de la misma clase.

Para generalizar las respuesta de la API, debemos generalizar lo más posible nuesta sealed class.
Para eso, vamos a indicarle, que ApiResponseStatus<T> va a funcionar para cualquier tipo de dato que metamos
Hacemos lo mismo para todas las clases que tengamos, le agregamos el <T>
 */
sealed class ApiResponseStatus<T>() {
    class Success<T>(val data: T):ApiResponseStatus<T>()
    class Loading<T> :ApiResponseStatus<T>()
    class Error<T>(val messageId :Int):ApiResponseStatus<T>()

}