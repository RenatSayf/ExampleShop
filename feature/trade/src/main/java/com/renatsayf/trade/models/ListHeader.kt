package com.renatsayf.trade.models

data class ListHeader(
    val title: String,
    val action: String = "View all"
): ISales {
    override fun getListName(): String {
        return title
    }
}