package com.fauzan.restoapp.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fauzan.restoapp.Activity.DetailActivity
import com.fauzan.restoapp.Model.RestoModel
import com.fauzan.restoapp.databinding.ViewholderRestaurantBinding

class RestaurantAdapter(private val items:List<RestoModel>): RecyclerView.Adapter<RestaurantAdapter.Viewholder>() {
    private lateinit var context:Context

    inner class Viewholder(val binding:ViewholderRestaurantBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RestaurantAdapter.Viewholder {
        context = parent.context
        val binding = ViewholderRestaurantBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: RestaurantAdapter.Viewholder, position: Int) {
        val item = items[position]
        holder.binding.restaurantTxt.text=item.restaurant
        holder.binding.descriptionTxt.text=item.description
        holder.binding.locationTxt.text=item.location
        holder.binding.timeTxt.text=item.time
        holder.binding.categoryTxt.text=item.category
        holder.binding.typeTxt.text=item.type
        holder.binding.pricetxt.text=item.price

        val drawableResourceId = holder.itemView.resources
            .getIdentifier(item.picUrl,"drawable",holder.itemView.context.packageName)
        Glide.with(holder.itemView.context)
            .load(drawableResourceId)
            .into(holder.binding.pic)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("object", item)
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = items.size
}