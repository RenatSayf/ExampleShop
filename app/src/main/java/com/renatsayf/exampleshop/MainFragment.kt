package com.renatsayf.exampleshop

import android.os.Bundle
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class MainFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val deepLink = "ExampleShop://signIn".toUri()
        findNavController().navigate(deepLink)
    }
}