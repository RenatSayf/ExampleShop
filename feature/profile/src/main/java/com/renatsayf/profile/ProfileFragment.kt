package com.renatsayf.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.renatsayf.local.utils.getUserFromPref
import com.renatsayf.profile.databinding.FragmentProfileBinding
import com.renatsayf.resourses.extensions.getImageFromInternalStorage
import com.renatsayf.resourses.extensions.saveImageToInternalStorage
import com.renatsayf.resourses.extensions.toDeepLink
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

        if (savedInstanceState == null) {
            this.getUserFromPref(onSuccess = { user ->
                viewModel.getUserData(user.firstName, user.password)
            })
        }

        with(binding) {

            includeTitle.btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnLogOut.setOnClickListener {
                findNavController().navigate("signIn".toDeepLink())
            }

            includeAboutUser.btnChangePhoto.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                resultLauncher.launch(intent)
            }

            lifecycleScope.launchWhenStarted {
                viewModel.state.collect { state ->
                    when(state) {
                        is ProfileViewModel.State.DataSuccess -> {
                            val path = state.user.photoPath
                            path?.let {
                                includeAboutUser.ivUserPhoto.getImageFromInternalStorage(it, onSuccess = { bitmap ->
                                    includeAboutUser.ivUserPhoto.setImageBitmap(bitmap)
                                })
                            }
                        }
                        is ProfileViewModel.State.Error -> {
                            Snackbar.make(binding.root, state.error, Snackbar.LENGTH_LONG).show()
                        }
                        ProfileViewModel.State.Initial -> {}
                        ProfileViewModel.State.Loading -> {}
                    }
                }
            }
        }
    }

    @Suppress("RedundantSamConstructor")
    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                val uri = intent?.data
                binding.includeAboutUser.ivUserPhoto.setImageURI(uri)

                this@ProfileFragment.getUserFromPref(onSuccess = { user ->
                    binding.includeAboutUser.ivUserPhoto.saveImageToInternalStorage(
                        user.firstName,
                        onSuccess = { file ->
                            val updatedUser = user.copy(photoPath = file)
                            viewModel.updateUserData(updatedUser)
                        })
                })
            }
        }
    )

}