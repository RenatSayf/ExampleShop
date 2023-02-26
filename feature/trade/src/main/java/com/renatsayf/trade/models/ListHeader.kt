package com.renatsayf.trade.models

import java.io.Serializable

data class ListHeader(
    val title: String,
    val action: String = "View all"
):Serializable