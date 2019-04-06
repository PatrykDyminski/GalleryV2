package com.example.galleryv2

import android.content.Context
import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import android.graphics.drawable.Drawable
import java.lang.Exception
import java.util.*
import java.util.Collections.swap




class MyAdapter(private val context: Context, private var history: ArrayList<DataItem>): RecyclerView.Adapter<MyAdapter.DataHolder>(),ItemTouchHelperAdapter {

    override fun onItemDismiss(position: Int) {
        history.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(history, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(history, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
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

        val target: Target = MyTarget(holder)
        Picasso.get().load(history[position].url).into(target)
    }

    fun processImageTagging(bitmap:Bitmap?, holder: DataHolder){
        val visionImg: FirebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap!!)
        val labeler: FirebaseVisionImageLabeler = FirebaseVision.getInstance().onDeviceImageLabeler

        labeler.processImage(visionImg).addOnSuccessListener { tags ->
            holder.tags.text = tags.joinToString(", ") { it.text }
        }
        .addOnFailureListener { ex ->
            Log.wtf("LAB",ex)
        }
    }

    inner class MyTarget(val holder:DataHolder): Target{
        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            holder.pic.setImageBitmap(bitmap)
            processImageTagging(bitmap,holder)
        }

        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            holder.pic.setImageResource(R.mipmap.merc)
        }

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            //holder.pic.setImageResource(R.mipmap.merc)
        }

    }

    class DataHolder(v: View): RecyclerView.ViewHolder(v){
        val name: TextView = itemView.findViewById(R.id.name_label)
        val pic: ImageView = itemView.findViewById(R.id.picture)
        val date: TextView = itemView.findViewById(R.id.date_label)
        val tags: TextView = itemView.findViewById(R.id.tags_label)
    }
}