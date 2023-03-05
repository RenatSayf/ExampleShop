package com.renatsayf.profile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.renatsayf.profile.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            includeTitle.btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnLogOut.setOnClickListener {
                val uri = "${getString(R.string.app_name)}://signIn".toUri()
//                val linkRequest = NavDeepLinkRequest.Builder.fromUri(uri).build()
//                val loginNavGraphId = resources.getIdentifier("login_nav_graph", "id", requireContext().packageName)
//                val navOptions = NavOptions.Builder()
//                    .setPopUpTo(destinationId = loginNavGraphId, inclusive = true)
//                    .build()
                findNavController().navigate(uri)
            }
        }
    }

}