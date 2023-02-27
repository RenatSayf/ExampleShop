package com.renatsayf.exampleshop

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class MainFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findNavController().navigate(R.id.action_mainFragment_to_login_nav_graph)
    }
}