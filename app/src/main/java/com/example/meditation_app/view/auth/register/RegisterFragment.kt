package com.example.meditation_app.view.auth.register

import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.viewModels
import com.android.volley.toolbox.Volley
import com.example.meditation_app.R
import com.example.meditation_app.base.BaseFragment
import com.example.meditation_app.databinding.FragmentRegisterBinding
import com.example.meditation_app.data.model.User
import com.example.meditation_app.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>() {

    override fun getInflater(): (LayoutInflater) -> FragmentRegisterBinding = FragmentRegisterBinding::inflate

    private val registerViewModel: RegisterViewModel by viewModels()
    override fun getViewModel(): RegisterViewModel = registerViewModel

    override fun setup() {
        val queue = Volley.newRequestQueue(requireContext())
        observer()
        baseBinding.apply {
            conditionText1.setText(conditionText1.text.toString().makeItUnderlineAndBold(), TextView.BufferType.SPANNABLE)
            conditionText1.movementMethod = LinkMovementMethod.getInstance()
            reCaptcha.setOnClickListener{
                if(!reCaptcha.isChecked) {
                    return@setOnClickListener
                }
                reCaptcha.isChecked = false
                baseViewModel.captcha(requireActivity(), queue) {
                    when(it){
                        is UiState.Success -> {
                            reCaptcha.isChecked = true
                        }
                        is UiState.Failure -> {
                            toast(it.error)
                            reCaptcha.isChecked = false
                        }
                    }
                }
            }
            registerButton.setOnClickListener {
                if(!(conditionCheckBox1.isChecked && conditionCheckBox2.isChecked)) {
                    errorCardView.visibility = View.VISIBLE
                    errorCardText.text = UiString.StringResources(R.string.approve_terms_and_conditions).asString(requireContext())
                    return@setOnClickListener
                }
                if(!reCaptcha.isChecked) {
                    errorCardView.visibility = View.VISIBLE
                    errorCardText.text = UiString.StringResources(R.string.error_recaptcha).asString(requireContext())
                    return@setOnClickListener
                }
                baseViewModel.validation(baseBinding, requireContext()){
                    when(it) {
                        is UiState.Success -> {
                            errorCardView.visibility = View.GONE
                            baseViewModel.register(
                                email = emailEditText.text.toString(),
                                password = passwordEditText.text.toString(),
                                user = getUserObj()
                            )
                        }
                        is UiState.Failure -> {
                            errorCardView.visibility = View.VISIBLE
                            errorCardText.text = it.error
                            Log.d(TAG, "Failure:  ${it.error}")
                        }
                    }
                }
            }
        }
    }

    private fun observer() {
        baseViewModel.register.observe(viewLifecycleOwner) { state ->
            when (state) {
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

    private fun getUserObj(): User {
        return User(
            id = "",
            first_name = baseBinding.firstNameEditText.text.toString(),
            last_name = baseBinding.lastNameEditText.text.toString(),
            email = baseBinding.emailEditText.text.toString(),
        )
    }

    private fun String.makeItUnderlineAndBold(): SpannableString {
        val ss = SpannableString(this)

        ss.setSpan(StyleSpan(Typeface.BOLD), 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val clickableSpan1: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) = notAvailableAlert(requireContext())
            override fun updateDrawState(textPaint: TextPaint) {
                textPaint.color = getColor(requireContext(), R.color.spannable_color)
                textPaint.isUnderlineText = true
            }
        }
        ss.setSpan(clickableSpan1, 33, 50, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        ss.setSpan(StyleSpan(Typeface.BOLD), 33, 50, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val clickableSpan2: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) = notAvailableAlert(requireContext())
            override fun updateDrawState(textPaint: TextPaint) {
                textPaint.color = getColor(requireContext(), R.color.spannable_color)
                textPaint.isUnderlineText = true
            }
        }
        ss.setSpan(clickableSpan2, 94, 109, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        ss.setSpan(StyleSpan(Typeface.BOLD), 94, 109, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return ss
    }

    companion object {
        private const val TAG = "RegisterFragment: "
        fun create(): RegisterFragment {
            return RegisterFragment()
        }
    }

}