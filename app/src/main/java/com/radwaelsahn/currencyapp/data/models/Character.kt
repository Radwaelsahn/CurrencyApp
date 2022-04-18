package com.radwaelsahn.currencyapp.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Character(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail
) : Parcelable

//    @ColumnInfo(name = "comics") val comics: Comics
//    @ColumnInfo(name= resourceURI) val resourceURI: String,
//    val events: Events?,
//    val modified: String?,
//    val series: Series?,
//    val stories: Stories?,
//    val urls: List<Url>

//    description	string	A short bio or description of the character.
//    modified	Date	The date the resource was most recently modified.
//    resourceURI	string	The canonical URL identifier for this resource.
//    urls	Array[Url]	A set of public web site URLs for the resource.
//    thumbnail	Image	The representative image for this character.
//    comics	ResourceList	A resource list containing comics which feature this character.
//    stories	ResourceList	A resource list of stories in which this character appears.
//    events	ResourceList	A resource list of events in which this character appears.
//    series
