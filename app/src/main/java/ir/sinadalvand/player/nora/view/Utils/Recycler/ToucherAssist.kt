/*
 *  Copyright (c)  2018 by Sina Dalvand
 *  All Rights Reserved.
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  All Classes & Files Created for CoolPlayer project
 *  This file created by LOGAN Username
 */

package ir.sinadalvand.player.nora.view.Utils.Recycler

import android.content.Context
import android.graphics.*
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper


class ToucherAssist(val mAdapter: ItemTouchHelperAdapter, val context: Context) : ItemTouchHelper.Callback() {


    var p = Paint()


    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return false
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        // Enable drag up and down and right swipe in right direction
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.END or ItemTouchHelper.START
        // final int swipeFlags =  ItemTouchHelper.END | ItemTouchHelper.START; Enable swipe in both direction
        return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)

    }

    override fun getAnimationDuration(recyclerView: RecyclerView, animationType: Int, animateDx: Float, animateDy: Float): Long {
        // return animationType == ItemTouchHelper.ANIMATION_TYPE_DRAG ? DEFAULT_DRAG_ANIMATION_DURATION : DEFAULT_SWIPE_ANIMATION_DURATION;
        return (if (animationType == ItemTouchHelper.ANIMATION_TYPE_DRAG) ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION else 350).toLong()
    }

    override fun onMove(recyclerView: RecyclerView, source: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        if (source.itemViewType != target.itemViewType) {
            return false
        }

        // Notify the adapter of the move
        mAdapter.onItemMove(source.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onChildDrawOver(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
        // Notify the adapter of the dismissal
        mAdapter.onItemDismiss(viewHolder.adapterPosition)
    }



    override fun getBoundingBoxMargin(): Int {
        return super.getBoundingBoxMargin()
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        // Fade out the view as it is swiped out of the parent's bounds
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
//            val itemView = viewHolder.itemView
//
//            val icon: Bitmap
//
//            //            if (dX > 0) {
//            this.c = c
//            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_delete_white_24dp)
//            // Set color for right swipe
//
//            p.setColor(Color.parseColor("#FF3D00"))
//            // Draw Rect with varying right side, equal to displacement dX
//            //            c.drawRect((float) itemView.getLeft() + Utils.dpToPx(0), (float) itemView.getTop(), itemView.getRight() + Utils.dpToPx(0),
//            //                    (float) itemView.getBottom(), p);
//
//            // Set the image icon for right swipe
//            c.drawBitmap(icon, itemView.left as Float + 20 * icon.getWidth() / 19, itemView.top as Float + (itemView.bottom as Float - itemView.top as Float - icon.getHeight()) / 2, p)
//
//
//            c.drawBitmap(icon, itemView.right as Float - 25 * icon.getWidth() / 10, itemView.top as Float + (itemView.bottom as Float - itemView.top as Float - icon.getHeight()) / 2, p)
//
//
//            icon.recycle()
//
//            //            }
        }
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            // Let the view holder know that this item is being moved or dragged
            val itemViewHolder = viewHolder as ItemTouchHelperViewHolder?
            itemViewHolder!!.onItemSelected(context)
        }

        super.onSelectedChanged(viewHolder, actionState)

        /* final boolean swiping = actionState == ItemTouchHelper.ACTION_STATE_SWIPE;
        swipeRefreshLayout.setEnabled(!swiping);*/
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)

        // Tell the view holder it's time to restore the idle state
        val itemViewHolder = viewHolder as ItemTouchHelperViewHolder
        itemViewHolder.onItemClear(context)

    }

    override fun getMoveThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        //  return super.getMoveThreshold(viewHolder);
        return 0.1f
        //  return super.getMoveThreshold(0.5f);
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        //if (viewHolder instanceof RecyclerView.ViewHolder) return 1f;
        //return super.getSwipeThreshold(viewHolder);
        return 0.9f
    }

    interface ItemTouchHelperViewHolder {

        fun onItemSelected(context: Context)
        fun onItemClear(context: Context)
    }


    interface ItemTouchHelperAdapter {

        fun onItemMove(fromPosition: Int, toPosition: Int)
        fun onItemDismiss(position: Int)
    }

}