package com.example.pokedex.controller

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.pokedex.R
import com.example.pokedex.databinding.ActivityMainBinding
import com.example.pokedex.model.User
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var inputUsername: EditText
    private lateinit var inputPassword: EditText
    private lateinit var buttonSignIn: Button
    private lateinit var buttonRegisterUser: Button
    private var userList: MutableList<User> = mutableListOf(User("user", "1234"))
    private var userAuthenticated: User? = null;

    private val registerUserLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ it ->
        if(it.resultCode == RESULT_OK){
            val json = it.data?.getStringExtra("user")
            val newUser = json?.let { User.fromJson(it) }!!
            userList.add(newUser)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

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
        userAuthenticated = null;
        val login = inputUsername.text.trim().toString()
        val password = inputPassword.text.trim().toString()
        val userFind = userList.find { it.username == login && it.password == password }
        if (userFind != null) {
            userAuthenticated = userFind;
            Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
            cleanFields()
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
        intent.putExtra("userList", Gson().toJson(userList))
        registerUserLauncher.launch(intent)
        return
    }

    private fun cleanFields() {
        inputUsername.setText("");
        inputPassword.setText("");
    }
}
