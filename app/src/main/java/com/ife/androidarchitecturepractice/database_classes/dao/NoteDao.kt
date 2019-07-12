package com.ife.androidarchitecturepractice.database_classes.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ife.androidarchitecturepractice.database_classes.entities.NoteEntity

@Dao
interface NoteDao
{
    @Insert
    fun insertNote(noteEntity: NoteEntity)

    @Update
    fun updateNote(noteEntity: NoteEntity)

    @Delete
    fun deleteSingleNote(noteEntity: NoteEntity)

    @Query("DELETE FROM NoteEntity")
    fun deleteAllNotes()

    @Query("SELECT * FROM NoteEntity ORDER BY priority DESC")
    fun getAllNotes(): LiveData<ArrayList<NoteEntity>>
}