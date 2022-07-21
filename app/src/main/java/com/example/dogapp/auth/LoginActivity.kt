package com.example.dogapp.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.dogapp.MainActivity
import com.example.dogapp.R
import com.example.dogapp.api.ApiResponseStatus
import com.example.dogapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), LoginFragment.LoginFragmentActions, SignUpFragment.SignUpFragmentActions {

    private val viewModel: AuthViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val loadingWheel = binding.loadingWheel

        viewModel.status.observe(this) {
            when(it){
                is ApiResponseStatus.Error ->{
                    loadingWheel.visibility = View.GONE
                    showErrorDialog(it.messageId)
                }
                is ApiResponseStatus.Loading -> loadingWheel.visibility = View.VISIBLE
                is ApiResponseStatus.Success -> loadingWheel.visibility = View.GONE
            }
        }
        //Cuando el observe detecte el cambio del user, que es cuando se hace todoo el procedimiento
        // del signUp , entonces iniciaremos la siguiente activity
        viewModel.user.observe(this){
            user ->
            if(user!=null){
                startMainActivity()
            }
        }
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun showErrorDialog(messageId: Int){
        AlertDialog.Builder(this)
            .setTitle(R.string.there_was_an_error)
            .setMessage(messageId)
            .setPositiveButton(android.R.string.ok){ _,_ -> /** Dimiss Dialog **/}
            .create()
            .show()

    }
    override fun onRegisterButtonClick() {
        findNavController(R.id.nav_host_fragment)
            .navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
    }
    //Al implementar la interface LoginFragmentsActions, cualquier método que implemente esta interfaz
    //Se deberá implementar forzosamente aquí
    override fun onLoginFieldsValidated(email: String, password: String) {
        viewModel.login(email,password)
    }

    //Aqui se hace la implementacion de la interface SignUpFragmentActions que trae el método onSignUpFieldsValidated()
    //Lo que hace es que lo datos que le pasamos desde el fragment lo recibimos aqui en login activity
    //y ejecutamos la función del ViewModel sigUp() que se encargará de la lógica del signUp
    override fun onSignUpFieldsValidated(
        email: String,
        password: String,
        passwordConfirmation: String
    ) {
        viewModel.sigUp(email,password,passwordConfirmation)
    }
}