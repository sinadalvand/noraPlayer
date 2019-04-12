package ir.sinadalvand.permissioner

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator

class SubmitButton : View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        val typedArray = context!!.obtainStyledAttributes(attrs, R.styleable.SubmitButton, 0, 0)
        if (typedArray.getString(R.styleable.SubmitButton_buttonText) != null) {
            buttonText = typedArray.getString(R.styleable.SubmitButton_buttonText)!!
        }
        buttonColor = typedArray.getColor(R.styleable.SubmitButton_buttonColor, Color.parseColor("#19CC95"))
        succeedColor = typedArray.getColor(R.styleable.SubmitButton_succeedColor, Color.parseColor("#19CC95"))
        failedColor = typedArray.getColor(R.styleable.SubmitButton_failedColor, Color.parseColor("#FC8E34"))
        textSize = typedArray.getDimension(R.styleable.SubmitButton_buttonTextSize, sp2px(15f).toFloat()).toInt()
        progressStyle = typedArray.getInt(R.styleable.SubmitButton_progressStyle, STYLE_LOADING)
        typedArray.recycle()
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    companion object {
        val STATE_NONE = 0
        val STATE_SUBMIT = 1
        val STATE_LOADING = 2
        val STATE_RESULT = 3
    }


    var viewState = STATE_NONE


    private var mWidth: Int = 0
    private var mHeight: Int = 0

    private var MAX_WIDTH: Int = 0
    private var MAX_HEIGHT: Int = 0


    private var x: Int = 0
    private var y: Int = 0

    private var buttonText: String = ""
    private var buttonColor: Int = 0
    private var succeedColor: Int = 0
    private var failedColor: Int = 0
    private var textSize: Int = 0


    private var textWidth: Int = 0
    private var textHeight: Int = 0

    private var bgPaint: Paint = Paint()
    private var loadingPaint: Paint = Paint()
    private var resultPaint: Paint = Paint()
    private var textPaint: Paint = Paint()

    private var buttonPath: Path = Path()
    private var loadPath: Path = Path()
    private var dst: Path = Path()
    private var pathMeasure: PathMeasure = PathMeasure()
    private var resultPath: Path = Path()

    private var circleLeft: RectF = RectF()
    private var circleMid: RectF = RectF()
    private var circleRight: RectF = RectF()

    private var loadValue: Float = 0.toFloat()

    private var submitAnim: ValueAnimator? = null
    private var loadingAnim: ValueAnimator? = null
    private var resultAnim: ValueAnimator? = null

    private var isDoResult: Boolean = false
    private var isSucceed: Boolean = false
    private var nextExistance = false

    private val STYLE_LOADING = 0
    private val STYLE_PROGRESS = 1


    private var progressStyle = STYLE_LOADING
    private var currentProgress: Float = (50 * 0.01).toFloat()

    private var listener: OnRefreshButton? = null


    var nextListener: OnNextListener? = null


    private fun init() {

        bgPaint.color = buttonColor
        bgPaint.style = Paint.Style.STROKE
        bgPaint.strokeWidth = 5f
        bgPaint.isAntiAlias = true


        loadingPaint.color = buttonColor
        loadingPaint.style = Paint.Style.STROKE
        loadingPaint.strokeWidth = 9f
        loadingPaint.isAntiAlias = true


        resultPaint.color = Color.WHITE
        resultPaint.style = Paint.Style.STROKE
        resultPaint.strokeWidth = 9f
        resultPaint.strokeCap = Paint.Cap.ROUND
        resultPaint.isAntiAlias = true


        textPaint.color = buttonColor
        textPaint.strokeWidth = (textSize / 6).toFloat()
        textPaint.textSize = textSize.toFloat()
        textPaint.isAntiAlias = true

        textWidth = getTextWidth(textPaint, buttonText)
        textHeight = getTextHeight(textPaint, buttonText)


    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        var widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        var heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

        if (widthMode == View.MeasureSpec.AT_MOST) {
            widthSize = textWidth + 100
        }

        if (heightMode == View.MeasureSpec.AT_MOST) {
            heightSize = (textHeight * 2.5).toInt()
        }

        if (heightSize > widthSize) {
            heightSize = (widthSize * 0.25).toInt()
        }

        mWidth = widthSize - 10
        mHeight = heightSize - 10
        x = (widthSize * 0.5).toInt()
        y = (heightSize * 0.5).toInt()
        MAX_WIDTH = mWidth
        MAX_HEIGHT = mHeight

        setMeasuredDimension(widthSize, heightSize)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.translate(x.toFloat(), y.toFloat())
        drawButton(canvas)
        if (viewState == STATE_NONE || viewState == STATE_SUBMIT && mWidth > textWidth) {
            drawButtonText(canvas)
        }
        if (viewState == STATE_LOADING) {
            drawLoading(canvas)
        }
        if (viewState == STATE_RESULT) {
            drawResult(canvas, isSucceed)
        }
    }


    private fun drawButton(canvas: Canvas) {
        buttonPath.reset()
        circleLeft.set((-mWidth / 2).toFloat(), (-mHeight / 2).toFloat(), (-mWidth / 2 + mHeight).toFloat(), (mHeight / 2).toFloat())
        buttonPath.arcTo(circleLeft, 90f, 180f)
        buttonPath.lineTo((mWidth / 2 - mHeight / 2).toFloat(), (-mHeight / 2).toFloat())
        circleRight.set((mWidth / 2 - mHeight).toFloat(), (-mHeight / 2).toFloat(), (mWidth / 2).toFloat(), (mHeight / 2).toFloat())
        buttonPath.arcTo(circleRight, 270f, 180f)
        buttonPath.lineTo((-mWidth / 2 + mHeight / 2).toFloat(), (mHeight / 2).toFloat())
        canvas.drawPath(buttonPath, bgPaint)
    }


    private fun drawLoading(canvas: Canvas) {
        dst.reset()
        circleMid.set((-MAX_HEIGHT / 2).toFloat(), (-MAX_HEIGHT / 2).toFloat(), (MAX_HEIGHT / 2).toFloat(), (MAX_HEIGHT / 2).toFloat())
        loadPath.addArc(circleMid, 270f, 359.999f)
        pathMeasure.setPath(loadPath, true)
        var startD = 0f
        val stopD: Float
        if (progressStyle == STYLE_LOADING) {
            startD = pathMeasure.length * loadValue
            stopD = startD + pathMeasure.length / 2 * loadValue
        } else {
            stopD = pathMeasure.length * currentProgress
        }
        pathMeasure.getSegment(startD, stopD, dst, true)
        canvas.drawPath(dst, loadingPaint)
    }


    private fun drawResult(canvas: Canvas, isSucceed: Boolean) {
        resultPath.reset()
        if (isSucceed) {

            if (nextExistance) {
                resultPath.moveTo((-mHeight / 6).toFloat(), 0f)
                resultPath.lineTo((mHeight / 3).toFloat(), 0f)

                resultPath.moveTo((mHeight / 6).toFloat(), (-mHeight / 7).toFloat())
                resultPath.lineTo((mHeight / 3).toFloat(), 0f)

                resultPath.moveTo((mHeight / 6).toFloat(), (mHeight / 7).toFloat())
                resultPath.lineTo((mHeight / 3).toFloat(), 0f)
            } else {
                resultPath.moveTo((-mHeight / 6).toFloat(), 0f)
                resultPath.lineTo(0f, (-mHeight / 6 + (1 + Math.sqrt(5.0)) * mHeight / 12).toFloat())
                resultPath.lineTo((mHeight / 3).toFloat(), (-mHeight / 7).toFloat())
            }


        } else {

            if (nextExistance) {
                resultPath.moveTo((-mHeight / 6).toFloat(), 0f)
                resultPath.lineTo((mHeight / 3).toFloat(), 0f)

                resultPath.moveTo((mHeight / 6).toFloat(), (-mHeight / 7).toFloat())
                resultPath.lineTo((mHeight / 3).toFloat(), 0f)

                resultPath.moveTo((mHeight / 6).toFloat(), (mHeight / 7).toFloat())
                resultPath.lineTo((mHeight / 3).toFloat(), 0f)
            } else {
                resultPath.moveTo((-mHeight / 6).toFloat(), (mHeight / 6).toFloat())
                resultPath.lineTo((mHeight / 6).toFloat(), (-mHeight / 6).toFloat())
                resultPath.moveTo((-mHeight / 6).toFloat(), (-mHeight / 6).toFloat())
                resultPath.lineTo((mHeight / 6).toFloat(), (mHeight / 6).toFloat())
            }


        }


        canvas.drawPath(resultPath, resultPaint)
    }

    private fun drawButtonText(canvas: Canvas) {
        textPaint.alpha = (mWidth - textWidth) * 255 / (MAX_WIDTH - textWidth)
        canvas.drawText(buttonText, (-textWidth / 2).toFloat(), getTextBaseLineOffset(), textPaint)
    }


    private fun startSubmitAnim() {
        viewState = STATE_SUBMIT
        submitAnim = ValueAnimator()
        submitAnim!!.setIntValues(MAX_WIDTH, MAX_HEIGHT)
        submitAnim!!.addUpdateListener { animation ->
            mWidth = animation.animatedValue as Int
            if (mWidth == mHeight) {
                bgPaint.color = Color.parseColor("#DDDDDD")
            }
            invalidate()
        }
        submitAnim!!.duration = 300
        submitAnim!!.interpolator = AccelerateInterpolator()
        submitAnim!!.start()
        submitAnim!!.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                if (isDoResult) {
                    startResultAnim()
                } else {
                    startLoadingAnim()
                }
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })
    }


    private fun startLoadingAnim() {
        viewState = STATE_LOADING
        if (progressStyle == STYLE_PROGRESS) {
            return
        }
        loadingAnim = ValueAnimator()
        loadingAnim!!.setFloatValues(0.0f, 1.0f)
        loadingAnim!!.addUpdateListener { animation ->
            loadValue = animation.animatedValue as Float
            invalidate()
        }
        loadingAnim!!.duration = 2000
        loadingAnim!!.repeatCount = ValueAnimator.INFINITE
        loadingAnim!!.start()
    }


    private fun startResultAnim() {
        viewState = STATE_RESULT
        if (loadingAnim != null) {
            loadingAnim!!.cancel()
        }
        resultAnim = ValueAnimator()
        resultAnim!!.setIntValues(MAX_HEIGHT, MAX_WIDTH)
        resultAnim!!.addUpdateListener { animation ->
            mWidth = animation.animatedValue as Int
            resultPaint.alpha = (mWidth - mHeight) * 255 / (MAX_WIDTH - MAX_HEIGHT)
            if (mWidth == mHeight) {
                if (isSucceed) {
                    bgPaint.color = succeedColor
                } else {
                    bgPaint.color = failedColor
                }
                bgPaint.style = Paint.Style.FILL_AND_STROKE
            }
            invalidate()
        }
        resultAnim!!.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                if (listener == null) {
                    return
                }
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })
        resultAnim!!.duration = 300
        resultAnim!!.interpolator = AccelerateInterpolator()
        resultAnim!!.start()
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_UP -> {

                if (viewState == STATE_RESULT && listener != null) {
                    reset()
                    listener!!.refreshed()
                    return false
                } else if (viewState == STATE_NONE) {
                    startSubmitAnim()
                }
            }
        }
        return super.onTouchEvent(event)
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (submitAnim != null) {
            submitAnim!!.cancel()
        }
        if (loadingAnim != null) {
            loadingAnim!!.cancel()
        }
        if (resultAnim != null) {
            resultAnim!!.cancel()
        }
    }


    fun doResult(isSucceed: Boolean) {
        if (viewState == STATE_NONE || viewState == STATE_RESULT || isDoResult) {
            return
        }
        isDoResult = true
        this.isSucceed = isSucceed
        if (viewState == STATE_LOADING) {
            startResultAnim()
        }
    }


    fun reset() {
        if (submitAnim != null) {
            submitAnim!!.cancel()
        }
        if (loadingAnim != null) {
            loadingAnim!!.cancel()
        }
        if (resultAnim != null) {
            resultAnim!!.cancel()
        }
        viewState = STATE_NONE
        mWidth = MAX_WIDTH
        mHeight = MAX_HEIGHT
        isSucceed = false
        isDoResult = false
        currentProgress = 0f
        init()
        invalidate()
    }


    fun setProgress(progress: Int) {
        if (progress < 0 || progress > 100) {
            return
        }
        currentProgress = (progress * 0.01).toFloat()
        if (progressStyle == STYLE_PROGRESS && viewState == STATE_LOADING) {
            invalidate()
        }
    }


    fun onRefreshButton(listener: OnRefreshButton) {
        this.listener = listener
    }

    fun setNextExistance(nextExistance: Boolean) {
        this.nextExistance = nextExistance
    }


    interface OnRefreshButton {
        fun refreshed()
    }

    interface OnNextListener {
        fun onNextListener()
    }


    private fun sp2px(sp: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (sp * fontScale + 0.5f).toInt()
    }

    private fun getTextBaseLineOffset(): Float {
        val fm = textPaint.fontMetrics
        return -(fm.bottom + fm.top) / 2
    }

    private fun getTextHeight(paint: Paint, str: String): Int {
        val rect = Rect()
        paint.getTextBounds(str, 0, str.length, rect)
        return rect.height()
    }

    private fun getTextWidth(paint: Paint, str: String?): Int {
        var mRet = 0
        if (str != null && str.length > 0) {
            val len = str.length
            val widths = FloatArray(len)
            paint.getTextWidths(str, widths)
            for (j in 0 until len) {
                mRet += Math.ceil(widths[j].toDouble()).toInt()
            }
        }
        return mRet
    }

}