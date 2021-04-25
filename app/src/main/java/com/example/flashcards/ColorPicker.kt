package com.example.flashcards

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.ColorPickerView
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import com.skydoves.colorpickerview.sliders.AlphaSlideBar
import com.skydoves.colorpickerview.sliders.BrightnessSlideBar

//TODO ADD BLACK COLOR
class ColorPicker : AppCompatActivity() {
	private var colorPickerView: ColorPickerView? = null
	private val FLAG_PALETTE = false
	private val FLAG_SELECTOR = false
	private var hexCode: String = ""
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_color_picker)
		
		colorPickerView = findViewById(R.id.colorPickerView)
		colorPickerView?.setColorListener(
			ColorEnvelopeListener { envelope, fromUser ->
				setLayoutColor(envelope)
			})
		
		var alphaSlideBar: AlphaSlideBar = findViewById(R.id.alphaSlideBar)
		colorPickerView?.attachAlphaSlider(alphaSlideBar)
	
		val brightnessSlideBar: BrightnessSlideBar = findViewById(R.id.brightnessSlide)
		colorPickerView?.attachBrightnessSlider(brightnessSlideBar)
		colorPickerView?.setLifecycleOwner(this)
	}
	
	private fun setLayoutColor(envelope: ColorEnvelope) {
		val textView: TextView? = findViewById(R.id.ColorSample)
		hexCode = envelope.hexCode
		textView?.setBackgroundColor(envelope.color)
	}
	
	private fun dialog() {
		val builder = ColorPickerDialog.Builder(this, R.style.AppTheme)
			.setTitle("ColorPicker Dialog")
			.setPreferenceName("Test")
			.setPositiveButton(
				getString(R.string.confirm),
				ColorEnvelopeListener { envelope, fromUser -> setLayoutColor(envelope) })
			.setNegativeButton(
				getString(R.string.cancel)
			) { dialogInterface, i -> dialogInterface.dismiss() }
		builder.show()
	}
	
	fun returnColor(view: View) {
		Log.d("COLORPICKER", hexCode.substring(2))
		val resultIntent = Intent()
		resultIntent.putExtra("colorChoiceHex", hexCode.substring(2))
//		resultIntent.putExtra("colorChoiceHex", hexCode)
		setResult(Activity.RESULT_OK, resultIntent)
		finish()
	}
	
}
