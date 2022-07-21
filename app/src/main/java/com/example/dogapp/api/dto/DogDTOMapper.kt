package com.example.dogapp.api.dto

import com.example.dogapp.model.Dog
/*
Esta clase será el que mapie la clase DogDTO y la transforme en una List<Dog>, para eso primero creamos un método
froDogDTODogDomain() que recibe un objeto DogDTO, y que retorna un Objeto Dog, ahora ya tiene sentido no?
Cuando retorna y creamos ese Objeto Dog, ya solamente tenemos que crear la List, creamos otro método llamado
fromDogDTOListToDogDomainInList() que recibe un objeto dogDTOList: List<DogDTO>, usando el método .map
para las listas, creamos la List<Dog> utilizando el retorno del objeto Dog que implementa la funcion froDogDTODogDomain
*vuelve a leer el procedimiento que hicimos, por que a la primera no le entendí, y todavía me cuesta.
 */
class DogDTOMapper {
    fun froDogDTODogDomain(dogDTO: DogDTO): Dog {
        return Dog(dogDTO.id, dogDTO.index,
            dogDTO.name, dogDTO.type,
            dogDTO.heightFemale, dogDTO.heighthMale,
            dogDTO.imageUrl, dogDTO.lifeExpectancy,
        dogDTO.temperament, dogDTO.weightFemale, dogDTO.weighMale)

    }
    fun fromDogDTOListToDogDomainInList(dogDTOList: List<DogDTO>):List<Dog>{
        return dogDTOList.map {
            froDogDTODogDomain(it)
        }
    }
}