/*
 *  Copyright (c)  2018 by Sina Dalvand
 *  All Rights Reserved.
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  All Classes & Files Created for CoolPlayer project
 *  This file created by LOGAN Username
 */

package ir.sinadalvand.player.nora.view.Activities

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.Menu
import android.view.View
import ir.sinadalvand.player.nora.R
import ir.sinadalvand.player.nora.utils.AppCompatActivityInjector
import ir.sinadalvand.player.nora.view.Adapters.Recyclers.PlayerQueueRecyclerAdapter
import ir.sinadalvand.player.nora.view.Utils.FakeDataGenerator
import ir.sinadalvand.player.nora.view.Utils.ImageUtils
import ir.sinadalvand.player.nora.view.Utils.Recycler.ToucherAssist
import ir.sinadalvand.player.nora.view.Utils.RecyclerUtils
import ir.sinadalvand.player.nora.view.customViews.SlidingUpPanelLayout
import ir.sinadalvand.player.nora.viewmodel.PlayerViewModel
import jp.wasabeef.blurry.Blurry
import kotlinx.android.synthetic.main.activity_player.*
import javax.inject.Inject


class PlayerActivity : AppCompatActivityInjector() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        val userModel: PlayerViewModel = ViewModelProviders.of(this).get(PlayerViewModel::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = Color.parseColor("#FF294F")
        }


        floatingActionButton.setOnClickListener {

        }

        setSupportActionBar(player_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        player_seekbar_sound.setMorph(1f)
        player_seekbar_sound.setProgress(78)



        player_slide_layout.setPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View?, slideOffset: Float) {
                Log.e("APP", "value: $slideOffset")
                val diffmorph = 1 - slideOffset;
                player_bottomshit.setMorph(slideOffset)
                player_seekbar.setMorph(slideOffset)
                player_sound_layout.alpha = 1f * diffmorph
                player_panel_title.alpha = slideOffset
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) play_appbar.elevation = slideOffset * 15
                play_appbar.setBackgroundColor(adjustAlpha(resources.getColor(R.color.colorbackground), slideOffset))
                player_toobar_summery.alpha = slideOffset
                guideline8.setGuidelinePercent(0.12f + (diffmorph * 0.08).toFloat())
                guideline11.setGuidelinePercent(0.12f + (diffmorph * 0.13).toFloat())
            }

            override fun onPanelCollapsed(panel: View?) {

            }

            override fun onPanelExpanded(panel: View?) {

            }

            override fun onPanelAnchored(panel: View?) {

            }

            override fun onPanelHidden(panel: View?) {

            }

        })


        val adapter = PlayerQueueRecyclerAdapter(FakeDataGenerator.getFakeSongs())
        player_pannel_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        player_pannel_recycler.adapter = adapter
        player_pannel_recycler.addItemDecoration(RecyclerUtils.getDivider(this))
        val helper = ItemTouchHelper(ToucherAssist(adapter, this))
        adapter.setHelper(helper)
        helper.attachToRecyclerView(player_pannel_recycler)
        Blurry.with(this).radius(25).sampling(4).from(ImageUtils.drawableToBitmap(player_backimage.drawable)).into(player_backimage);
    }



    @ColorInt
    fun adjustAlpha(@ColorInt color: Int, factor: Float): Int {
        val alpha = Math.round(Color.alpha(color) * factor)
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        return Color.argb(alpha, red, green, blue)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_player_activity, menu)
        return true
    }
}
