package com.example.meditation_app.view.login_register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.meditation_app.R
import com.example.meditation_app.databinding.FragmentRegisterBinding
import com.example.meditation_app.model.User
import com.example.meditation_app.utils.UiState
import com.example.meditation_app.utils.isValidEmail
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
            if (validation()){
                viewModel.register(
                    email = binding.emailEditText.text.toString(),
                    password = binding.passwordEditText.text.toString(),
                    user = getUserObj()
                )
            }
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

    private fun validation(): Boolean {
        var isValid = true
        if (binding.firstNameEditText.text.isNullOrEmpty()){
            isValid = false
            toast(getString(R.string.enter_first_name))
        }
        if (binding.lastNameEditText.text.isNullOrEmpty()){
            isValid = false
            toast(getString(R.string.enter_last_name))
        }
        if (binding.emailEditText.text.isNullOrEmpty()){
            isValid = false
            toast(getString(R.string.enter_email))
        }else{
            if (!binding.emailEditText.text.toString().isValidEmail()){
                isValid = false
                toast(getString(R.string.invalid_email))
            }
        }
        if (binding.passwordEditText.text.isNullOrEmpty()){
            isValid = false
            toast(getString(R.string.enter_password))
        }else{
            if (binding.passwordEditText.text.toString().length < 8){
                isValid = false
                toast(getString(R.string.invalid_password))
            }
        }
        return isValid
    }

    companion object {
        fun create(): RegisterFragment {
            return RegisterFragment()
        }
    }

}