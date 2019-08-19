package com.example.googlemap.widget

import android.content.Context
import android.graphics.*
import android.graphics.Shader.TileMode
import android.util.AttributeSet
import android.view.View
import com.example.googlemap.R
import kotlin.math.min

class DonutChart(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var radius: Float = 0.toFloat()

    private var paint: Paint
    private var shadowPaint: Paint

    private var myPath: Path
    private var shadowPath: Path

    private var outterCircle: RectF
    private var innerCircle: RectF
    private var shadowRectF: RectF

    init {

        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.DonutChart, 0, 0)

        try {
            radius = a.getDimension(R.styleable.DonutChart_radius, 20.0f)
        } finally {
            a.recycle()
        }

        paint = Paint().apply {
            isDither = true
            style = Paint.Style.FILL
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            isAntiAlias = true
            strokeWidth = radius / 14.0f
        }

        shadowPaint = Paint().apply {
            color = -0x10000000
            style = Paint.Style.STROKE
            isAntiAlias = true
            strokeWidth = 6.0f
            maskFilter = BlurMaskFilter(4f, BlurMaskFilter.Blur.SOLID)
        }

        myPath = Path()
        shadowPath = Path()

        outterCircle = RectF()
        innerCircle = RectF()
        shadowRectF = RectF()

        var adjust = .019f * radius
        shadowRectF.set(adjust, adjust, radius * 2 - adjust, radius * 2 - adjust)

        adjust = .038f * radius
        outterCircle.set(adjust, adjust, radius * 2 - adjust, radius * 2 - adjust)

        adjust = .276f * radius
        innerCircle.set(adjust, adjust, radius * 2 - adjust, radius * 2 - adjust)

    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // draw shadow
        paint.shader = null
        val adjust = .0095f * radius
        paint.setShadowLayer(8F, adjust, -adjust, -0x56000000)
        drawDonut(canvas, paint, 0f, 359.9f)

        // green
        setGradient(-0x7b43c3, -0xa477d7)
        drawDonut(canvas, paint, 0f, 60f)

        //red
        setGradient(-0x1fb5d1, -0x48e9e5)
        drawDonut(canvas, paint, 60f, 60f)

        // blue
        setGradient(-0xb5493f, -0xde7d53)
        drawDonut(canvas, paint, 120f, 60f)

        // yellow
        setGradient(-0x100, -0x12cdb)
        drawDonut(canvas, paint, 180f, 180f)
    }

    private fun drawDonut(canvas: Canvas, paint: Paint, start: Float, sweep: Float) {

        myPath.reset()
        myPath.arcTo(outterCircle, start, sweep, false)
        myPath.arcTo(innerCircle, start + sweep, -sweep, false)
        myPath.close()
        canvas.drawPath(myPath, paint)
    }

    private fun setGradient(sColor: Int, eColor: Int) {
        paint.shader = RadialGradient(
            radius, radius, radius - 5,
            intArrayOf(sColor, eColor),
            floatArrayOf(.6f, .95f), TileMode.CLAMP
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val desiredWidth = radius.toInt() * 2
        val desiredHeight = radius.toInt() * 2

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width: Int
        val height: Int

        //70dp exact
        width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> //wrap content
                min(desiredWidth, widthSize)
            else -> desiredWidth
        }

        //Measure Height
        height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> min(desiredHeight, heightSize)
            else -> desiredHeight
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height)
    }
}