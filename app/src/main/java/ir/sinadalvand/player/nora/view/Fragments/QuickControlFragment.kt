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
import android.support.constraint.ConstraintSet
import android.support.transition.ChangeBounds
import android.support.transition.TransitionManager
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import dagger.android.support.AndroidSupportInjection
import ir.sinadalvand.player.nora.R

class QuickControlFragment : Fragment() {

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    var switch = true;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_quickcontrol, container, false)

        view.setOnClickListener {

            if(switch){
                val constraintSet = ConstraintSet()
                constraintSet.clone(context, R.layout.fragment_quickcontrol_alt)

                val transition = ChangeBounds()
                transition.interpolator = AnticipateOvershootInterpolator(1.0f)
                transition.duration = 1200



                TransitionManager.beginDelayedTransition(view.findViewById(R.id.landing_fragment_controller_main), transition)
                constraintSet.applyTo(view.findViewById(R.id.landing_fragment_controller_main))

                switch = false
            }else{

                val constraintSet = ConstraintSet()
                constraintSet.clone(context, R.layout.fragment_quickcontrol)

                val transition = ChangeBounds()
                transition.interpolator = AnticipateOvershootInterpolator(1.0f)
                transition.duration = 1200

                TransitionManager.beginDelayedTransition(view.findViewById(R.id.landing_fragment_controller_main), transition)
                constraintSet.applyTo(view.findViewById(R.id.landing_fragment_controller_main))

                switch = true
            }

        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
