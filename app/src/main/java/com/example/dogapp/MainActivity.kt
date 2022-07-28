package com.example.dogapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dogapp.auth.LoginActivity
import com.example.dogapp.databinding.ActivityMainBinding
import com.example.dogapp.model.User
import com.example.dogapp.settings.SettingsActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = User.getLoggedInUser(this)
        if(user == null){
            openLoginActivity()
            return

        }

        binding.settingsFab.setOnClickListener {
            openSettingsActivity()
        }

    }

    private fun openSettingsActivity() {
        startActivity(Intent(this,SettingsActivity::class.java))
    }

    private fun openLoginActivity() {
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }

}
