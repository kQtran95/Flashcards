package com.example.flashcards

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.RelativeLayout


class SwippeableRelativeLayout : RelativeLayout {
    private var mGestureDetector: GestureDetector? = null

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return mGestureDetector!!.onTouchEvent(event)
    }

    fun setGestureDetector(gestureDetector: GestureDetector) {
        mGestureDetector = gestureDetector
    }
}