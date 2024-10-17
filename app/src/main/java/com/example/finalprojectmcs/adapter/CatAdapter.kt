package com.example.finalprojectmcs.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectmcs.datalink.Cat
import com.example.finalprojectmcs.CatDetailActivity
import com.example.finalprojectmcs.R
import com.squareup.picasso.Picasso

class CatAdapter(private val catList: MutableList<Cat>, private val context: Context) : RecyclerView.Adapter<CatAdapter.CatViewHolder>() {

    class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.textName)
        val description: TextView = itemView.findViewById(R.id.textDescription)
        val price: TextView = itemView.findViewById(R.id.textPrice)
        val image: ImageView = itemView.findViewById(R.id.imgCat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cat_item, parent, false)
        return CatViewHolder(view)
    }
    override fun getItemCount(): Int {
        return catList.size
    }
    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val cat = catList[position]
        holder.name.text = cat.name
        holder.description.text = cat.description
        holder.price.text = "${cat.price}"
        Picasso.get().load(cat.imageUrl).into(holder.image)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, CatDetailActivity::class.java).apply {
                putExtra("CatName", cat.name)
                putExtra("CatDescription", cat.description)
                putExtra("CatPrice", cat.price)
                putExtra("CatImage", cat.imageUrl)
            }
            context.startActivity(intent)
        }


    }


}
