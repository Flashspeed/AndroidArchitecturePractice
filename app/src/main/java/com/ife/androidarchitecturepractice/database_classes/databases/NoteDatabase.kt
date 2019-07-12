package com.ife.androidarchitecturepractice.database_classes.databases

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ife.androidarchitecturepractice.database_classes.dao.NoteDao
import com.ife.androidarchitecturepractice.database_classes.entities.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class NoteDatabase : RoomDatabase()
{
    abstract fun noteDao(): NoteDao


    // Singleton that returns an instance of this database
    companion object NoteDatabaseSingleton
    {
        lateinit var instance: NoteDatabase

        fun getDatabaseInstance(context: Context): NoteDatabase
        {
            instance = Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "note_database"
            )
                /**
                 * Link: https://developer.android.com/training/data-storage/room/migrating-db-versions#handle-missing-migrations
                 * After updating your database's schemas, it's possible that some on-device databases
                 * could still use an older schema version. If Room cannot find a migration rule for
                 * upgrading that device's database from the older version to the current version, an
                 * IllegalStateException occurs. To prevent the app from crashing when this situation occurs,
                 * call the fallbackToDestructiveMigration() builder method when creating the database
                 */
                .fallbackToDestructiveMigration()

                // Run the AsyncTask to populate the database with data when its run for the first time
                .addCallback(object : RoomDatabase.Callback()
                {
                    override fun onCreate(db: SupportSQLiteDatabase)
                    {
                        super.onCreate(db)
                        AsyncTaskPopulateDatabaseOnFirstRun(instance).execute()
                    }
                })
                .build()

            return instance
        }

        class AsyncTaskPopulateDatabaseOnFirstRun(
            private val noteDatabase: NoteDatabase,
            private val noteDao: NoteDao = noteDatabase.noteDao()
        ) : AsyncTask<Unit, Unit, Unit>()
        {
            override fun doInBackground(vararg params: Unit?)
            {
                noteDao.insertNote(NoteEntity(title = "Title 1", description = "Description 1", priority = 1))
                noteDao.insertNote(NoteEntity(title = "Title 2", description = "Description 2", priority = 2))
                noteDao.insertNote(NoteEntity(title = "Title 3", description = "Description 3", priority = 3))
            }
        }
    }

//    val roomCallback = object: RoomDatabase.Callback(){
//        override fun onCreate(db: SupportSQLiteDatabase)
//        {
//            super.onCreate(db)
//            AsyncTaskPopulateDatabaseOnFirstRun(NoteDatabaseSingleton.instance).execute()
//        }
//    }
}