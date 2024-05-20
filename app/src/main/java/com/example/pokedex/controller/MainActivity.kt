package com.example.pokedex.controller

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.pokedex.R
import com.example.pokedex.databinding.ActivityMainBinding
import com.example.pokedex.model.Singleton
import com.example.pokedex.model.repository.UserRepository
import com.example.pokedex.viewmodel.login.LoginViewModel
import com.example.pokedex.viewmodel.login.LoginViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var inputUsername: EditText
    private lateinit var inputPassword: EditText
    private lateinit var buttonSignIn: Button
    private lateinit var buttonRegisterUser: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )
        viewModel = LoginViewModelFactory().create(LoginViewModel::class.java)
        UserRepository.setContext(this);

        inputUsername = binding.editTextUsername
        inputPassword = binding.editTextPassword
        buttonSignIn = binding.buttonSignIn
        buttonSignIn.isEnabled = false;
        buttonRegisterUser = binding.buttonRegister
        inputUsername.addTextChangedListener(textWatcher);
        inputPassword.addTextChangedListener(textWatcher);
        buttonSignIn.setOnClickListener{signIn()}
        buttonRegisterUser.setOnClickListener{registerUser()}
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val login = inputUsername.text.trim()
            val password = inputPassword.text.trim()
            buttonSignIn.isEnabled = login.isNotEmpty() && password.isNotEmpty()
        }
        override fun afterTextChanged(s: Editable?) {}
    }

    private fun signIn() {
        val login = inputUsername.text.trim().toString()
        val password = inputPassword.text.trim().toString()
        val userFind = viewModel.getByUsernameAndPassword(login, password)
        if (userFind != null) {
            Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
            cleanFields()
            Singleton.setCurrentUser(userFind)
            val intent = Intent(this,PokemonHomeActivity::class.java)
            startActivity(intent)
            return
        }

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder
            .setTitle("The fields are incorrect")
            .setPositiveButton("Close") { _, _ ->
                cleanFields()
            }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun registerUser() {
        val intent = Intent(this, RegisterUserActivity::class.java)
        startActivity(intent)
        return
    }

    private fun cleanFields() {
        inputUsername.setText("");
        inputPassword.setText("");
    }
}
