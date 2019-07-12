package com.ife.androidarchitecturepractice.database_classes.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity//(tableName = "note")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    private val id: Long? = null,

    val title: String,

    val description: String,

    val priority: Int
)