/*
 *  Copyright (c)  2018 by Sina Dalvand
 *  All Rights Reserved.
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  All Classes & Files Created for CoolPlayer project
 *  This file created by LOGAN Username
 */

package ir.sinadalvand.player.nora.view.Utils

import android.content.Context

object Utils {


    fun getAttr(context: Context, attr: Int):Any {
        val styledAttributes = context.theme.obtainStyledAttributes(intArrayOf(attr))
        val mActionBarSize = styledAttributes.getDimension(0, 0f).toInt()
        styledAttributes.recycle()
        return mActionBarSize
    }
}