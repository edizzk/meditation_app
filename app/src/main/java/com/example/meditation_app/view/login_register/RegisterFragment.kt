package com.example.meditation_app.view.login_register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.meditation_app.databinding.FragmentRegisterBinding
import com.example.meditation_app.model.User
import com.example.meditation_app.utils.UiState
import com.example.meditation_app.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        binding.registerButton.setOnClickListener {
            viewModel.register(
                email = binding.emailEditText.text.toString(),
                password = binding.passwordEditText.text.toString(),
                user = getUserObj()
            )
        }
    }

    private fun observer() {
        viewModel.register.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {

                }
                is UiState.Failure -> {
                    toast(state.error)
                }
                is UiState.Success -> {
                    toast(state.data)
                }
            }
        }
    }

    private fun getUserObj(): User {
        return User(
            id = "",
            first_name = binding.firstNameEditText.text.toString(),
            last_name = binding.lastNameEditText.text.toString(),
            email = binding.emailEditText.text.toString(),
        )
    }

    companion object {
        fun create(): RegisterFragment {
            return RegisterFragment()
        }
    }

}