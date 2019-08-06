package com.example.googlemap.widget

import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Drawable
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.math.sin

class DonutChart(values: FloatArray, colors: IntArray) : Drawable() {

    private val radius = 120f
    private var paint: Paint
    private val myPath: Path
    private val outterCircle: RectF
    private val innerCircle: RectF
    private val COLORS: IntArray = colors.copyOf(colors.size)
    private val valueDegree: FloatArray
    private val valueReal: FloatArray = values.copyOf(values.size)
    private var start = -1
    private val paintText = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        valueDegree = calculateData(values)

        paint = Paint()
        paint.isDither = true
        paint.style = Paint.Style.FILL
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        paint.isAntiAlias = true
        paint.strokeWidth = radius / 14.0f

        val shadowPaint = Paint()
        shadowPaint.color = -0x10000000
        shadowPaint.style = Paint.Style.STROKE
        shadowPaint.isAntiAlias = true
        shadowPaint.strokeWidth = 6.0f
        shadowPaint.maskFilter = BlurMaskFilter(4f, BlurMaskFilter.Blur.SOLID)

        myPath = Path()

        outterCircle = RectF()
        innerCircle = RectF()
        val shadowRectF = RectF()

        var adjust = .019f * radius
        shadowRectF.set(adjust, adjust, radius * 2 - adjust, radius * 2 - adjust)

        adjust = .038f * radius
        outterCircle.set(adjust, adjust, radius * 2 - adjust, radius * 2 - adjust)

        adjust = .276f * radius
        innerCircle.set(adjust, adjust, radius * 2 - adjust, radius * 2 - adjust)
    }

    private fun calculateData(data: FloatArray): FloatArray {
        var total = 0f
        for (aData in data) {
            total += aData
        }
        for (i in data.indices) {
            data[i] = 360 * (data[i] / total)
        }
        return data
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
            floatArrayOf(.6f, .95f), Shader.TileMode.CLAMP
        )
    }

    private fun darkenColor(color: Int): Int {
        val a = Color.alpha(color)
        val r = (Color.red(color) * 0.8f).roundToInt()
        val g = (Color.green(color) * 0.8f).roundToInt()
        val b = (Color.blue(color) * 0.8f).roundToInt()
        return Color.argb(
            a,
            min(r, 255),
            min(g, 255),
            min(b, 255)
        )
    }

    private fun convertDpToPixel(dp: Float): Float {
        val metrics = Resources.getSystem().displayMetrics
        val px = dp * (metrics.densityDpi / 160f)
        return px.roundToInt().toFloat()
    }

    override fun draw(canvas: Canvas) {
        // draw shadow
        paint.shader = null
        val adjust = .0095f * radius
        paint.setShadowLayer(8f, adjust, -adjust, -0x56000000)
        drawDonut(canvas, paint, 0f, 359.9f)
        var sweep: Float

        for (i in 0 until valueDegree.size) {
            sweep = valueDegree[i]

            if (i != 0) {
                start += valueDegree[i - 1].toInt()
            }

            if (start == -1 && sweep == 0f) {
                sweep = 359f
            }

            setGradient(COLORS[i], darkenColor(COLORS[i]))
            drawDonut(canvas, paint, start.toFloat(), sweep)

            if (valueReal[i] != 0f) {
                paintText.color = Color.BLACK
                paintText.textSize = convertDpToPixel(13f)
                val x = 35 * cos(Math.toRadians((start + sweep / 2).toDouble())) + 35
                val y = 35 * sin(Math.toRadians((start + sweep / 2).toDouble())) + 45

                canvas.drawText(
                    valueReal[i].toInt().toString() + "",
                    convertDpToPixel(x.toFloat()),
                    convertDpToPixel(y.toFloat()),
                    paintText
                )
            }
        }
    }

    override fun setAlpha(p0: Int) {

    }

    override fun getOpacity(): Int {
        return PixelFormat.UNKNOWN
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {

    }

}