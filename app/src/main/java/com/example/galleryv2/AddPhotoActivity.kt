package com.example.galleryv2

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.android.synthetic.main.activity_add_photo.*

class AddPhotoActivity : AppCompatActivity() {

    companion object {
        const val KEY = "replying"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_photo)

        confirm_button.setOnClickListener { onConfirmClicked() }
    }

    private fun onConfirmClicked() {

        val name = name_field.text.toString()
        val url = url_field.text.toString()
        val intent = Intent(this, MainActivity::class.java)
        val date = SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss", Locale.getDefault()).format(Date())

        intent.putExtra(KEY ,DataItem(name, url, "", date))
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}