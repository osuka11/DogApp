package com.example.dogapp.doglist

import com.example.dogapp.R
import com.example.dogapp.model.Dog
import com.example.dogapp.api.ApiResponseStatus
import com.example.dogapp.api.DogsApi.retofitService
import com.example.dogapp.api.dto.AddDogToUserDTO
import com.example.dogapp.api.dto.DogDTOMapper
import com.example.dogapp.api.makeNetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext


class DogRepository {
/*
Que carajos hicimos aqui? Bueno, en general todas las llamadas a la api, tienen la misma estructura, lo único que varia
es en lo que nos retorna, los metodos que se repiten son los mismos, y podemos generalizar la respuesta de la api
Como? Modificando la clase Sealed, para volverla lo más generica posible. (Ve y checa la sealed class para que veas como quedo)

Ahora, cada vez que hagamos referencia o asignemos un objeto con el tipo de dato ApiResponseStatus, debemos indicarle
explicitamente que tipo de objeto va a recibir en esa instancia.

Lo que hicimos despues, es crear un file con una suspend File, donde vamos a hacer un metodo para generalizar
los request, este metodo le mandaremos un fragmento de código que va a ser evaluado en un try-catch y dependendiendo
la respuesta, va a retornar un success<T> o un error
 */
    private suspend fun downloadDogs(): ApiResponseStatus<List<Dog>> {
        return makeNetworkCall {
            val dogListApiResponse = retofitService.getAllDogs()
            val dogDTOList = dogListApiResponse.data.dogs
            val dogDTOMapper = DogDTOMapper()
            dogDTOMapper.fromDogDTOListToDogDomainInList(dogDTOList)
        }
        /*
        return withContext(Dispatchers.IO){

            try {
                val dogListApiResponse = retofitService.getAllDogs()
                val dogDTOList = dogListApiResponse.data.dogs
                val dogDTOMapper = DogDTOMapper()
                //dogDTOMapper.fromDogDTOListToDogDomainInList(dogDTOList)
                ApiResponseStatus.Success(dogDTOMapper.fromDogDTOListToDogDomainInList(dogDTOList))
           Ahora ya no retornamos solo una List<Dog>, ahora retornaremos un estado de nuestro ApiResponseStatus, que ahora es una sealed class
           Por medio de la sealed class, la clase Success, recibe un objeto tipo List<Dog>, y eso es lo que
           retornamos, por medio de un try catch que captura una excepcion, si llega al catch retorna
           la clase Error de sealed class, que lleva adentro un mensaje de error

            }catch (ex:UnknownHostException){
                ApiResponseStatus.Error("Error descargando datos")
            }
*/
/*El objeto DogListApiResponse tiene una propiedad data, que herada de
DogListResponse y que este a su vez contiene una propieda Dog de tipo List<Dog>


 */

/*
    private fun getFakeDogs(): MutableList<Dog> {
        //Creacion de lista de perros
        val dogList = mutableListOf<Dog>()
        dogList.add(
            Dog(
                1, 1, "Chihuahua", "Toy", 5.4,
                6.7, "", "12 - 15", "", 10.5,
                12.3
            )
        )
        dogList.add(
            Dog(
                2, 1, "Labrador", "Toy", 5.4,
                6.7, "", "12 - 15", "", 10.5,
                12.3
            )
        )
        dogList.add(
            Dog(
                3, 1, "Retriever", "Toy", 5.4,
                6.7, "", "12 - 15", "", 10.5,
                12.3
            )
        )
        dogList.add(
            Dog(
                4, 1, "San Bernardo", "Toy", 5.4,
                6.7, "", "12 - 15", "", 10.5,
                12.3
            )
        )
        dogList.add(
            Dog(
                5, 1, "Husky", "Toy", 5.4,
                6.7, "", "12 - 15", "", 10.5,
                12.3
            )
        )
        dogList.add(
            Dog(
                6, 1, "Xoloscuincle", "Toy", 5.4,
                6.7, "", "12 - 15", "", 10.5,
                12.3
            )
        )
        return dogList
    }

 */

    }

    /*
    Este método es que llamara el servicio de addDogToUser, para agregar un nuevo perro a la coleccion del usuario

     */

    suspend fun addDogToUser(dogId: Long):ApiResponseStatus<Any> = makeNetworkCall {
            val addDogToUserDTO = AddDogToUserDTO(dogId)
            val defaultResponse = retofitService.addDogToUser(addDogToUserDTO)

            if(!defaultResponse.isSucces){
                throw Exception(defaultResponse.message)
            }
        }
    private suspend fun getUserDogs():ApiResponseStatus<List<Dog>> = makeNetworkCall {
        val dogListApiResponse = retofitService.getUserDogs()
        val dogDT0List = dogListApiResponse.data.dogs
        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDogDTOListToDogDomainInList(dogDT0List)

    }

    suspend fun getDogCollection():ApiResponseStatus<List<Dog>>{
        return withContext(Dispatchers.IO) {
            /*
            Deferred Courroutines
            Los procesos de downloadsDogs y getUserDogs se ejecutarán al mismo tiempo.

             */
            val allDogsListResponseDeferred =  async { downloadDogs()  }
            val userDogsListResponseDeferred = async { getUserDogs() }

            val allDogsListResponse = allDogsListResponseDeferred.await()
            val userDogsListResponse = userDogsListResponseDeferred.await()

            if(allDogsListResponse is ApiResponseStatus.Error){
            allDogsListResponse
            }else if(userDogsListResponse is ApiResponseStatus.Error){
            userDogsListResponse
            }else if(allDogsListResponse is ApiResponseStatus.Success &&
                userDogsListResponse is ApiResponseStatus.Success ){
                ApiResponseStatus.Success(
                    getCollectionList(allDogsListResponse.data,
                        userDogsListResponse.data))
            }else{
                ApiResponseStatus.Error(R.string.unknown_error)
            }

        }
    }

    private fun getCollectionList(allDogsList: List<Dog>, userDogList: List<Dog>):List<Dog>{


        //Transforma los elementos de una lista formar una lista nueva
        //Tomamos la lista de todos los perros y la comparamos con la lista de la coleccion de perros del usuario
        return allDogsList.map{
            //Si un elemento de coleccion del usuario es igual a la lista de todos los perros, entonces lo deja tal como es
            //Si no, devuelvo un perro vacio con su index
            if(userDogList.contains(it)){
                it
            }else{
                Dog(0,it.index,"","","","","",
                "","","","",inCollection = false)
            }
        }.sortedBy {
            it.index
        }
        //Metodo sortedBy se activa cuando se agrega la interfaz Comparable en el modelo Dog
    }


}

