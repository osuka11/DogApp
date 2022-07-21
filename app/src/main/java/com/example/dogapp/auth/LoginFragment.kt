package com.example.dogapp.auth

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dogapp.R
import com.example.dogapp.databinding.FragmentLoginBinding
import com.example.dogapp.isValidEmail


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    interface LoginFragmentActions{
        fun onRegisterButtonClick()
        fun onLoginFieldsValidated(email: String, password:String)
    }
    private lateinit var loginFragmentActions: LoginFragmentActions

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginFragmentActions = try {
            context as LoginFragmentActions
        }catch (ex: ClassCastException){
            throw ClassCastException("$context must be implement LoginFragmentAction")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        binding.loginRegisterButton.setOnClickListener {
            loginFragmentActions.onRegisterButtonClick()
        //Cuando se de click al boton register, se manda llamar la funcion de la interface onRegisterButtonClick
            //Esa funcion se implementa en LoginActivity, que lo unico que hace es cambiar de fragment por medio
            //del navigation que hicimos
        }
        binding.loginButton.setOnClickListener {
            validateFields()
        }
        return binding.root

    }

    private fun validateFields() {
        binding.emailInput.error = ""
        binding.passwordInput.error = ""
        val email = binding.emailEdit.text.toString()
        if(isValidEmail(email)){
            binding.emailInput.error = getString(R.string.email_is_not_valid)
            return
        }

        val password = binding.passwordEdit.text.toString()
        if (password.isEmpty()){
            binding.passwordInput.error = getString(R.string.password_must_not_be_empty)
            return
        }

        //Perform sign Up
        loginFragmentActions.onLoginFieldsValidated(email,password)
        //Aqui la mandamos llamar la interface, pero se va a implementar en LoginActivity
    }


}