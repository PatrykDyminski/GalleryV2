package com.example.galleryv2

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.example.galleryv2.TouchHelpers.ItemTouchHelperCallback
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    val myDataset: ArrayList<DataItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewManager = LinearLayoutManager(this)
        viewAdapter = MyAdapter(this,myDataset)

        recyclerView = my_recycler_view.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        val callback = ItemTouchHelperCallback(viewAdapter as MyAdapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recyclerView)

        myDataset.add(DataItem("elo","http://i.imgur.com/DvpvklR.png","","jakas data"))
        myDataset.add(DataItem("elo320","https://ocs-pl.oktawave.com/v1/AUTH_2887234e-384a-4873-8bc5-405211db13a2/spidersweb/2019/05/oneplus-7-4.jpg","","inna data"))
        myDataset.add(DataItem("elo","https://ocs-pl.oktawave.com/v1/AUTH_2887234e-384a-4873-8bc5-405211db13a2/spidersweb/2019/05/Logitech-G935-Wireless-1.jpg","","jeszcze inna"))
        viewAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.dot_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_photo_menu -> {
                addPhoto()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addPhoto():Boolean {
        val intent = Intent(this,AddPhotoActivity::class.java)
        startActivityForResult(intent,1)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1 && resultCode == Activity.RESULT_OK && data!=null){

            val item:DataItem? = data?.getParcelableExtra(AddPhotoActivity.KEY)
            myDataset.add(item!!)
            viewAdapter.notifyDataSetChanged()
        }
    }
}
