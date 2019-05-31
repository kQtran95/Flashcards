package com.example.flashcards

import android.content.res.TypedArray
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GestureDetectorCompat
import android.widget.ImageView
import android.widget.Button
import android.util.DisplayMetrics
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
	
	private var gestureDetectorCompat: GestureDetectorCompat? = null
	private var displayArray: TypedArray? = null
	private var romanjiArray: Array<String>? = null
	private var menu: Menu? = null
	var index = 0
	var strings = false
	val total = 47
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		val paintView = findViewById<PaintView>(R.id.paintView)
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

//	private fun addListenerOnImageViewer() {
//		val image: ImageView? = findViewById(R.id.kanaViewer)
//
//		val icons = ArrayList<Int>()
//		(0 until displayArray.length()).forEach {
//			val icon = displayArray.getResourceId(it, -1)
//			icons.add(icon)
//		}
//
//		val elements = displayArray.length() - 1
//		displayArray.recycle()
//		var random = false
//		var index = 0
//		if (random) {
//			var list = makeAndShuffleList(elements)
//			image?.let { Glide.with(this).load(icons[list.removeAt(0)]).into(it) }
//			image?.setOnClickListener {
//				if (list.size == 0) {
//					list = makeAndShuffleList(elements)
//				}
//				Glide.with(this).load(icons[icons[list.removeAt(0)]]).into(image)
//				paintView.clear()
//
//			}
//		} else {
//			image?.let { Glide.with(this).load(icons[index]).into(it) }
//			image?.setOnClickListener {
//				if (index < icons.size - 1) {
//					index++
//				} else {
//					index = 0
//				}
//				Glide.with(this).load(icons[index]).into(image)
//				paintView.clear()
//			}
//		}
//	}
	
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
	
	fun nextImage(index: Int) {
		val textView: TextView? = findViewById(R.id.romanjiViewer)
		textView!!.visibility = View.GONE
		
		val image: ImageView? = findViewById(R.id.kanaViewer)
		image!!.visibility = View.VISIBLE
		val icons = ArrayList<Int>()
		if (displayArray != null) {
			(0 until displayArray!!.length()).forEach {
				val icon = displayArray!!.getResourceId(it, -1)
				icons.add(icon)
			}
			image?.let { Glide.with(this).load(icons[index]).into(it) }
			paintView.clear()
		}
	}
	
	fun nextCharacter(index: Int) {
		val image: ImageView? = findViewById(R.id.kanaViewer)
		image!!.visibility = View.GONE
		
		val textView: TextView? = findViewById(R.id.romanjiViewer)
		textView!!.visibility = View.VISIBLE
		romanjiArray = resources.getStringArray(R.array.characters_romanji)
		textView?.text = (romanjiArray as Array<String>)[index]
		paintView.clear()
	}

//	fun addListenerOnTextViewer() {
//		val textView: TextView? = findViewById(R.id.romanjiViewer)
//		val textArray = ArrayList<String>()
//		(0 until romanjiArray.size).forEach {
//			val romanji = romanjiArray[it]
//			textArray.add(romanji)
//		}
//
//		val elements = textArray.size - 1
//		var random = false
//		var index = 0
//		if (random) {
//			var list = makeAndShuffleList(elements)
//			textView?.text = textArray[list[index]]
//			textView?.setOnClickListener {
//				if (index < textArray.size - 1) {
//					index++
//				} else {
//					list = makeAndShuffleList(elements)
//					index = 0
//				}
//				textView.text = textArray[list[index]]
//				paintView.clear()
//
//			}
//		} else {
//			textView?.text = textArray[index]
//			textView?.setOnClickListener {
//				if (index < textArray.size - 1) {
//					index++
//				} else {
//					index = 0
//				}
//				textView.text = textArray[index]
//				paintView.clear()
//			}
//		}
//	}
	
	private fun addListenerOnClearButton() {
		val button: Button? = findViewById(R.id.clearButton)
		button?.setOnClickListener {
			paintView.clear()
		}
	}
	
	private fun displayImageArray(arrayLocation: Int) {
		displayArray = resources.obtainTypedArray(arrayLocation)
		nextImage(0)
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
		index = 0
		strings = false
		when (item.itemId) {
			R.id.romanjiCharacter -> {
				strings = true
				nextCharacter(0)
				return true
			}
			R.id.hiraganaCharacter -> {
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