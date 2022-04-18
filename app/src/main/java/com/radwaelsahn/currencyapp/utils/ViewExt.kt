package com.radwaelsahn.currencyapp.utils

import android.animation.ObjectAnimator
import android.app.Activity
import android.app.Service
import android.content.Context
import android.graphics.Paint
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.radwaelsahn.currencyapp.R


fun View.showKeyboard() {
    (context?.getSystemService(Service.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.showSoftInput(this, 0)
}

fun View.hideKeyboard() {
    (context?.getSystemService(Service.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.toGone() {
    this.visibility = View.GONE
}

fun View.toInvisible() {
    this.visibility = View.GONE
}


/**
 * Transforms static java function Snackbar.make() to an extension function on View.
 */
fun View.showSnackbar(snackbarText: String, timeLength: Int) {
    Snackbar.make(this, snackbarText, timeLength).run {
        addCallback(object : Snackbar.Callback() {
            override fun onShown(sb: Snackbar?) {
//                EspressoIdlingResource.increment()
            }

            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
//                EspressoIdlingResource.decrement()
            }
        })
        show()
    }
}

/**
 * Triggers a snackbar message when the value contained by snackbarTaskMessageLiveEvent is modified.
 */
fun View.setupSnackbar(
    lifecycleOwner: LifecycleOwner,
    snackbarEvent: LiveData<Event<Int>>,
    timeLength: Int
) {

    snackbarEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let {
            showSnackbar(context.getString(it), timeLength)
        }
    })
}

fun View.showToast(
    lifecycleOwner: LifecycleOwner,
    snackbarEvent: LiveData<Event<Any>>,
    timeLength: Int
) {

    snackbarEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let {
            when (it) {
                is String -> Toast.makeText(context, it, timeLength).show()
                is Int -> Toast.makeText(context, context.getString(it), timeLength).show()
                else -> {
                }
            }
        }
    })
}

fun ImageView.loadImage(@DrawableRes resId: Int) = Glide.with(this)
    .load(resId)
    .into(this)

fun ImageView.loadImage(imageUrl: String?) {
    if (imageUrl != null && !imageUrl.isNullOrEmpty()) {
        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.color.black)
            .into(this)
    } else
        loadImage(R.color.black)
}

fun ImageView.loadRoundedImage(imageUrl: String?, corner: Int) {
    if (imageUrl != null && !imageUrl.isNullOrEmpty()) {
        val url: String? = imageUrl
        Glide.with(this)
            .load(url)
            .apply(
                RequestOptions().transforms(
                    CenterCrop(),
                    RoundedCorners(corner)
                )
            )
            .placeholder(R.color.white)
            .into(this)


    }
}

fun ImageView.loadRoundedImage(imageUrl: String?) {
    if (imageUrl != null && !imageUrl.isNullOrEmpty()) {
        val url: String? = imageUrl
        Log.e("imageUrl", url!!)
        Glide.with(this)
            .load(url)
            .apply(
                RequestOptions().transforms(
                    CenterCrop(),
                    RoundedCorners(Constants.IMAGE_CORNER)
                )
            )
            .placeholder(R.color.white)
            .into(this)
    }
}

private val CONTENT_TYPE = "Content-Type"
private val Authorization = "Authorization"
private val Language = "Accept-Language"
private val CONTENT_TYPE_VALUE = "application/json"
private val APP_VERSION = "AppVersion"
fun ImageView.loadImageWithHeader(url: String) {

    Log.e("url", url)

    val glideUrl = GlideUrl(
        url,
        LazyHeaders.Builder()
            .addHeader(Authorization, Constants.marvel_public_key)
            .build()
    )

    Glide.with(this.context)
        .load(glideUrl)
//        .diskCacheStrategy(DiskCacheStrategy.NONE)
//        .skipMemoryCache(true)
        .into(this)
}


fun ImageView.loadCircleImage(imageUrl: String?, placeHolder: Int) {
    try {
        if (imageUrl != null && imageUrl!!.isNotEmpty() && !imageUrl.isNullOrEmpty()) {
            val url: String = imageUrl
            Glide.with(this)
                .load(url)
//                .dontAnimate()
//                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//                .apply(RequestOptions.bitmapTransform(RoundedCorners(Constants.IMAGE_CORNER_CIRCLE))) // round
                .circleCrop()
                .placeholder(placeHolder)
                .error(placeHolder)
                .into(this)
        }
    } catch (e: Exception) {
        Glide.with(this)
            .load(placeHolder)
            .circleCrop()
            .placeholder(placeHolder)
            .error(placeHolder)
            .into(this)
    }

}

fun TextView.makeUnderlined() {
    this.paintFlags = this.paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

fun TextView.makeUnderlined(text: String) {
//
//    var content = SpannableString(text)
//    content.setSpan(UnderlineSpan(), 0, content.length, 0)
//    this.text = content

//    this.text = Html.fromHtml(String.format(this.context.getString(R.string.underlined_text), text))

    //this.setPaintFlags(this.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
    this.paintFlags = this.paintFlags or Paint.UNDERLINE_TEXT_FLAG

    this.text = text
}

fun TextView.clearUnderlined(text: String) {
//    if (this.paintFlags == Paint.UNDERLINE_TEXT_FLAG)
    this.paintFlags = this.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()

    this.text = text
}

fun AppCompatTextView.setTextFutureExt(text: String) =
    setTextFuture(
        PrecomputedTextCompat.getTextFuture(
            text,
            TextViewCompat.getTextMetricsParams(this),
            null
        )
    )

fun AppCompatEditText.setTextFutureExt(text: String) =
    setText(
        PrecomputedTextCompat.create(text, TextViewCompat.getTextMetricsParams(this))
    )


fun rotateAnimation(view: View) {
    val rotate =
        RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
    rotate.duration = 5000
    rotate.interpolator = LinearInterpolator()
    rotate.repeatCount = ObjectAnimator.INFINITE

    view.startAnimation(rotate)

}

fun View.changeSizeAspectRatio(widthDp: Int) {

    val params = this!!.layoutParams as ViewGroup.LayoutParams

    val display = context.resources.displayMetrics
//        params.width = display.widthPixels
//        params.height = display.widthPixels * 9 / 16

    var width = convertDpToPixel(widthDp, this.context)
    if (width != 0)
        params.width = width
    else params.width = display.widthPixels

    params.height = params.width * 9 / 16

    Log.i("width", params.width.toString() + "")
    Log.i("height", params.height.toString() + "")

    this!!.layoutParams = params

}

fun convertDpToPixel(dp: Int, context: Context): Int {
    val resources = context.resources
    val metrics = resources.displayMetrics
    val px = dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    return px.toInt()
}

fun changeTextColor(textView: TextView, colorId: Int) {
    textView.setTextColor(
        ContextCompat.getColor(
            textView.context,
            colorId
        )
    )
}


fun hideKeyboard(activity: Activity) {
    val view = activity.findViewById<View>(android.R.id.content)
    if (view != null) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

