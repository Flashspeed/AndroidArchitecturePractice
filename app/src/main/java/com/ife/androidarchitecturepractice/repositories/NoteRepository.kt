package com.ife.androidarchitecturepractice.repositories

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.ife.androidarchitecturepractice.database_classes.dao.NoteDao
import com.ife.androidarchitecturepractice.database_classes.databases.NoteDatabase
import com.ife.androidarchitecturepractice.database_classes.entities.NoteEntity

/**
 * This class is used to make it easier to interact with the Note database
 */
class NoteRepository(
    private val context: Context,
    private val noteDatabase: NoteDatabase = NoteDatabase.getDatabaseInstance(context = context),
    private var noteDao: NoteDao = noteDatabase.noteDao(),
    private val allNotes: LiveData<ArrayList<NoteEntity>> = noteDao.getAllNotes()
)
{
    fun insertNote(note: NoteEntity) = AsyncTaskInsertNote(noteDao).execute(note)

    fun updateNote(note: NoteEntity) = AsyncTaskUpdateNote(noteDao).execute(note)

    fun deleteNote(note: NoteEntity) = AsyncTaskDeleteNote(noteDao).execute(note)

    fun deleteAllNotes() = AsyncTaskDeleteAllNotes(noteDao).execute()

    fun getAllNotes(): LiveData<ArrayList<NoteEntity>> = allNotes

    private class AsyncTaskInsertNote(val noteDao: NoteDao) : AsyncTask<NoteEntity, Unit, Unit>()
    {
        override fun doInBackground(vararg note: NoteEntity?)
        {
            noteDao.insertNote(note[0]!!)
        }
    }

    private class AsyncTaskUpdateNote(val noteDao: NoteDao) : AsyncTask<NoteEntity, Unit, Unit>()
    {
        override fun doInBackground(vararg note: NoteEntity?)
        {
            noteDao.updateNote(note[0]!!)
        }
    }

    private class AsyncTaskDeleteNote(val noteDao: NoteDao) : AsyncTask<NoteEntity, Unit, Unit>()
    {
        override fun doInBackground(vararg note: NoteEntity?)
        {
            noteDao.deleteSingleNote(note[0]!!)
        }
    }

    private class AsyncTaskDeleteAllNotes(val noteDao: NoteDao) : AsyncTask<Unit, Unit, Unit>()
    {
        override fun doInBackground(vararg params: Unit?)
        {
            noteDao.deleteAllNotes()
        }
    }
}