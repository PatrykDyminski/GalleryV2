package com.example.galleryv2

import android.content.Context
import android.content.Intent
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
import android.support.v4.content.ContextCompat.startActivity
import android.widget.Toast
import com.example.galleryv2.TouchHelpers.ItemTouchHelperAdapter
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


class MyAdapter(private val context: Context, private var listOfPhotos: ArrayList<DataItem>) :
    RecyclerView.Adapter<MyAdapter.DataHolder>(),
    ItemTouchHelperAdapter {

    override fun onItemDismiss(position: Int) {
        listOfPhotos.removeAt(position)
        //notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(listOfPhotos, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(listOfPhotos, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = listOfPhotos.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cell = layoutInflater.inflate(R.layout.data_view, parent, false)
        return DataHolder(cell)
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {

        holder.name.text = listOfPhotos[position].name
        holder.tags.text = listOfPhotos[position].tags.joinToString(" #", prefix = "#")
        holder.date.text = listOfPhotos[position].date

        val target: Target = MyTarget(holder, position)
        Picasso.get().load(listOfPhotos[position].url).into(target)

        onClickListener(holder, position)
    }

    private fun onClickListener(holder: DataHolder, position: Int) {
        holder.itemView.setOnClickListener {
            Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show()
            val intent = Intent(context, FragmentActivity::class.java)
            intent.putParcelableArrayListExtra(FragmentActivity.ARRAY, listOfPhotos)
            intent.putExtra(FragmentActivity.POSITION, position)
            startActivity(context, intent, null)
        }
    }

    fun processImageTagging(bitmap: Bitmap?, holder: DataHolder, position: Int) {
        val visionImg: FirebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap!!)
        val labeler: FirebaseVisionImageLabeler = FirebaseVision.getInstance().onDeviceImageLabeler

        labeler.processImage(visionImg).addOnSuccessListener { tags ->
            holder.tags.text = tags.joinToString(" #", prefix = "#") { it.text }

            var temp = ArrayList<String>()
            temp.addAll(tags.map { it.text })
            listOfPhotos[position].tags = temp

        }
            .addOnFailureListener { ex ->
                Log.wtf("LAB", ex)
            }
    }

    inner class MyTarget(val holder: DataHolder, val position: Int) : Target {
        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            holder.pic.setImageBitmap(bitmap)
            processImageTagging(bitmap, holder, position)
        }

        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            holder.pic.setImageResource(R.mipmap.merc)
        }

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            //holder.pic.setImageResource(R.mipmap.merc)
        }

    }

    class DataHolder(v: View) : RecyclerView.ViewHolder(v) {
        val name: TextView = itemView.findViewById(R.id.name_label)
        val pic: ImageView = itemView.findViewById(R.id.picture)
        val date: TextView = itemView.findViewById(R.id.date_label)
        val tags: TextView = itemView.findViewById(R.id.tags_label)
    }
}