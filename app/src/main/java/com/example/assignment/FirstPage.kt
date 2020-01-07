package com.example.assignment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import java.util.*

class FirstPage : AppCompatActivity() {

    private val REQUEST_CODE = 1
    lateinit var progress: ProgressBar
    lateinit var userList: ArrayList<User>
    lateinit var pref: SharedPreferences
    private var prefFile = "com.example.assignment.share"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pref = getSharedPreferences(prefFile,Context.MODE_PRIVATE)
        if(pref.getBoolean("4",false )){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        setContentView(R.layout.firstpage)
        userList = ArrayList<User>()
        progress = findViewById(R.id.progressBar)
        progress.visibility = View.GONE
        val btnLogin = findViewById<Button>(R.id.buttonLogin)
        val btnSignUp = findViewById<Button>(R.id.ButtonRegister)

        btnLogin.setOnClickListener{
            val intent = Intent(this,LoginPage::class.java)
            startActivity(intent)
            finish()
        }

        btnSignUp.setOnClickListener{
            val intent = Intent(this,Register::class.java)
            startActivityForResult(intent,REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data:Intent?) {
        if(requestCode == REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                data?.let{
                    val user = User((it.getStringExtra(Register.EXTRA_EMAIL)), it.getStringExtra(Register.EXTRA_NAME),it.getStringExtra(Register.EXTRA_PASSWORD))
                    createUser(user)
                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun createUser(user: User) {

        val url = getString(R.string.url_server) + getString(R.string.url_user_create) + "?name=" + user.name +
                "&email=" + user.email + "&password=" + user.password

        progress.visibility = View.VISIBLE

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                // Process the JSON
                try{

                    if(response != null){
                        val strResponse = response.toString()
                        val jsonResponse  = JSONObject(strResponse)
                        val success: String = jsonResponse.get("success").toString()

                        if(success.equals("1")){
                            Toast.makeText(applicationContext, "Record saved", Toast.LENGTH_LONG).show()
                            //Add record to user list
                            userList.add(user)

                        }else{
                            Toast.makeText(applicationContext, "Record not saved", Toast.LENGTH_LONG).show()
                        }
                        progress.visibility = View.GONE
                    }
                }catch (e:Exception){

                    Log.d("Main", "Responses: %s".format(e.message.toString()))
                    progress.visibility = View.GONE

                }
            },
            Response.ErrorListener { error ->
                Log.d("Main", "Response: %s".format(error.message.toString()))
                progress.visibility = View.GONE
            }
        )

        //Volley request policy, only one time request
        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            0, //no retry
            1f
        )

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}
