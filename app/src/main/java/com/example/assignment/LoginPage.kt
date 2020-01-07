package com.example.assignment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.core.content.edit
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONArray
import org.json.JSONObject
import java.util.regex.Pattern.compile

class LoginPage : AppCompatActivity() {
    lateinit var userList: ArrayList<User>
    lateinit var progressBar:ProgressBar
    lateinit var pref:SharedPreferences
    private var prefFile = "com.example.assignment.share"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        userList = ArrayList<User>()
        pref = getSharedPreferences(prefFile, Context.MODE_PRIVATE)
        val login: Button =findViewById<Button>(R.id.buttonLoginLogin)
        val back :Button=findViewById<Button>(R.id.buttonBackFirstPage)
        val username = findViewById<EditText>(R.id.username)
        val password =findViewById<EditText>(R.id.password)
        progressBar = findViewById<ProgressBar>(R.id.loading)
        progressBar.visibility= View.GONE
        login.setOnClickListener{
            if(emailRegex.matcher(username.text.toString()).matches()) {
                compare(username.text.toString(), password.text.toString())
            }
            else{
                finish()
            }
        }

        back.setOnClickListener{
            val intent = Intent(this,FirstPage::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun compare(username:String,password:String) {
        val url = getString(R.string.url_server) + getString(R.string.url_user_read)
        progressBar.visibility = View.VISIBLE
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                // Process the JSON
                try{
                    if(response != null){
                        Log.d("main","abc")
                        val strResponse = response.toString()
                        val jsonResponse  = JSONObject(strResponse)
                        val jsonArray: JSONArray = jsonResponse.getJSONArray("records")
                        val size: Int = jsonArray.length()
                        for(i in 0..size-1){
                            var jsonUser: JSONObject = jsonArray.getJSONObject(i)
                            var user: User = User(jsonUser.getString("email"), jsonUser.getString("name"),jsonUser.getString("password"))
                            if((user.email.equals(username))&&user.password.equals(password)){
                                Log.d("Main","successly")
                                progressBar.visibility=View.GONE
                                with(pref.edit()){
                                    putString("1",user.email)
                                    putString("2",user.name)
                                    putString("3",user.password)
                                    putBoolean("4",true)
                                    apply()
                                }
                                val intent = Intent(this,MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                        progressBar.visibility=View.GONE
                    }
                }catch (e:Exception){
                    Log.d("Main", "Response: %s".format(e.message.toString()))


                }
            },
            Response.ErrorListener { error ->
                Log.d("Main", "Response: %s".format(error.message.toString()))

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

    private val emailRegex = compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    companion object{
        const val EXTRA_NAME = "my.edu.tarc.teenjobdb.NAME"
        const val EXTRA_EMAIL = "my.edu.tarc.teenjobdb.CONTACT"
        const val EXTRA_PASSWORD = "my.edu.tarc.teenjobdb.PASSWORD"
    }
}
