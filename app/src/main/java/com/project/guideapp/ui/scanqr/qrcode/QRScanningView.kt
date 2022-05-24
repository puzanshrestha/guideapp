package com.project.guideapp.ui.scanqr.qrcode

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import com.project.guideapp.R

class QRScanningView : View {
    private val paint = Paint()
    private var mPosY = 0
    private var runAnimation = true
    private var showLine = true
    private var refreshRunnable: Runnable? = null
    private var isGoingDown = true
    private var mHeight = 0
    private val DELAY = 1

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        paint.color = resources.getColor(R.color.teal)
        paint.strokeWidth = 5.0f //make sure add stroke width otherwise line not display
        refreshRunnable = Runnable { refreshView() }
    }

    public override fun onDraw(canvas: Canvas) {
        mHeight = canvas.height
        if (showLine) {
            canvas.drawLine(0f, mPosY.toFloat(), canvas.width.toFloat(), mPosY.toFloat(), paint)
        }
        if (runAnimation) {
            Handler(Looper.getMainLooper()).postDelayed(refreshRunnable!!, DELAY.toLong())
        }
    }

    fun startAnimation() {
        runAnimation = true
        showLine = true
        this.invalidate()
    }

    fun stopAnimation() {
        runAnimation = false
        showLine = false
        reset()
        this.invalidate()
    }

    private fun reset() {
        mPosY = 0
        isGoingDown = true
    }

    private fun refreshView() {
        //Update new position of the line
        println("mPOSY: $mPosY")
        if (isGoingDown) {
            mPosY += 5
            if (mPosY > mHeight) {
                //We invert the direction of the animation
                mPosY = mHeight
                isGoingDown = false
            }
        } else {
            mPosY -= 5
            if (mPosY < 0) {
                //We invert the direction of the animation
                mPosY = 0
                isGoingDown = true
            }
        }
        this.invalidate()
    }
}