/*
 *  Copyright (c)  2018 by Sina Dalvand
 *  All Rights Reserved.
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  All Classes & Files Created for CoolPlayer project
 *  This file created by LOGAN Username
 */

package ir.sinadalvand.player.nora.view.customViews

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import ir.sinadalvand.player.nora.R


class ArcSeek @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0,
        defStyleRes: Int = 0
) : View(context, attrs, defStyle) {

    private var morph = 0f
    private var progress = 0;
    private final val START_ANGLE = 40
    private var mMode = SeekMode.ARC
    private var percent = 40.toDouble()
    val thumb: Drawable = resources.getDrawable(R.drawable.thumb2)

    fun setMode(mode: SeekMode) {
        when (mode) {
            SeekMode.ARC -> setMorph(0f)
            SeekMode.LINE -> setMorph(1f)
            else -> {
            }
        }
        mMode = mode
    }

    fun getMode(): SeekMode {
        return mMode
    }

    fun setMorph(morph: Float) {
        this.morph = morph
        mMode = when (morph) {
            0f -> SeekMode.ARC

            1f -> SeekMode.LINE

            else -> SeekMode.IDE
        }
        invalidate()
    }

    fun setProgress(percent: Int) {
        progress = percent
        invalidate()
    }

    fun getProgress(): Int {
        return progress
    }


    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.STROKE
        paint.strokeCap = Paint.Cap.ROUND


        val diffmorph = 1 - morph
        val rad = (8 * morph) + (height.toFloat() * diffmorph) + paddingTop

        val color = getProgressColor(context.resources.getColor(R.color.secondaryBackRed), context.resources.getColor(R.color.colorPrimary), morph)
        val color2 = getProgressColor(context.resources.getColor(R.color.colorPrimary), context.resources.getColor(R.color.secondaryBackRed), morph)


        paint.color = Color.parseColor("#FFFFFF")
        paint.strokeWidth = 7f
        canvas?.drawArc(RectF(0f, paddingTop.toFloat(), width.toFloat(), rad), -140f, 100f, false, paint)



        paint.color = color
        paint.strokeWidth = 8f
        canvas?.drawArc(RectF(0f, paddingTop.toFloat(), width.toFloat(), rad), -140f, progress.toFloat(), false, paint)


        val perc = if (progress <= 50) START_ANGLE + progress else 90 - (progress - 50)


        val hope = Angle(rad / 2, perc.toDouble())

//        Log.e("Angle:", "X: " + hope[0] + "Fake: ${((Math.cos(40.0)) * (height/2))}        Y: " + hope[1] + "     Rad : " + rad)


        val thumb: Drawable = resources.getDrawable(R.drawable.thumb2)
        val thumbHalfHeight = thumb.intrinsicHeight / 2
        val thumbHalfWidth = thumb.intrinsicWidth / 2


        val outterCircle = thumb as LayerDrawable

        val circle1: GradientDrawable = outterCircle.findDrawableByLayerId(R.id.progress_toggle_circle1) as GradientDrawable
        val circle2: GradientDrawable = outterCircle.findDrawableByLayerId(R.id.progress_toggle_circle2) as GradientDrawable
        val circle3: GradientDrawable = outterCircle.findDrawableByLayerId(R.id.progress_toggle_circle3) as GradientDrawable
        circle1.setColor(color)
        circle2.setColor(color2)
        circle3.setColor(color)


//        val tops = ((height / 2) * diffmorph).toInt() - hope[1].toInt() + (paddingTop * diffmorph).toInt() + thumbHalfHeight + 4
        val tops = (rad / 2).toInt() + paddingTop - hope[1].toInt() - thumbHalfHeight - 5 //+ paddingTop


        val cope = Angle(width.toFloat() / 2, perc.toDouble())
        val lefts = if (progress <= 50) (width / 2) - cope[0].toInt() - thumbHalfWidth else (width / 2) + cope[0].toInt() - thumbHalfWidth


        thumb.setBounds(lefts, tops, lefts + thumbHalfWidth * 2, tops + thumbHalfHeight * 2)
        thumb.draw(canvas!!)

        Log.e("APP", "Draw : "+alpha)

        super.onDraw(canvas)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {

            MotionEvent.ACTION_DOWN -> {
                isPressed = true
            }

            MotionEvent.ACTION_MOVE -> {
                isPressed = true
                    if (mMode == SeekMode.ARC) {

                        if (isinCirclarArea(event)) convertTouchEventPointToAngle(event.x, event.y)
                    } else if (mMode == SeekMode.LINE) {
                        isinLinearArea(event)
                    }

                return true
            }

            MotionEvent.ACTION_UP -> {
                isPressed = false
            }


            else -> super.onTouchEvent(event)

        }
        return true
    }


    private fun isinLinearArea(event: MotionEvent): Boolean {


        val data = Angle(width / 2.toFloat(), 40.0)

        val skip = width / 2 - data[0]
        val total = width - (2 * skip)



        if (event.y < thumb.intrinsicHeight * 2 && event.x > skip - 15 && event.x < width - skip + 15) {

            val datsa = ((total - event.x + skip) * 100) / total
            if (datsa in 0.0..100.0) setProgress(100 - datsa.toInt())

            return true
//            Log.e("Progress", "Data: " + datsa)
        }
        return false
    }

    private fun isinCirclarArea(event: MotionEvent): Boolean {
        val size = Math.max(height, width)
        return contains(event.x, event.y, (size / 2).toFloat(), (size * 10 / 33).toFloat())
    }

    internal fun contains(x: Float, y: Float, big_rad: Float, small_rad: Float): Boolean {


        val centerX = width / 2
        val centerY = (height / 2)
        val distanceX = x - centerX
        val distanceY = y - centerY

        return distanceX * distanceX + distanceY * distanceY <= big_rad * big_rad && distanceX * distanceX + distanceY * distanceY >= small_rad * small_rad && y < height / 4
    }


    private fun Angle(rad: Float, angle: Double): FloatArray {
//        val degree = Math.atan((H / W).toDouble())
        val skipY = Math.sin(Math.toRadians(angle)).toFloat() * rad
        val skipX = Math.abs(Math.cos(Math.toRadians(angle)) * rad).toFloat()
        return floatArrayOf(skipX, skipY)
    }


    fun getProgressColor(startColor: Int, endColor: Int, progress: Float): Int {
        val alpha = getValue(startColor and -0x1000000 shr 24, endColor and -0x1000000 shr 24, progress)
        val red = getValue(startColor and 0x00FF0000 shr 16, endColor and 0x00FF0000 shr 16, progress)
        val green = getValue(startColor and 0x0000FF00 shr 8, endColor and 0x0000FF00 shr 8, progress)
        val blue = getValue(startColor and 0x000000FF, endColor and 0x000000FF, progress)
        return alpha shl 24 or (red shl 16) or (green shl 8) or blue

    }

    private fun getValue(start: Int, end: Int, progress: Float): Int {
        return start + ((end - start) * progress).toInt()
    }

    class ToggleDrawable(val context: Context) : Drawable() {

        private val paint1: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
        private val paint2: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
        private var boundse = Rect()

        init {
            paint1.color = Color.WHITE
            paint2.color = Color.BLACK
        }

        override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
            super.setBounds(left, top, right, bottom)
        }


        //        override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
