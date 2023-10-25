package com.bima.mystoryapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bima.mystoryapp.data.response.ListStoryItem

@Database(
    entities = [ListStoryItem::class],
    version = 1,
    exportSchema = false
)
abstract class StoryDataBase : RoomDatabase() {

    abstract fun storyDao() : StoryDao

    companion object {
        @Volatile
        private var INSTANCE: StoryDataBase? = null

        @JvmStatic
        fun getDatabase(context: Context): StoryDataBase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    StoryDataBase::class.java, "story_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}