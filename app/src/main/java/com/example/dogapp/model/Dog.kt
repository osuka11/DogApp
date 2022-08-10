package com.example.dogapp.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Dog(
    val id: Long,
    val index:Int,
    val type: String,
    val name: String,
    val heightFemale: String,
    val heighthMale:String,
    val imageUrl: String,
    val lifeExpectancy: String,
    val temperament: String,
    val weightFemale: String,
    val weighMale: String
    ) : Parcelable, Comparable<Dog> {

    //Metodo CompareTo
    override fun compareTo(other: Dog): Int {
        return if (this.index > other.index){
            1
        }else{
            -1
        }
    }
}