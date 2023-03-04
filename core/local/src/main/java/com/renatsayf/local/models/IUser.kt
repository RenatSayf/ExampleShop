package com.renatsayf.local.models

interface IUser {
    val email: String
    val firstName: String
    val lastName: String?
    val password: String
}