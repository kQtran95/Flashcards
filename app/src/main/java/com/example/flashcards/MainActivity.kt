package com.example.flashcards

import android.content.res.TypedArray
import android.os.Bundle
import androidx.core.view.GestureDetectorCompat
import android.widget.ImageView
import android.widget.Button
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
	
	private var gestureDetectorCompat: GestureDetectorCompat? = null
	private var displayArray: TypedArray? = null
	private var romanjiArray: Array<String>? = null
	private val icons = ArrayList<Int>()
	private var menu: Menu? = null
	private var image: ImageView? = null
	private var textView: TextView? = null
	private val romanji = 0
	private val hiragana = 1
	private val katakana = 2
	private var currentView: Int = -1
	private var index = 0
	private var strings = false
	private val total = 47
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		val paintView = findViewById<PaintView>(R.id.paintView)
		image = findViewById(R.id.kanaViewer)
		textView = findViewById(R.id.romanjiViewer)
		
		val metrics = DisplayMetrics()
		windowManager.defaultDisplay.getMetrics(metrics)
		paintView.init(metrics)
		val gestureListener = CustomGestureListener()
		gestureListener.setActivity(this)
		gestureDetectorCompat = GestureDetectorCompat(this, gestureListener)
		addListenerOnClearButton()
	}
	
	private fun makeAndShuffleList(elements: Int): ArrayList<Int> {
		val list = ArrayList<Int>()
		for (i in 0..elements) {
			list.add(i)
		}
		list.shuffle()
		return list
	}
	
	override fun onTouchEvent(event: MotionEvent): Boolean {
		gestureDetectorCompat!!.onTouchEvent(event)
		return true
	}
	
	fun displayMessage(message: String) {
		val textView: TextView? = findViewById(R.id.romanjiViewer)
		textView!!.text = message
	}
	
	fun rightSwipe() {
		if (index == total)
			index = -1
		if (strings) nextCharacter(++index) else nextImage(++index)
	}
	
	fun leftSwipe() {
		if (index == 0)
			index = total + 1
		if (strings) nextCharacter(--index) else nextImage(--index)
	}
	
	fun upSwipe() {
		when (currentView) {
			romanji -> {
				displayArray = resources.obtainTypedArray(R.array.hiragana_characters)
				displayKana()
			}
			hiragana -> {
				displayArray = resources.obtainTypedArray(R.array.katakana_characters)
				displayKana()
			}
			katakana -> {
				displayArray = resources.obtainTypedArray(R.array.hiragana_characters)
				displayKana()
			}
		}
		
	}
	
	fun downSwipe() {
		when (currentView) {
			romanji -> {
				displayArray = resources.obtainTypedArray(R.array.katakana_characters)
				displayKana()
			}
			katakana, hiragana -> {
				image!!.visibility = View.GONE
				textView!!.visibility = View.VISIBLE
				romanjiArray = resources.getStringArray(R.array.characters_romanji)
				textView!!.text = (romanjiArray as Array<String>)[index]
			}
		}
	}
	
	fun oneTap() {
		if (currentView == romanji) {
			image!!.visibility = View.GONE
			textView!!.visibility = View.VISIBLE
		} else if (currentView == katakana || currentView == hiragana) {
			textView!!.visibility = View.GONE
			image!!.visibility = View.VISIBLE
			when (currentView) {
				katakana -> displayArray = resources.obtainTypedArray(R.array.katakana_characters)
				hiragana -> displayArray = resources.obtainTypedArray(R.array.hiragana_characters)
			}
			displayKana()
		}
	}
	
	fun twoTap() {
		//TODO display hiragana gif then katakana gif
	}
	
	fun displayKana() {
		image!!.visibility = View.VISIBLE
		textView!!.visibility = View.GONE
		
		val array = ArrayList<Int>()
		if (displayArray != null) {
			(0 until displayArray!!.length()).forEach {
				val icon = displayArray!!.getResourceId(it, -1)
				array.add(icon)
			}
		}
		image.let { Glide.with(this).load(array[index]).into(it!!) }
	}
	
	fun nextImage(index: Int) {
		textView!!.visibility = View.GONE
		image!!.visibility = View.VISIBLE
		if (icons.size > 0)
			image.let { Glide.with(this).load(icons[index]).into(it!!) }
		paintView.clear()
	}
	
	fun nextCharacter(index: Int) {
		image!!.visibility = View.GONE
		textView!!.visibility = View.VISIBLE
		romanjiArray = resources.getStringArray(R.array.characters_romanji)
		textView!!.text = (romanjiArray as Array<String>)[index]
		paintView.clear()
	}
	
	private fun addListenerOnClearButton() {
		val button: Button? = findViewById(R.id.clearButton)
		button?.setOnClickListener {
			paintView.clear()
		}
	}
	
	private fun displayImageArray(arrayLocation: Int) {
		displayArray = resources.obtainTypedArray(arrayLocation)
		if (displayArray != null) {
			icons.clear()
			(0 until displayArray!!.length()).forEach {
				val icon = displayArray!!.getResourceId(it, -1)
				icons.add(icon)
			}
		}
		nextImage(0)
	}
	
	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		val menuInflater = menuInflater
		menuInflater.inflate(R.menu.main, menu)
		this.menu = menu
		return super.onCreateOptionsMenu(menu)
	}
	
	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		index = 0
		strings = false
		when (item.itemId) {
			R.id.romanjiCharacter -> {
				strings = true
				currentView = romanji
				nextCharacter(0)
				return true
			}
			R.id.hiraganaCharacter -> {
				currentView = hiragana
				displayImageArray(R.array.hiragana_characters)
				return true
			}
			R.id.hiraganaCharacterStrokes -> {
				displayImageArray(R.array.hiragana_characters_stroke)
				return true
			}
			R.id.hiraganaCharacterGifs -> {
				displayImageArray(R.array.hiragana_characters_stroke_gif)
				return true
			}
			R.id.katakanaCharacter -> {
				currentView = katakana
				displayImageArray(R.array.katakana_characters)
				return true
			}
			R.id.katakanaCharacterStrokes -> {
				displayImageArray(R.array.katakana_characters_stroke)
				return true
			}
			R.id.katakanaCharacterGifs -> {
				displayImageArray(R.array.katakana_characters_stroke_gif)
				return true
			}
		}
		
		return super.onOptionsItemSelected(item)
	}
}