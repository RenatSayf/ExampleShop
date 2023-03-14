package com.renatsayf.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class User(

    @PrimaryKey
    @ColumnInfo(name = "email")
    override val email: String,

    @ColumnInfo(name = "first_name")
    override val firstName: String,

    @ColumnInfo(name = "last_name")
    override val lastName: String?,

    @ColumnInfo(name = "password")
    override val password: String,

    @ColumnInfo(name = "photo_path")
    override val photoPath: String? = null
): IUser
