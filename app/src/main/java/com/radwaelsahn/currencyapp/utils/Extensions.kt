package com.radwaelsahn.currencyapp.utils

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.radwaelsahn.currencyapp.R
import java.math.BigInteger
import java.security.MessageDigest

fun convertToMd5(input: String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}

fun RecyclerView.addVerticalItemDecoration() {
    val linearLayoutManager = LinearLayoutManager(
        this.context,
        LinearLayoutManager.HORIZONTAL, false
    )
    this.layoutManager = linearLayoutManager
//    linearLayoutManager.scrollToPosition(5)

    val dividerItemDecoration = DividerItemDecoration(
        this.context,
        linearLayoutManager.orientation
    )
    ContextCompat.getDrawable(
        this.context,
        R.drawable.vertical_divider
    )?.let {
        dividerItemDecoration.setDrawable(
            it
        )
    }
    this.addItemDecoration(dividerItemDecoration)

}


fun addLinearHorizontalItemDecoration(recyclerView: RecyclerView) {
    val linearLayoutManager = LinearLayoutManager(recyclerView.context)
    recyclerView.layoutManager = linearLayoutManager
    val dividerItemDecoration =
        DividerItemDecoration(recyclerView.context, linearLayoutManager.orientation)
    recyclerView.addItemDecoration(dividerItemDecoration)
}

fun changeSizeAspectRatio(view: View, context: Context, widthDp: Int) {

    val params = view!!.layoutParams as ViewGroup.LayoutParams

    val display = context.resources.displayMetrics

    var width = convertDpToPixel(widthDp, context)
    if (width != 0) params.width = width
    else params.width = display.widthPixels

    params.height = params.width * 3 / 4

    view!!.layoutParams = params

}

