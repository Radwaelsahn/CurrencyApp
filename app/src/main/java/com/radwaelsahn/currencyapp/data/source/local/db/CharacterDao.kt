package com.radwaelsahn.currencyapp.data.source.local.db

import androidx.room.*
import com.radwaelsahn.currencyapp.data.source.local.models.CharacterLocal

@Dao
interface CharactersDao {
    @Query("SELECT * FROM `characters.db` ORDER BY createdOn ASC")
    suspend fun getAll(): List<CharacterLocal>

//    @Query("SELECT * FROM `characters.db` WHERE id IN (:ids)")
//    fun loadAllByIds(ids: IntArray): List<CharacterLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReplace(entity: CharacterLocal): Long

//    @Query("SELECT * FROM `characters.db` WHERE id= :id")
//    fun findById(id: Int): CharacterLocal

    @Insert
    suspend fun insert(vararg character: CharacterLocal)

//    @Delete
//    fun delete(character: CharacterLocal)
//
//    @Query("DELETE FROM `characters.db`")
//    fun deleteAll()


}