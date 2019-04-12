package ir.sinadalvand.permissioner

import android.content.Context
import android.os.Handler
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.view.animation.Animation
import android.view.animation.AnimationUtils


open class SuperTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatTextView(context, attrs, defStyleAttr) {


    private val fadeInAnimation: Animation = AnimationUtils.loadAnimation(getContext(), R.anim.fadein)
    private val fadeOutAnimation: Animation = AnimationUtils.loadAnimation(getContext(), R.anim.fadeout)

    private val slideup: Animation = AnimationUtils.loadAnimation(getContext(), R.anim.slideup)
    private val slidedown: Animation = AnimationUtils.loadAnimation(getContext(), R.anim.slidedown)


    private val position: Int = 0
    private val timeout = 1500
    private var showing: Boolean = false
    private var stopped: Boolean = false
    private var textHolder: String = "";
    private val handlertext: Handler = Handler()
    private var firstRun: Boolean = true
    private var animationMood : AnimationMood = AnimationMood.FADER


    fun setText(text: String, mood: AnimationMood = AnimationMood.FADER) {
        animationMood = mood
        stopAnimation()
        this.textHolder = text
        startAnimation()
    }

    /**
     * Start the animation
     */
    private fun startAnimation() {
        if (!isInEditMode && !firstRun) {

            when(animationMood){
                AnimationMood.FADER ->{
                    startAnimation(fadeOutAnimation)
                    handlertext.postDelayed({
                        text = textHolder
                        startAnimation(fadeInAnimation)
                    }, 500)
                }

                AnimationMood.SLIDER ->{
                    startAnimation(slideup)
                    handlertext.postDelayed({
                        text = textHolder
                        startAnimation(slidedown)
                    }, 500)
                }
            }


        } else if (firstRun) {
            firstRun = false
            text = textHolder
        }
    }

    /**
     * Stop the currently active animation
     */
    private fun stopAnimation() {
        handlertext.removeCallbacksAndMessages(null)
        if (animation != null) animation.cancel()
    }


    enum class AnimationMood {
        SLIDER, FADER
    }

}



