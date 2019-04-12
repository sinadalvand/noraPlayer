package ir.sinadalvand.player.nora.view.Transition

import android.animation.Animator
import android.animation.ObjectAnimator
import android.support.transition.Transition
import android.support.transition.TransitionValues
import android.util.Property
import android.view.ViewGroup
import ir.sinadalvand.player.nora.view.customViews.TriangleImageview

class TriangleImageviewTransition : Transition() {

    private val PROPNAME_MORPH = TriangleImageviewTransition::class.java.name + ":morph"

    private val sTransitionProperties = arrayOf(PROPNAME_MORPH)

    private val MORPH_PROPERTY =
            object : Property<TriangleImageview, Float>(Float::class.java, "morph") {
                override fun set(view: TriangleImageview, morph: Float?) {
                    view.morph = morph!!
                }

                override fun get(view: TriangleImageview): Float? {
                    return view.morph
                }
            }


    override fun getTransitionProperties(): Array<String>? {
        return sTransitionProperties
    }


    override fun captureStartValues(p0: TransitionValues) {
        captureValues(p0, "Start")
    }

    override fun captureEndValues(p0: TransitionValues) {
        captureValues(p0, "End")
    }

    private fun captureValues(transitionValues: TransitionValues, value: Any) {
        if (transitionValues.view is TriangleImageview) {
            transitionValues.values[PROPNAME_MORPH] = value
        }
    }

    override fun createAnimator(sceneRoot: ViewGroup, startValues: TransitionValues?, endValues: TransitionValues?): Animator? {
        if (endValues == null || endValues.view !is TriangleImageview) {
            return null
        }
        val view = endValues.view as TriangleImageview

        val destination = if (view.mode == TriangleImageview.MODE.PLAY) 1f else 0f

        return ObjectAnimator.ofFloat(view, MORPH_PROPERTY, view.morph, destination)
    }
}