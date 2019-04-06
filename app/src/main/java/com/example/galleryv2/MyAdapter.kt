package com.example.galleryv2

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso

class MyAdapter(private val context: Context, private var history: ArrayList<DataItem>): RecyclerView.Adapter<MyAdapter.DataHolder>(),ItemTouchHelperAdapter {

    override fun onItemDismiss(position: Int) {
        history.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {

    }

    override fun getItemCount(): Int = history.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cell = layoutInflater.inflate(R.layout.data_view, parent,false)
        return DataHolder(cell)
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {

        holder.name.text = history[position].name
        holder.tags.text = history[position].tags
        holder.date.text = "data noo"
        //holder.pic.setImageResource(R.mipmap.merc)
        Picasso.get().load(history[position].url).into(holder.pic)


    }


    class DataHolder(v: View): RecyclerView.ViewHolder(v){

        val name: TextView = itemView.findViewById(R.id.name_label)
        val pic: ImageView = itemView.findViewById(R.id.picture)
        val date: TextView = itemView.findViewById(R.id.date_label)
        val tags: TextView = itemView.findViewById(R.id.tags_label)


    }
}