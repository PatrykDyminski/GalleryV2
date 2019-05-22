package com.example.galleryv2.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.galleryv2.DataItem

import com.example.galleryv2.R
import com.squareup.picasso.Picasso

class SimilarsFragment : Fragment() {

    private lateinit var data: ArrayList<DataItem>

    companion object {
        private const val DATA_KEY = "data"
        private const val LIST_KEY = "test"

        @JvmStatic
        fun newInstance(element: DataItem, array: ArrayList<DataItem>) =
            SimilarsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(DATA_KEY, element)
                    putParcelableArrayList(LIST_KEY, array)
                }
            }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_similars, container, false)

        val element = arguments?.getParcelable<DataItem>(DATA_KEY)
        val list = arguments?.getParcelableArrayList<DataItem>(LIST_KEY)

        val img1 = view.findViewById<ImageView>(R.id.img1)
        val img2 = view.findViewById<ImageView>(R.id.img2)
        val img3 = view.findViewById<ImageView>(R.id.img3)
        val img4 = view.findViewById<ImageView>(R.id.img4)
        val img5 = view.findViewById<ImageView>(R.id.img5)
        val img6 = view.findViewById<ImageView>(R.id.img6)

        val imgList: ArrayList<ImageView> = arrayListOf(img1, img2, img3, img4, img5, img6)

        val similars = getSimilars(list!!, element!!)

        for (i in 0 until similars.size) {
            Picasso.get().load(similars[i].url).into(imgList[i])
        }

        return view
    }

    private fun getSimilars(data: ArrayList<DataItem>, element: DataItem): List<DataItem> {
        val index = data.indexOf(element)
        val result = data
        result.removeAt(index)
        return result.filter { !it.tags.intersect(element.tags).isEmpty() }
    }
}
