package com.example.pokedex.controller

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.pokedex.R
import com.example.pokedex.databinding.ActivityMainBinding
import com.example.pokedex.databinding.ActivityRegisterUserBinding
import com.example.pokedex.model.User
import com.google.gson.Gson

class RegisterUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterUserBinding
    private lateinit var inputRegisterUsername: EditText
    private lateinit var inputRegisterPassword: EditText
    private lateinit var inputRegisterRepeatPassword: EditText
    private lateinit var buttonCreate: Button
    private var userList: MutableList<User> = mutableListOf()
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_register_user
        )

        inputRegisterUsername = binding.editTextRegisterUsername
        inputRegisterPassword = binding.editTextRegisterPassword
        inputRegisterRepeatPassword = binding.editTextRegisterRepeatPassword

        userList = Gson().fromJson(intent.getStringExtra("userList"), Array<User>::class.java).toMutableList()
        buttonCreate = binding.buttonCreate
        buttonCreate.isEnabled = false;
        inputRegisterUsername.addTextChangedListener(textWatcher);
        inputRegisterPassword.addTextChangedListener(textWatcher);
        inputRegisterRepeatPassword.addTextChangedListener(textWatcher);
        buttonCreate.setOnClickListener{createUser()}

    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val login = inputRegisterUsername.text.trim().toString()
            val password = inputRegisterPassword.text.trim().toString()
            val passwordRepeated = inputRegisterRepeatPassword.text.trim().toString()
            buttonCreate.isEnabled = (login.isNotBlank() && password.isNotBlank() && passwordRepeated.isNotBlank()
                    && (password == passwordRepeated))
        }
        override fun afterTextChanged(s: Editable?) {
        }
    }

    private fun createUser() {
        val login = inputRegisterUsername.text.trim().toString()
        val password = inputRegisterPassword.text.trim().toString()
        val passwordRepeated = inputRegisterRepeatPassword.text.trim().toString()
        val userFind = userList.find { it.username == login.lowercase() }

        if (userFind != null) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder
                .setTitle("User already registered with this username")
                .setPositiveButton("Close") { _, _ ->
                }

            val dialog: AlertDialog = builder.create()
            dialog.show()
            return
        }


        if (password == passwordRepeated) {
            Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
            user = User(login.lowercase(), password)
            val intent = Intent()
            intent.putExtra("user", user.toJson())
            setResult(RESULT_OK,intent)
            finish()
        }
    }
}