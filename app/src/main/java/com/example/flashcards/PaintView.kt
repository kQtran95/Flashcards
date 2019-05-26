package com.example.flashcards

import android.content.Context
import android.graphics.*
import android.graphics.Paint.DITHER_FLAG
import android.util.AttributeSet
import android.view.View
import java.util.ArrayList
import android.view.MotionEvent
import android.graphics.Bitmap
import android.util.DisplayMetrics



class PaintView (context: Context, attrs: AttributeSet? = null,
                                          private var mX: Float = 0.toFloat(), private var mY: Float = 0.toFloat()
) : View(context, attrs) {
    private var brushSize = 20
    private val defaultColor = Color.RED
    private var defaultBGColor = Color.WHITE
    private val touchTolerance = 4f
    private var mPath: Path? = null
    private var mPaint: Paint = Paint()
    private val paths = ArrayList<FingerPath>()
    private var currentColor: Int = 0
    private var strokeWidth: Int = 0
    private var emboss: Boolean = false
    private var blur: Boolean = false
    private var mBitmap: Bitmap? = null
    private var mCanvas: Canvas? = null
    private val mBitmapPaint = Paint(DITHER_FLAG)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0.toFloat(), 0.toFloat()) {
        mPaint.isAntiAlias = true
        mPaint.isDither = true
        mPaint.color = defaultColor
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeJoin = Paint.Join.ROUND
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.xfermode = null
        mPaint.alpha = 0xff
    }

    fun init(metrics: DisplayMetrics) {
        val height = metrics.heightPixels
        val width = metrics.widthPixels

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap)

        currentColor = defaultColor
        strokeWidth = brushSize
    }

    fun normal() {
        emboss = false
        blur = false
    }

    fun clear() {
        paths.clear()
        normal()
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.save()
        mCanvas!!.drawColor(defaultBGColor)

        for (fp in paths) {
            mPaint.color = fp.color
            mPaint.strokeWidth = fp.strokeWidth.toFloat()
            mPaint.maskFilter = null

            mCanvas!!.drawPath(fp.path, mPaint)

        }

        canvas.drawBitmap(mBitmap, 0.toFloat(), 0.toFloat(), mBitmapPaint)
        canvas.restore()
    }

    private fun touchStart(x: Float, y: Float) {
        mPath = Path()
        val fp = FingerPath(currentColor, strokeWidth, mPath!!)
        paths.add(fp)

        mPath!!.reset()
        mPath!!.moveTo(x, y)
        mX = x
        mY = y
    }

    private fun touchMove(x: Float, y: Float) {
        val dx = Math.abs(x - mX)
        val dy = Math.abs(y - mY)

        if (dx >= touchTolerance || dy >= touchTolerance) {
            mPath!!.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mX = x
            mY = y
        }
    }

    private fun touchUp() {
        mPath!!.lineTo(mX, mY)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStart(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(x, y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                touchUp()
                invalidate()
            }
        }

        return true
    }
}