package com.example.flashcards

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Button
import android.util.DisplayMetrics
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val paintView = findViewById<PaintView>(R.id.paintView)
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        paintView.init(metrics)
        addListenerOnNextButton()
        addListenerOnClearButton()
    }

//    fun repopulateRandomList(): List<Int> {
//
//    }

    private fun makeAndShuffleList(elements: Int): ArrayList<Int> {
        val list = ArrayList<Int>()
        for (i in 0..elements) {
            list.add(i)
        }
        list.shuffle()
        return list
    }

    private fun addListenerOnNextButton() {
        val button: Button? = findViewById(R.id.nextButton)
        val image: ImageView? = findViewById(R.id.hiraganaViewer)

        val icons = ArrayList<Int>()
        val arr = resources.obtainTypedArray(R.array.hiraganaCharacters)
        (0 until arr.length()).forEach {
            val icon = arr.getResourceId(it, -1)
            icons.add(icon)
        }

        val elements = arr.length() - 1

        arr.recycle()
        var list = makeAndShuffleList(elements)

        button?.setOnClickListener {
            if (list.size == 0) {
                list = makeAndShuffleList(elements)
            }
            image?.setImageResource(icons[list.removeAt(0)])
        }
    }

    private fun addListenerOnClearButton() {
        val button: Button? = findViewById(R.id.clearButton)
        button?.setOnClickListener {
            paintView.clear()
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        val menuInflater = menuInflater
//        menuInflater.inflate(R.menu.main, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.normal -> {
//                paintView.normal()
//                return true
//            }
//            R.id.clear -> {
//                paintView.clear()
//                return true
//            }
//        }
//
//        return super.onOptionsItemSelected(item)
//    }
}
