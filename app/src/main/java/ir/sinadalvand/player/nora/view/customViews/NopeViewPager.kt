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
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent


class NopeViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ViewPager(context, attrs) {


    var fx: Float = -1f
    var maxright: Float = 0f
    var maxpos: Float = 0f
    var laspos = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return when (ev!!.action) {
            MotionEvent.ACTION_DOWN -> {
                fx = ev.x
                maxright = 0f
                laspos = fx
                maxpos = 0f
                super.onTouchEvent(ev)
            }

            MotionEvent.ACTION_MOVE -> {

//                Log.e("APP", "currentpage: " + currentItem + "   X: " + ev.x + "  Last x: " + fx + "   Y: " + ev.y)

                val x = ev.x
                if (adapter != null)
                    if (currentItem == this.adapter!!.count - 1) {
                        return false
                    } else if (currentItem == this.adapter!!.count - 2) {

                        maxright = Math.max(maxright, x - fx)
                        maxpos = if (maxright == x - fx) x else maxpos
//                        Log.e("MAX Right", "firstx: $fx   x: $x    maxPos : $maxpos     right: $maxright    limit: ${(maxpos - maxright)}")

                        if (x > fx + 80) {
                            return super.onTouchEvent(ev)
                        } else {
                            return false
                        }
                    } else {
                        return super.onTouchEvent(ev)
                    }
                else return super.onTouchEvent(ev)


            }

            MotionEvent.ACTION_UP -> {
                fx = -1f
                maxright = 0f
                laspos = 0f
                maxpos = 0f
                return super.onTouchEvent(ev)
            }

            else -> {
                return super.onTouchEvent(ev)
            }

        }


    }

}