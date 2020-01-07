package com.example.assignment.ui.home


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.Constraints
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.assignment.Company
import com.example.assignment.CompanyListAdapter
import com.example.assignment.MySingleton
import com.example.assignment.R
import org.json.JSONArray
import org.json.JSONObject

class HomeFragment : Fragment() {

    lateinit var companyList:ArrayList<Company>
    lateinit var adapterview: CompanyListAdapter
    //lateinit var progress: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        //progress = root.findViewById(R.id.progressBarHome)
        //progress.visibility = View.GONE
        companyList =ArrayList<Company>()
        adapterview=CompanyListAdapter(companyList)
        syncCompany()
        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerViewHome).apply{
            setHasFixedSize(true)
            layoutManager=LinearLayoutManager(activity)
            adapter = adapterview
        }
        return root
    }
    /*
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.nav_home-> {
                syncCompany()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    */
    private fun syncCompany() {
        val url = getString(R.string.url_server) + getString(R.string.url_company_read)
        //progress.visibility = View.VISIBLE
        companyList.clear()
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                // Process the JSON
                try{
                    if(response != null){
                        val strResponse = response.toString()
                        val jsonResponse  = JSONObject(strResponse)
                        val jsonArray: JSONArray = jsonResponse.getJSONArray("records")
                        val size: Int = jsonArray.length()
                        for(i in 0..size-1){
                            val jsonCompany: JSONObject = jsonArray.getJSONObject(i)
                            val company: Company = Company(jsonCompany.getString("name"), jsonCompany.getString("description"),jsonCompany.getInt("rating"),jsonCompany.getString("job"),jsonCompany.getString("jobdescription"),jsonCompany.getString("review"),jsonCompany.getInt("salary"))
                            companyList.add(company)
                        }
                        Toast.makeText(activity, "Record found :" + size, Toast.LENGTH_LONG).show()
                       // progress.visibility = View.GONE
                    }
                }catch (e:Exception){
                    Log.d("Main", "Response: %s".format(e.message.toString()))
                  //  progress.visibility = View.GONE

                }
            },
            Response.ErrorListener { error ->
                Log.d("Main", "Response: %s".format(error.message.toString()))
               // progress.visibility = View.GONE
            }
        )
        //Volley request policy, only one time request
        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            0, //no retry
            1f
        )

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(activity!!).addToRequestQueue(jsonObjectRequest)
    }

    fun getsize():Int{
        return companyList.size
    }
}