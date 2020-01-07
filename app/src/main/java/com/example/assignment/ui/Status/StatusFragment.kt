package com.example.assignment.ui.Status

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.assignment.*
import org.json.JSONArray
import org.json.JSONObject

class StatusFragment : Fragment() {

    lateinit var statusList:ArrayList<Status>
    lateinit var adapter: StatusListAdapter
    lateinit var progress: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_status, container, false)
        progress = root.findViewById(R.id.progressBarStatus)
        progress.visibility = View.GONE
        statusList =ArrayList<Status>()
        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerViewStatus)
        adapter = StatusListAdapter(activity!!)
        adapter.setStatus(statusList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager= LinearLayoutManager(activity)
        return root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.nav_status-> {
                //syncStatus()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
/*
    private fun syncStatus() {
        val url = getString(R.string.url_server) + getString(R.string.url_company_read)+"?email"+user.email
        progress.visibility = View.VISIBLE
        statusList.clear()
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
                            val status: Status = Status(jsonCompany.getString("status"), jsonCompany.getString("companyname"),jsonCompany.getString("companyjob"))
                            statusList.add(status)
                        }
                        Toast.makeText(activity, "Record found :" + size, Toast.LENGTH_LONG).show()
                        progress.visibility = View.GONE
                    }
                }catch (e:Exception){
                    Log.d("Main", "Response: %s".format(e.message.toString()))
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
        MySingleton.getInstance(activity!!).addToRequestQueue(jsonObjectRequest)
    }
    */

}

