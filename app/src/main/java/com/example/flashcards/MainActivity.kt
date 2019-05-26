package com.example.flashcards

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Button
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuInflater
import android.text.method.TextKeyListener.clear
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addListenerOnButton()
        var paintView = findViewById<PaintView>(R.id.paintView)
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        paintView.init(metrics)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.normal -> {
                paintView.normal()
                return true
            }
            R.id.clear -> {
                paintView.clear()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
