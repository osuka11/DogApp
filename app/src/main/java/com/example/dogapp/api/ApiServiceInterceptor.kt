package com.example.dogapp.api

import okhttp3.Interceptor
import okhttp3.Response
import java.lang.RuntimeException

/*
Nuesto objeto Interceptor será hecho con el patrón de diseño Singleton

Lo que hace este intercept es que todos los request que mandemos a web van a pasar a través de este método, el método
toma el request que nosotros estamos enviando, verifica si tiene un header(NEEDS_AUTH_HEADER_KEY), si no lo tiene(!=) significa que no necesitamos
autenticacion, y el request continua como si nada, si si tiene el header (==) significa que necesitamos autenticacion entonces va a checer si existe un session token
si no existe va a mandar una excepcion, si si existe lo va a agregar en el builder con la sintaxis que necesita el servidor("AUTH-TOKEN",sessionToken) y finalmente
 */
object ApiServiceInterceptor:Interceptor {

    const val NEEDS_AUTH_HEADER_KEY = "needs_authentication"
    private var sessionToken: String? = null
    //Creamos un setter para el sessionToken
    fun setSessionToken(authenticationToken: String) {
        this.sessionToken = sessionToken
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()
        if(request.header(NEEDS_AUTH_HEADER_KEY) != null){
            //needs credentials
            if(sessionToken == null){
                throw RuntimeException("Need to be authenticated")
            }else{
                requestBuilder.addHeader("AUTH-TOKEN", sessionToken!!)
            }
        }
        return chain.proceed(requestBuilder.build())
    }
}