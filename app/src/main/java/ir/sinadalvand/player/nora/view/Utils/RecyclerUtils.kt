/*
 *  Copyright (c)  2018 by Sina Dalvand
 *  All Rights Reserved.
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  All Classes & Files Created for CoolPlayer project
 *  This file created by LOGAN Username
 */

package ir.sinadalvand.player.nora.view.Utils

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import ir.sinadalvand.player.nora.R

object RecyclerUtils {

    fun getDivider(context: Context):RecyclerView.ItemDecoration{
        val myDivider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        myDivider.setDrawable(ContextCompat.getDrawable(context, R.drawable.recycler_horizontal_divider)!!)
        return myDivider;
    }
}