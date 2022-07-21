package com.example.dogapp.auth

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dogapp.R
import com.example.dogapp.databinding.FragmentLoginBinding
import com.example.dogapp.databinding.FragmentSignUpBinding
import com.example.dogapp.isValidEmail

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    /*Interface para pasar datos del SignUPFragment al LoginActivity, esta interface la implementa
    Login activity y loginActivity recibe los datos por la interface y por su viewModel se encarga de lo demas
     */
    interface SignUpFragmentActions{

        fun onSignUpFieldsValidated(email: String, password:String, passwordConfirmation:String)

    }
    private lateinit var signUpFragmentActions: SignUpFragmentActions

    override fun onAttach(context: Context) {
        super.onAttach(context)
        signUpFragmentActions = try {
            context as SignUpFragmentActions
        }catch (ex: ClassCastException){
            throw ClassCastException("$context must be implement LoginFragmentAction")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater)
        setupSignUpButton()

        return binding.root
    }

    private fun setupSignUpButton() {
        binding.signUpButton.setOnClickListener {
            validateFields()
        }
    }
    //Metodo para validar los campos. Si al final los campos son correctos, se invoca la interface signUpFragmentActions
    private fun validateFields() {
        binding.emailInput.error = ""
        binding.confirmPasswordInput.error = ""
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

        val passwordConfirmation = binding.confirmPasswordEdit.text.toString()
        if (passwordConfirmation.isEmpty()){
            binding.confirmPasswordInput.error = getString(R.string.password_must_not_be_empty)
            return
        }

        if(password != passwordConfirmation) {
                binding.passwordInput.error = getString(R.string.password_do_not_match)
                return
        }
        //Perform sign Up
        signUpFragmentActions.onSignUpFieldsValidated(email,password,passwordConfirmation)
        //Aqui la mandamos llamar la interface, pero se va a implementar en LoginActivity


    }

}