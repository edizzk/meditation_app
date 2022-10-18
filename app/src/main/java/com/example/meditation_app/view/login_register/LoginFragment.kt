package com.example.meditation_app.view.login_register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.meditation_app.databinding.FragmentLoginBinding
import com.example.meditation_app.utils.UiState
import com.example.meditation_app.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        binding.loginButton.setOnClickListener {
            viewModel.login(
                email = binding.emailEditText.text.toString(),
                password = binding.passwordEditText.text.toString()
            )
        }
    }

    private fun observer(){
        viewModel.login.observe(viewLifecycleOwner) { state ->
            when(state){
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

    companion object {
        fun create(): LoginFragment {
            return LoginFragment()
        }
    }

}