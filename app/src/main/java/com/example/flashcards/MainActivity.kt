package com.example.flashcards

import android.content.res.TypedArray
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Button
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList
import android.graphics.drawable.Drawable
import com.bumptech.glide.RequestBuilder



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val paintView = findViewById<PaintView>(R.id.paintView)
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        paintView.init(metrics)
//        GlideApp.with(this).load(R.raw.large_giphy_logo).into(giphyLogoView);
//        val gifItemRequest = GlideApp.with(this).asDrawable()
        addListenerOnClearButton()
    }

    //    fun repopulateRandomList(): List<Int> {
//
//    }
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
            image?.setImageResource(icons[list.removeAt(0)])

            image?.setOnClickListener {
                if (list.size == 0) {
                    list = makeAndShuffleList(elements)
                }
                image.setImageResource(icons[list.removeAt(0)])
                paintView.clear()

            }
        } else {
            image?.setImageResource(icons[index])

            image?.setOnClickListener {
                if (index < icons.size - 1) {
                    index++
                } else {
                    index = 0
                }
                image.setImageResource(icons[index])
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

    fun displayHiraganaCharacters() {
        displayArray = resources.obtainTypedArray(R.array.hiragana_characters)
    }

    fun displayHiraganaCharactersStrokes() {
        displayArray = resources.obtainTypedArray(R.array.hiragana_characters_stroke)
    }

    fun displayHiraganaCharactersGifs() {
        displayArray = resources.obtainTypedArray(R.array.hiragana_characters_stroke_gif)
    }

    fun displayKatakanaCharacters() {
        displayArray = resources.obtainTypedArray(R.array.katakana_characters)
    }

    fun displayKatakanaCharactersStrokes() {
        displayArray = resources.obtainTypedArray(R.array.katakana_characters_stroke)
    }

    fun displayKatakanaCharactersGifs() {
        displayArray = resources.obtainTypedArray(R.array.katakana_characters_stroke_gif)
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
                displayHiraganaCharacters()
                addListenerOnImageViewer()
                return true
            }
            R.id.hiraganaCharacterStrokes -> {
                displayHiraganaCharactersStrokes()
                addListenerOnImageViewer()
                return true
            }
            R.id.hiraganaCharacterGifs -> {
                displayHiraganaCharactersGifs()
                addListenerOnImageViewer()
                return true
            }
            R.id.katakanaCharacter -> {
                displayKatakanaCharacters()
                addListenerOnImageViewer()
                return true
            }
            R.id.katakanaCharacterStrokes -> {
                displayKatakanaCharactersStrokes()
                addListenerOnImageViewer()
                return true
            }
            R.id.katakanaCharacterGifs -> {
                displayKatakanaCharactersGifs()
                addListenerOnImageViewer()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}