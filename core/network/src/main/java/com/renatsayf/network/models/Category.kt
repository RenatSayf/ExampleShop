package com.renatsayf.network.models

import java.io.Serializable

data class Category(
    val id: Int,
    val name: String,
    val imgRes: Int
): Serializable {

    companion object{

        suspend fun get(): List<Category> {
                val list = mutableListOf<Category>()
                repeat(2) {
                    list.addAll(
                        listOf(
                            Category(1, "Phones", com.renatsayf.resourses.R.drawable.ic_phone),
                            Category(1, "Headphones", com.renatsayf.resourses.R.drawable.ic_headphones),
                            Category(1, "Games", com.renatsayf.resourses.R.drawable.ic_games),
                            Category(1, "Cars", com.renatsayf.resourses.R.drawable.ic_car),
                            Category(1, "Furniture", com.renatsayf.resourses.R.drawable.ic_furniture),
                            Category(1, "kids", com.renatsayf.resourses.R.drawable.ic_kids)
                        )
                    )
                }
                return list
            }
    }

}
