/*
 *  Copyright (c)  2018 by Sina Dalvand
 *  All Rights Reserved.
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  All Classes & Files Created for CoolPlayer project
 *  This file created by LOGAN Username
 */

package ir.sinadalvand.player.nora.view.Fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.AndroidSupportInjection
import ir.sinadalvand.player.nora.R
import ir.sinadalvand.player.nora.model.songProvider.Song
import ir.sinadalvand.player.nora.view.Adapters.Recyclers.ArtistRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_landing.*

class AlbumsFragment : Fragment() {


    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)

        // View Model Must Init Here
        // viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_landing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        landing_fragment_recycler.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        landing_fragment_recycler.adapter = ArtistRecyclerAdapter(arrayListOf(Song(), Song(), Song(), Song(), Song(), Song(), Song(), Song(), Song(), Song(), Song(), Song())
                , context!!, SIna)


    }


}