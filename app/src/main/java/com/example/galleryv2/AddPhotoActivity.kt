package com.example.galleryv2

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_photo.*

class AddPhotoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_photo)

        confirm_button.setOnClickListener { onConfirmClicked() }
    }

    private fun onConfirmClicked() {

        val name = name_field.text.toString()
        val url = url_field.text.toString()
        val tags = tags_field.text.toString()

        //Toast.makeText(this, name, Toast.LENGTH_SHORT).show()

        val intent = Intent(this, MainActivity::class.java)


        val bundle = Bundle()

        bundle.putString("name",name)
        bundle.putString("url",url)
        bundle.putString("tags",tags)

        intent.putExtras(bundle)

        setResult(Activity.RESULT_OK, intent)
        finish()
    }

}