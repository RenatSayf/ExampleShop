package com.renatsayf.login.back

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.renatsayf.login.R
import com.renatsayf.login.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            btnLogin.setOnClickListener {

                val firstName = etFirstName.text.toString()
                val password = etPassword.text.toString()
                viewModel.login(firstName, password)
            }

            lifecycleScope.launchWhenResumed {
                viewModel.state.collect { state ->
                    when(state) {
                        is LoginViewModel.State.Current -> {
                            etFirstName.setText(state.firstName)
                            etPassword.setText(state.password)
                        }
                        is LoginViewModel.State.FailureLogin -> {
                            val message = "The user is not registered"
                            Snackbar.make(root, message, Snackbar.LENGTH_LONG).show()
                        }
                        LoginViewModel.State.SuccessLogin -> {
                            val deepLink = "ExampleShop://trade".toUri()
                            findNavController().navigate(deepLink)
                        }
                    }
                }
            }
        }

    }

    override fun onPause() {

        saveState()
        super.onPause()
    }

    private fun saveState() {
        viewModel.setState(
            LoginViewModel.State.Current(
            firstName = binding.etFirstName.text.toString(),
            password = binding.etPassword.text.toString()
        ))
    }

}