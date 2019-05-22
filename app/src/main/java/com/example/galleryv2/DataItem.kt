package com.example.galleryv2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataItem(val name: String, val url: String, var tags: ArrayList<String> = ArrayList(), val date: String) :
    Parcelable {

}