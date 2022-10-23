package com.example.meditation_app.view.auth.login

import android.os.Bundle
import android.util.Log
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
        observer()
        binding.apply {
            loginButton.setOnClickListener{
                viewModel.validation(binding, requireContext()){
                    when(it) {
                        is UiState.Success -> {
                            errorCardView.visibility = View.GONE
                            viewModel.login(
                                email = emailEditText.text.toString(),
                                password = passwordEditText.text.toString(),
                                rememberMe = rememberMeCheckbox.isChecked
                            )
                        }
                        is UiState.Failure -> {
                            errorCardView.visibility = View.VISIBLE
                            errorCardText.text = it.error
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getRememberMePref {
            if (it != null){
                //nav to home
            }else {
                Log.d("TEST, ", "Failure:  $it")
            }
        }
    }

    private fun observer(){
        viewModel.login.observe(viewLifecycleOwner) { state ->
            when(state){
                is UiState.Failure -> {
                    binding.errorCardView.visibility = View.VISIBLE
                    binding.errorCardText.text = state.error
                }
                is UiState.Success -> {
                    binding.errorCardView.visibility = View.GONE
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