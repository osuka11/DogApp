package com.example.dogapp.model

import android.app.Activity
import android.content.Context
import com.squareup.moshi.Json

class User(
    val id: Long,
    val email: String,
    val authenticationToken: String,


    ){


    companion object{
        private const val AUTH_PREFS = "auth_prefs"
        private const val ID_KEY = "id"
        private const val EMAIL_KEY = "email"
        private const val AUTH_TOKEN_KEY = "auth_token"


        /*
        Una funcion sirve para guardar las preferencias de inicio de sesion del usuario, como el email, la contraseña
        y token, cuando se guarden, cada que el usuario cierra la app y vuelva a entrar, la aplicacion buscará esas preferencias
        e iniciaria la app sin necesidad de hacer login, recibe como parametro el activity desde donde se llama, que puede ser
        login activiy o main activity
         */

        fun setLoggedInUser(activity:Activity, user: User){
            activity.getSharedPreferences(AUTH_PREFS, Context.MODE_PRIVATE).also {
                it.edit().putLong(ID_KEY, user.id)
                    .putString(EMAIL_KEY,user.email)
                    .putString(AUTH_TOKEN_KEY, user.authenticationToken).apply()

            }

        }
        /*
        Esta funcion consulta los datos o preferencias guardadas anteriormente y hace una validacion de que las preferencias
        no sean nulas( lo cual significaría que no hay ningun usuario logeado), de esa forma con un usuario logueado y con las sharedPreferences
        se podrá iniciar la app sin hacer login
         */
        fun getLoggedInUser(activity:Activity): User?{
            //Almacena los sharedPreferences
            val prefs = activity.getSharedPreferences(AUTH_PREFS, Context.MODE_PRIVATE) ?: return null

            val userId = prefs.getLong(ID_KEY,0)
            if (userId == 0L){
                return null
            }

            val user = User(
                userId,
                prefs.getString(EMAIL_KEY,"")?:"",
                prefs.getString(AUTH_TOKEN_KEY,"")?: "" )

            return user
        }
        /*
        Funcion para el LogOut del usuario, borra la preferencia previamente guardada
         */
        fun logout(activity: Activity){
            activity.getSharedPreferences(AUTH_PREFS, Context.MODE_PRIVATE).also {
                it.edit()
                    .clear().apply()

            }
        }
    }
}