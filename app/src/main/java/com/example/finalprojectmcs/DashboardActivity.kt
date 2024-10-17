package com.example.finalprojectmcs

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.finalprojectmcs.adapter.CatAdapter
import com.example.finalprojectmcs.databinding.ActivityDashboardBinding
import com.example.finalprojectmcs.datalink.Cat
import org.json.JSONArray
import org.json.JSONException

class DashboardActivity : AppCompatActivity() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var catAdapter: CatAdapter
    private lateinit var catList: MutableList<Cat>
    private lateinit var requestQueue: RequestQueue
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var databaseHelper: DataBaseHelper
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.CatContent)
        recyclerView.layoutManager = LinearLayoutManager(this)
        catList = mutableListOf()
        catAdapter = CatAdapter(catList, this)
        recyclerView.adapter = catAdapter

        requestQueue = Volley.newRequestQueue(this)

        databaseHelper = DataBaseHelper(this)
        sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)



        val userId = sharedPreferences.getString("user_id", null)
        if (userId != null) {
            val user = databaseHelper.getUserById(userId)
            if (user != null) {
                val username: TextView = binding.usernametext
                val phone: TextView = binding.PhoneText
                username.text = user.username
                phone.text = user.phoneNumber
            }
        }






        val btnAbout: Button = binding.aboutus
        val btnLogout: Button = binding.btnLogOut

        btnAbout.setOnClickListener {
            val intent = Intent(this,AboutusActivity::class.java)
            startActivity(intent)
        }

        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }





        val url = "https://api.npoint.io/3fa9a95557f89f097063"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                parseJSON(response)
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Failed to fetch data: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(jsonArrayRequest)




    }






    private fun parseJSON(response: JSONArray) {
        try {
            for (i in 0 until response.length()) {
                val jsonObject = response.getJSONObject(i)
                val cat = Cat(
                    jsonObject.getString("CatName"),
                    jsonObject.getString("CatDescription"),
                    jsonObject.getInt("CatPrice"),
                    jsonObject.getString("CatImage")
                )
                catList.add(cat)
            }
            catAdapter.notifyDataSetChanged()
        } catch (e: JSONException) {
            e.printStackTrace()
        }


    }


}
