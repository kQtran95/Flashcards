package com.example.flashcards

import android.view.MotionEvent
import android.view.GestureDetector


class CustomGestureListener : GestureDetector.SimpleOnGestureListener() {
	
	// Source activity that display message in text view.
	private var activity: MainActivity? = null
	var index = 0
	
	fun setActivity(activity: MainActivity) {
		this.activity = activity
	}
	
	/* This method is invoked when a swipe gesture happened. */
	override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
		// Get swipe delta value in x axis.
		val deltaX = e1.x - e2.x
		
		// Get swipe delta value in y axis.
		val deltaY = e1.y - e2.y
		
		// Get absolute value.
		val deltaXAbs = Math.abs(deltaX)
		val deltaYAbs = Math.abs(deltaY)
		
		// Only when swipe distance between minimal and maximal distance value then we treat it as effective swipe
		if (deltaXAbs in 100.0..1000.0) {
			if (deltaX > 0) {
				this.activity!!.rightSwipe()
			} else {
				this.activity!!.leftSwipe()
			}
		}
		
		if (deltaYAbs in 200.0..1000.0) {
			if (deltaY > 0) {
				this.activity!!.upSwipe()
			} else {
				this.activity!!.downSwipe()
			}
		}
		
		return true
	}
	
	// Invoked when single tap screen.
	override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
//		this.activity!!.displayMessage("Single tap occurred.")
		this.activity!!.oneTap()
		return true
	}
	
	// Invoked when double tap screen.
	override fun onDoubleTap(e: MotionEvent): Boolean {
		this.activity!!.displayMessage("Double tap occurred.")
		return true
	}
}