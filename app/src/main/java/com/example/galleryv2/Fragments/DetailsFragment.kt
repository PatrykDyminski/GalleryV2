package com.example.galleryv2.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.galleryv2.DataItem

import com.example.galleryv2.R


class DetailsFragment : Fragment() {

    companion object {
        const val DATA_KEY = "data"
        @JvmStatic
        fun newInstance(element: DataItem) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(DATA_KEY, element)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        val name = view.findViewById<TextView>(R.id.name)
        val date = view.findViewById<TextView>(R.id.date)
        val tags = view.findViewById<TextView>(R.id.tags)

        val element = arguments?.getParcelable<DataItem>(DATA_KEY)
        val data = arguments?.getParcelableArrayList<DataItem>("test")

        name.text = element?.name
        date.text = element?.date

        val tgs = element?.tags!!.joinToString(" #", prefix = "#")
        tags.text = tgs

        return view
    }


}
