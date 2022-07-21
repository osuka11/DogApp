package com.example.dogapp.api.dto

/*
Uno de los principios de SOLID, es que cada clase tiene que tener un única responsabilidad, nuestra clase Dog,
es la que vamos a utilizar en tod0 el fronted, sin embargo al añadirle el converter @field:Json,
ya le estamos añadiendo otra responsabilidad, que es que codifique un campo como Json. Si bien esto es muy sencillo
de realizar. TENEMOS QUE SEGUIR BUENAS PRACTICAS.
Vamos a utilizar un patrón de diseño llamado: DTO
Los DTO son un tipo de objetos que sirven únicamente para transportar datos. El DTO contiene las propiedades del objeto
Datos que pueden tener su origen en una o más entidades de información.

Para crear este patrón, vamos a crear una clase DogDTO, que llevará la responsabilidad de codificar los campos a JSON, muy simple no?
Y ahora solo necesitamos retornar en nuestra llamada una List<Dog>, Pero una vez que le quitamos la responsabilidad a la clase Dog de ser el
codifique a JSON, habrá conflictos en transporte de los datos, vamos a crear una clase DogDTOMapper. Vamos allá
 */
import com.squareup.moshi.Json

class DogDTO(val id: Long,
val index:Int,
@field:Json(name = "name_en") val name: String,
@field:Json(name = "dog_type") val type: String,
@field:Json(name = "height_female") val heightFemale: String,
@field:Json(name = "height_male") val heighthMale:String,
@field:Json(name = "image_url") val imageUrl: String,
@field:Json(name = "life_expectancy") val lifeExpectancy: String,
val temperament: String,
@field:Json(name = "weight_female") val weightFemale: String,
@field:Json(name = "weight_male")val weighMale: String
)