package com.example.galleryv2
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataItem(val name:String, val url: String, var tags: String, val date:String):Parcelable {

}