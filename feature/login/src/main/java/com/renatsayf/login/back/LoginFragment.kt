package com.renatsayf.login.back

import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.renatsayf.login.R
import com.renatsayf.login.databinding.FragmentLoginBinding
import com.renatsayf.resourses.extensions.toDeepLink
import dagger.hilt.android.AndroidEntryPoint


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

            btnPasswordVisibility.setOnClickListener {
                val imageView = it as ImageView
                if (etPassword.inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    etPassword.apply {
                        inputType = InputType.TYPE_CLASS_TEXT
                        transformationMethod = PasswordTransformationMethod.getInstance()
                        setSelection(this.text.length)
                    }
                    imageView.setImageResource(R.drawable.ic_visibility_off_grey)
                }
                else {
                    etPassword.apply {
                        inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                        transformationMethod = HideReturnsTransformationMethod.getInstance()
                        setSelection(this.text.length)
                    }
                    imageView.setImageResource(R.drawable.ic_visibility_grey)
                }
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
                            findNavController().navigate("trade".toDeepLink())
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