/*
 *  Copyright (c)  2018 by Sina Dalvand
 *  All Rights Reserved.
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  All Classes & Files Created for CoolPlayer project
 *  This file created by LOGAN Username
 */

package ir.sinadalvand.player.nora.view.Activities

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ir.sinadalvand.player.nora.R


class Test : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

//        val pallet = Palette.from(drawableToBitmap(Testid.drawable)) //.setRegion(0,drawableToBitmap(Testid.drawable).height*5/6,drawableToBitmap(Testid.drawable).width,drawableToBitmap(Testid.drawable).height)
//        val data = pallet.generate().lightVibrantSwatch
//
//        Log.e("APP", "COLOR RGB: " + data!!.rgb)




    }

    fun manipulateColor(color: Int, factor: Float): Int {
        val a = 154
        val r = Math.round(Color.red(color) * factor)
        val g = Math.round(Color.green(color) * factor)
        val b = Math.round(Color.blue(color) * factor)
        return Color.argb(a,
                Math.min(r, 255),
                Math.min(g, 255),
                Math.min(b, 255))
    }
}
