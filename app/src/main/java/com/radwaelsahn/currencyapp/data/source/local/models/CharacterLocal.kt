package com.radwaelsahn.currencyapp.data.source.local.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.radwaelsahn.currencyapp.data.models.Thumbnail

@Entity(tableName = "characters.db")
data class CharacterLocal(
//    @PrimaryKey(autoGenerate = true) val character_unique_id: Int,
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @Embedded val thumbnail: Thumbnail,
    @ColumnInfo(name = "createdOn") val createdOn: String
)
