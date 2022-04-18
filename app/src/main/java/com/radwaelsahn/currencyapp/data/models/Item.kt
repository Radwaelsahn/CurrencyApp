package com.radwaelsahn.currencyapp.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(
    val name: String,
    val resourceURI: String,
    val role: String?
):Parcelable