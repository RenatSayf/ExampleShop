package com.renatsayf.login.sign_in

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.renatsayf.login.R
import com.renatsayf.login.databinding.FragmentSignInBinding
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            btnLogin.setOnClickListener {

                findNavController().navigate(R.id.action_signInFragment_to_loginFragment)
            }

            etEmail.validEmail {

            }

            btnSignIn.setOnClickListener {
                val isValid = etEmail.validEmail()
                when(isValid) {
                    true -> {
                        viewModel.registration(
                            firstName = etFirstName.text.toString(),
                            lastName = etLastName.text.toString(),
                            email = etEmail.text.toString()
                        )
                    }
                    false -> {
                        val message = "${etEmail.text} - not valid email address"
                        Snackbar.make(it, message, Snackbar.LENGTH_LONG).show()
                    }
                }
            }

            lifecycleScope.launchWhenResumed {
                viewModel.state.collect { state ->
                    when(state) {
                        is SignInViewModel.State.Current -> {
                        }
                        is SignInViewModel.State.FailureSignUp -> {
                            val message = "User with such email already exists"
                            Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
                        }
                        is SignInViewModel.State.SuccessSignUp -> {
                            val message = "Your password is ${state.password}"
                            Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
                            findNavController().navigate(R.id.action_signInFragment_to_trade_nav_graph)
                        }
                    }
                }
            }
        }
    }

    override fun onPause() {

        saveState()
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })
        super.onPause()
    }

    private fun saveState() {
        viewModel.setState(SignInViewModel.State.Current(
            firstName = binding.etFirstName.text.toString(),
            lastName = binding.etLastName.text.toString(),
            email = binding.etEmail.text.toString()
        ))
    }

}