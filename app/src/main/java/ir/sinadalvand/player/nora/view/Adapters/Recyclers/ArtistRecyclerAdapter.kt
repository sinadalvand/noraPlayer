/*
 *  Copyright (c)  2018 by Sina Dalvand
 *  All Rights Reserved.
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  All Classes & Files Created for CoolPlayer project
 *  This file created by LOGAN Username
 */

package ir.sinadalvand.player.nora.view.Adapters.Recyclers

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ir.sinadalvand.player.nora.R
import ir.sinadalvand.player.nora.model.songProvider.Song
import kotlinx.android.synthetic.main.recycler_item_album.view.*


class ArtistRecyclerAdapter(val datalist: MutableList<Song>,val context:Context , val viewgroup:ViewGroup) : RecyclerView.Adapter<ArtistRecyclerAdapter.Holder>() {


    override fun onCreateViewHolder(container: ViewGroup, position: Int): Holder {
        return Holder(LayoutInflater.from(container.context).inflate(R.layout.recycler_item_album, container, false))
    }

    override fun getItemCount(): Int = datalist.size


    override fun onBindViewHolder(holder: Holder, position: Int) {
        Glide.with(holder.itemView).load(R.drawable.logo).apply(RequestOptions().skipMemoryCache(true)).into(holder.image)


    }


    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val layout = itemView.landing_atrist_item_layout!!
        val image = itemView.landing_atrist_item_image!!

    }
}