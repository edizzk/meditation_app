package com.example.meditation_app.view.auth.login

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.example.meditation_app.base.BaseFragment
import com.example.meditation_app.databinding.FragmentLoginBinding
import com.example.meditation_app.utils.Resource
import com.example.meditation_app.utils.toast
import com.example.meditation_app.view.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override fun getInflater(): (LayoutInflater) -> FragmentLoginBinding = FragmentLoginBinding::inflate

    private val loginViewModel: LoginViewModel by viewModels()
    override fun getViewModel(): LoginViewModel = loginViewModel

    override fun setup() {
        observer()
        baseBinding.apply {
            loginButton.setOnClickListener{
                baseViewModel.validation(emailEditText.text, passwordEditText.text){
                    when(it) {
                        is Resource.Success -> {
                            errorCardView.visibility = View.GONE
                            baseViewModel.login(
                                email = emailEditText.text.toString(),
                                password = passwordEditText.text.toString(),
                                rememberMe = rememberMeCheckbox.isChecked
                            )
                        }
                        is Resource.Failure -> {
                            errorCardView.visibility = View.VISIBLE
                            errorCardText.text = it.error
                        }
                    }
                }
            }
        }
    }

    private fun observer(){
        baseViewModel.login.observe(viewLifecycleOwner) { state ->
            when(state){
                is Resource.Failure -> {
                    baseBinding.errorCardView.visibility = View.VISIBLE
                    baseBinding.errorCardText.text = state.error
                    Log.d(TAG, "Login Failure: ${state.error}")
                }
                is Resource.Success -> {
                    baseBinding.errorCardView.visibility = View.GONE
                    Intent(requireContext(), HomeActivity::class.java).also { startActivity(it)}
                    requireActivity().finish()
                    toast(state.data)
                }
            }
        }
    }

    companion object {
        private const val TAG = "LoginFragment: "
        fun create(): LoginFragment {
            return LoginFragment()
        }
    }

}