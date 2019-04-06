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
import android.widget.Toast
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

            val bundle = data.extras

            val name = bundle.getString("name")
            val url = bundle.getString("url")
            val tags = bundle.getString("tags")

            val item = DataItem(name,url,tags)

            myDataset.add(item)

            viewAdapter.notifyDataSetChanged()

            Toast.makeText(this, url, Toast.LENGTH_SHORT).show()

        }
    }
}
