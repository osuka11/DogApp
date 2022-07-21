package com.example.dogapp

import android.util.Patterns

/*
Este file sirve para declarar métodos que se van a reutilizar mucho en diferentes problemas
 */
fun isValidEmail(email:String?):Boolean{
    return email.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches()//Este metodo valida si el string que le pasamos tiene una estructura de un email
    //return email.isNullOrEmpty()
}