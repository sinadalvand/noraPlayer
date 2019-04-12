/*
 *  Copyright (c)  2018 by Sina Dalvand
 *  All Rights Reserved.
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  All Classes & Files Created for CoolPlayer project
 *  This file created by LOGAN Username
 */

package ir.sinadalvand.player.nora.view.Utils

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.Transformation

object CoolAnimationUtils {

    class ResizeAnimation(val view: View, val width: Int = -1, val height: Int = -1) : Animation() {

        init {
            duration = 500
        }

        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            val height = if (height < 0) view.width else ((height - view.height) * interpolatedTime + view.height).toInt()
            val width = if (width < 0) view.width else ((width - view.width) * interpolatedTime + view.width).toInt()
            val p = view.layoutParams
            p.height = height
            p.width = width
            view.requestLayout()
        }
    }


    fun runMultiAnimation( vararg anims: Animation): AnimationSet {
        val animations = AnimationSet(false)
        for (anim in anims) {
            animations.addAnimation(anim)
        }
        animations.duration =500
        return animations
    }
}