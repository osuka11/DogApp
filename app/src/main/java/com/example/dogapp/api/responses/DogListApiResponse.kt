package com.example.dogapp.api.responses

import com.squareup.moshi.Json

class DogListApiResponse(val message:String,
                         @field:Json(name = "is_sucess")val isSucces: Boolean,
                         val data: DogListResponse) {
}