package com.example.galleryv2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.example.galleryv2.Fragments.BigPhotoFragment
import com.example.galleryv2.Fragments.DetailsFragment
import com.example.galleryv2.Fragments.SimilarsFragment

class FragmentActivity : AppCompatActivity() {
    companion object {
        const val ARRAY = "lista"
        const val POSITION = "pos"
    }

    private lateinit var fragmentManager: FragmentManager
    private lateinit var bigPhotoFragment: Fragment
    private lateinit var detailsFragment: Fragment
    private lateinit var similarsFragment: Fragment
    private var isSwapped:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        fragmentManager = supportFragmentManager

        val data = intent.getParcelableArrayListExtra<DataItem>(ARRAY)
        val position = intent.getIntExtra(POSITION, 0)

        bigPhotoFragment = BigPhotoFragment.newInstance(data[position].url)
        detailsFragment = DetailsFragment.newInstance(data[position])
        similarsFragment = SimilarsFragment.newInstance(data[position], data)

        fragmentManager.beginTransaction().add(R.id.activity_fragment, bigPhotoFragment, "photo").commit()
        fragmentManager.beginTransaction().add(R.id.activity_fragment, detailsFragment, "details").commit()
        fragmentManager.beginTransaction().add(R.id.activity_fragment, similarsFragment, "similars").commit()
        fragmentManager.beginTransaction().hide(detailsFragment).commit()
        fragmentManager.beginTransaction().hide(similarsFragment).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.swap_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.swap -> {
                swapFragments()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun swapFragments() {
        if (isSwapped){
            fragmentManager.beginTransaction().hide(detailsFragment).commit()
            fragmentManager.beginTransaction().hide(similarsFragment).commit()
            fragmentManager.beginTransaction().show(bigPhotoFragment).commit()
            isSwapped = !isSwapped

        } else{
            fragmentManager.beginTransaction().hide(bigPhotoFragment).commit()
            fragmentManager.beginTransaction().show(detailsFragment).commit()
            fragmentManager.beginTransaction().show(similarsFragment).commit()
            isSwapped = !isSwapped
        }

    }


}
