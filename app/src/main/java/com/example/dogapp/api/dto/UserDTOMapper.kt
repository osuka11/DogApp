package com.example.dogapp.api.dto

import com.example.dogapp.model.User

class UserDTOMapper {

    fun fromUserDTODomain(userDTO: UserDTO): User {
        return User(userDTO.id, userDTO.email, userDTO.authenticationToken)

    }

}