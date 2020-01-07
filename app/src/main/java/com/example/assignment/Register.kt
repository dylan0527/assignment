package com.example.assignment

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val signUp = findViewById<Button>(R.id.buttonSignUp)
        val back = findViewById<Button>(R.id.buttonBack)
        signUp.setOnClickListener{
            signUp()

        }
        back.setOnClickListener {
            finish()
        }
    }

    private fun signUp(){
        if(TextUtils.isEmpty(editTextName.text)){
            editTextName.setError(getString(R.string.error_value_required))
            return
        }
        if(TextUtils.isEmpty(editTextEmail.text)){
            editTextEmail.setError(getString(R.string.error_value_required))
            return
        }
        if(TextUtils.isEmpty(editTextPassword.text)){
            editTextPassword.setError(getString(R.string.error_value_required))
            return
        }
        if(TextUtils.isEmpty(editTextconfirmPassword.text)){
            editTextconfirmPassword.setError(getString(R.string.error_value_required))
            return
        }


        val name = editTextName.text.toString()
        val email = editTextEmail.text.toString()
        val password = editTextPassword.text.toString()

        val intent = Intent()
        intent.putExtra(EXTRA_NAME,name)
        intent.putExtra(EXTRA_EMAIL,email)
        intent.putExtra(EXTRA_PASSWORD,password)

        setResult(Activity.RESULT_OK,intent)

        finish()

    }

    companion object{
        const val EXTRA_NAME = "my.edu.tarc.teenjobdb.NAME"
        const val EXTRA_EMAIL = "my.edu.tarc.teenjobdb.CONTACT"
        const val EXTRA_PASSWORD = "my.edu.tarc.teenjobdb.PASSWORD"
    }
}