//            super.setBounds(0, 0, dp2px(20f).toInt(),dp2px(20f).toInt())
//            boundse = bounds
//        }

        override fun draw(canvas: Canvas) {
            boundse = bounds
            canvas.drawCircle(boundse.right / 2.toFloat(), boundse.bottom / 2.toFloat(), boundse.right / 2.toFloat(), paint1)
            canvas.drawCircle(boundse.right / 2.toFloat(), boundse.bottom / 2.toFloat(), boundse.right / 3.toFloat(), paint2)
            canvas.drawCircle(boundse.right / 2.toFloat(), boundse.bottom / 2.toFloat(), boundse.right / 4.toFloat(), paint1)

        }

        override fun setAlpha(alpha: Int) {

        }

        override fun getOpacity(): Int {
            return this.opacity
        }

        override fun setColorFilter(filter: ColorFilter?) {

        }

        public fun setColors(primary: Int, secondprimary: Int) {
            paint1.color = primary
            paint2.color = secondprimary
            invalidateSelf()

        }


    }


    var percents = 0;
    private fun convertTouchEventPointToAngle(xPos: Float, yPos: Float): Double {
        // transform touch coordinate into component coordinate
        val postion = Math.max(width, height)
        var x = xPos - (postion / 2)
        val y = yPos - (postion / 2)


        x = if (x > width / 2) -x else x
        var angle = Math.toDegrees(Math.atan2(y.toDouble(), x.toDouble()) + Math.PI / 2)
//        angle = if (angle < 0) angle + 360 else angle
        angle = 90 - Math.abs(angle)


        val percents = if (xPos < width / 2) angle - 40 else 140 - angle
        if (percents in 0..100) setProgress(percents.toInt())
        Log.e("Angle", "Angle is : $progress")

        return angle
    }


    private fun px2dp(px: Float): Float {
        return px / context.resources.displayMetrics.density
    }

    private fun dp2px(dp: Float): Float {
        return dp * context.resources.displayMetrics.density
    }


    public enum class SeekMode {
        ARC, IDE, LINE
    }
}