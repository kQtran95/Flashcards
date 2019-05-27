package com.example.flashcards

import android.content.res.TypedArray
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Button
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
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
        addListenerOnClearButton()
    }

    private lateinit var displayArray: TypedArray
    private var menu: Menu? = null

    private fun makeAndShuffleList(elements: Int): ArrayList<Int> {
        val list = ArrayList<Int>()
        for (i in 0..elements) {
            list.add(i)
        }
        list.shuffle()
        return list
    }

    private fun addListenerOnImageViewer() {
        val image: ImageView? = findViewById(R.id.kanaViewer)
        
        val icons = ArrayList<Int>()
        (0 until displayArray.length()).forEach {
            val icon = displayArray.getResourceId(it, -1)
            icons.add(icon)
        }

        val elements = displayArray.length() - 1
        displayArray.recycle()
        var random = false
        var index = 0
        if (random) {
            var list = makeAndShuffleList(elements)
            image?.let { Glide.with(this).load(icons[list.removeAt(0)]).into(it) }
            image?.setOnClickListener {
                if (list.size == 0) {
                    list = makeAndShuffleList(elements)
                }
                Glide.with(this).load(icons[icons[list.removeAt(0)]]).into(image)
                paintView.clear()

            }
        } else {
            image?.let { Glide.with(this).load(icons[index]).into(it) }
            image?.setOnClickListener {
                if (index < icons.size - 1) {
                    index++
                } else {
                    index = 0
                }
                Glide.with(this).load(icons[index]).into(image)
                paintView.clear()
            }
        }
    }

    private fun addListenerOnClearButton() {
        val button: Button? = findViewById(R.id.clearButton)
        button?.setOnClickListener {
            paintView.clear()
        }
    }

    private fun displayArray(arrayLocation: Int) {
        displayArray = resources.obtainTypedArray(arrayLocation)
        addListenerOnImageViewer()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main, menu)
        this.menu = menu
        return super.onCreateOptionsMenu(menu)
    }

//    fun updateMenuTitles() {
//        val bedMenuItem = menu?.findItem(R.id.hiraganaCharacter)
//
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.hiraganaCharacter -> {
                displayArray(R.array.hiragana_characters)
                return true
            }
            R.id.hiraganaCharacterStrokes -> {
                displayArray(R.array.hiragana_characters_stroke)
                return true
            }
            R.id.hiraganaCharacterGifs -> {
                displayArray(R.array.hiragana_characters_stroke_gif)
                return true
            }
            R.id.katakanaCharacter -> {
                displayArray(R.array.katakana_characters)
                return true
            }
            R.id.katakanaCharacterStrokes -> {
                displayArray(R.array.katakana_characters_stroke)
                return true
            }
            R.id.katakanaCharacterGifs -> {
                displayArray(R.array.katakana_characters_stroke_gif)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}