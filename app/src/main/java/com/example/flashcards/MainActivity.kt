package com.example.flashcards

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Button
import android.support.v4.content.ContextCompat
import android.graphics.drawable.Drawable
import android.view.View


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addListenerOnButton();
    }
    private fun addListenerOnButton() {
        val button: Button? = findViewById(R.id.startButton)
        val image: ImageView? = findViewById(R.id.hiraganaViewer)

        val icons = ArrayList<Int>()
        val arr = resources.obtainTypedArray(R.array.hiraganaCharacters)
        (0 until arr.length()).forEach {
            // get resource id of each drawable
            val icon = arr.getResourceId(it, -1)
            icons.add(icon)
        }

        arr.recycle()
        var index = 0
        button?.setOnClickListener {
            image?.setImageResource(icons[index])
            if (index < icons.size - 1) {
                index++
            } else {
                index = 0
            }
        }

    }
}
