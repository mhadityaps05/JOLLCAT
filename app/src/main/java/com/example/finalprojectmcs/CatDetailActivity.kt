package com.example.finalprojectmcs

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import com.example.finalprojectmcs.databinding.ActivityCatdetailBinding
import com.squareup.picasso.Picasso

class CatDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCatdetailBinding
    private lateinit var CatImage: ImageView
    private lateinit var CatName: TextView
    private lateinit var CatDescription: TextView
    private lateinit var CatPrice: TextView
    private lateinit var PurchaseQuantity: EditText
    private lateinit var btnBuy: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatdetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CatImage = findViewById(R.id.imgCat)
        CatName = findViewById(R.id.textName)
        CatDescription = findViewById(R.id.textDescription)
        CatPrice = findViewById(R.id.textPrice)
        PurchaseQuantity = findViewById(R.id.PurchaseQuantity)
        btnBuy = findViewById(R.id.btnBuy)

        val catName = intent.getStringExtra("CatName")
        val catDescription = intent.getStringExtra("CatDescription")
        val catPrice = intent.getIntExtra("CatPrice", 0)
        val catImage = intent.getStringExtra("CatImage")


        CatName.text = catName
        CatDescription.text = catDescription
        CatPrice.text = "$catPrice"
        Picasso.get().load(catImage).into(CatImage)

        val i = PurchaseQuantity.text.toString()

        btnBuy.setOnClickListener {
            val intent = Intent(this,DashboardActivity::class.java)
            startActivity(intent)
        }

    }


}