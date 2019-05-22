package com.example.galleryv2.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.example.galleryv2.R
import com.squareup.picasso.Picasso


class BigPhotoFragment : Fragment() {

    companion object {

        private const val KEY = "data"

        @JvmStatic
        fun newInstance(url: String) =
            BigPhotoFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY, url)
                }
            }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view  = inflater.inflate(R.layout.fragment_big_photo, container, false)
        val photo = view.findViewById<ImageView>(R.id.obrazek)

        Picasso.get().load(arguments?.getString(KEY)).into(photo)

        return view
    }



}
