package com.renatsayf.local.models

import java.io.Serializable

interface IUser: Serializable {
    val email: String
    val firstName: String
    val lastName: String?
    val password: String
    val photoPath: String?
}