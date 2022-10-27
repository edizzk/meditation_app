package com.example.meditation_app.view.auth.login

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.example.meditation_app.base.BaseFragment
import com.example.meditation_app.databinding.FragmentLoginBinding
import com.example.meditation_app.utils.UiState
import com.example.meditation_app.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override fun getInflater(): (LayoutInflater) -> FragmentLoginBinding = FragmentLoginBinding::inflate

    private val loginViewModel: LoginViewModel by viewModels()
    override fun getViewModel(): LoginViewModel = loginViewModel

    override fun setup() {observer()
        baseBinding.apply {
            loginButton.setOnClickListener{
                baseViewModel.validation(baseBinding, requireContext()){
                    when(it) {
                        is UiState.Success -> {
                            errorCardView.visibility = View.GONE
                            baseViewModel.login(
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
        baseViewModel.getRememberMePref {
            if (it != null){
                //nav to home
            }else {
                Log.d(TAG, "Failure:  $it")
            }
        }
    }

    private fun observer(){
        baseViewModel.login.observe(viewLifecycleOwner) { state ->
            when(state){
                is UiState.Failure -> {
                    baseBinding.errorCardView.visibility = View.VISIBLE
                    baseBinding.errorCardText.text = state.error
                }
                is UiState.Success -> {
                    baseBinding.errorCardView.visibility = View.GONE
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