/*
 *  Copyright (c)  2018 by Sina Dalvand
 *  All Rights Reserved.
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  All Classes & Files Created for CoolPlayer project
 *  This file created by LOGAN Username
 */

package ir.sinadalvand.player.nora.view.customViews

import android.content.Context
import android.graphics.*
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet

class ArcLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var morph = 0.toFloat()

    fun setMorph(morph: Float) {
        this.morph = morph
        invalidate()
    }


    fun getMorph(): Float {
        return morph
    }

    override fun dispatchDraw(canvas: Canvas?) {

        val diffMorph = 1 - morph
        val topmarg =  (height / 19 * diffMorph);

        val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.parseColor("#FF294F")
        val path: Path = Path()
        path.addArc(RectF(0f, topmarg, width.toFloat(), ((height * 10 / 17) * diffMorph)+topmarg), 0f, -180f)
        path.addRect(RectF(0f, ((height * 10 / (17 * 2))* diffMorph)+topmarg, width.toFloat(), height.toFloat()), Path.Direction.CCW)

        canvas!!.drawPath(path, paint)

        super.dispatchDraw(canvas)
    }
}