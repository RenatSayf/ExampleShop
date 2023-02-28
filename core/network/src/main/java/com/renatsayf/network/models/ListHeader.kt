package com.renatsayf.network.models

data class ListHeader(
    val title: String,
    val action: String = "View all"
): ISales {
    override fun getListTitle(): String {
        return title
    }

}