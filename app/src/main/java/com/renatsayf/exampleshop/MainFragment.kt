package com.renatsayf.exampleshop

import android.os.Bundle
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.renatsayf.resourses.extensions.toDeepLink

class MainFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findNavController().navigate("signIn".toDeepLink())
    }
}