package com.example.galleryv2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.example.galleryv2.Fragments.BigPhotoFragment

class FragmentActivity : AppCompatActivity() {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var bigPhotoFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        fragmentManager = supportFragmentManager

        val data = intent.getParcelableArrayListExtra<DataItem>("lista")
        val position = intent.getIntExtra("pos", 0)

        bigPhotoFragment = BigPhotoFragment.newInstance(data[position].url)
        fragmentManager.beginTransaction().add(R.id.activity_fragment, bigPhotoFragment, "photo").commit()
    }
}
