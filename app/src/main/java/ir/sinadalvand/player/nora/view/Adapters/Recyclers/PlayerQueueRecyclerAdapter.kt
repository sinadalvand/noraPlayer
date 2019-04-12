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
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.sinadalvand.player.nora.R
import ir.sinadalvand.player.nora.model.songProvider.Song
import ir.sinadalvand.player.nora.view.Utils.Recycler.ToucherAssist
import kotlinx.android.synthetic.main.recycler_item_playerpanel.view.*
import android.widget.ImageView
import java.util.*


class PlayerQueueRecyclerAdapter(var data: MutableList<Song>) : RecyclerView.Adapter<PlayerQueueRecyclerAdapter.PlayerItemViewHolder>(), ToucherAssist.ItemTouchHelperAdapter {

    private lateinit var helper: ItemTouchHelper

    fun setHelper(helper: ItemTouchHelper) {
        this.helper = helper
    }

    override fun onCreateViewHolder(container: ViewGroup, position: Int): PlayerItemViewHolder {
        val view: View = LayoutInflater.from(container.context).inflate(R.layout.recycler_item_playerpanel, container, false)
        return PlayerItemViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: PlayerItemViewHolder, position: Int) {
        val details = data[position]

        holder.title.text = details.title
        holder.singer.text = details.artistName


        if(details.id == 22.toLong() ){

            holder.handle.setImageResource(R.drawable.ic_small_logo_play)
            holder.handle.scaleType = ImageView.ScaleType.CENTER_INSIDE
            holder.remove.visibility = View.INVISIBLE
        }


        if(details.id != 22.toLong() )
        holder.handle.setOnClickListener {
            if (::helper.isInitialized){
                helper.startDrag(holder)
                Log.e("APP","Started : "+details.id)
            }
        }

        if(details.id != 22.toLong() )
        holder.remove.setOnClickListener {
            if (data.size - 1 >= position) {
                data.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, itemCount)
            }
        }

    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        Log.e("APP", "Move From $fromPosition to $toPosition")

        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(data, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(data, i, i - 1)

            }
        }
        notifyItemMoved(fromPosition, toPosition)


    }

    override fun onItemDismiss(position: Int) {
        if (data.size - 1 >= position) {
            notifyItemRemoved(position)
            data.removeAt(position)
        }
    }


    class PlayerItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), ToucherAssist.ItemTouchHelperViewHolder {

        val title = itemView.player_panel_recycler_title
        val singer = itemView.player_panel_recycler_singer
        val handle = itemView.player_panel_recycler_handel
        val remove = itemView.player_panel_recycler_remove


        override fun onItemSelected(context: Context) {

        }

        override fun onItemClear(context: Context) {

        }

    }


}