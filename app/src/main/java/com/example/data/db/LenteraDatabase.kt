package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.DisciplineRecord
import com.example.data.model.Student
import com.example.data.model.WeeklyEvaluation

@Database(
    entities = [Student::class, WeeklyEvaluation::class, DisciplineRecord::class],
    version = 1,
    exportSchema = false
)
abstract class LenteraDatabase : RoomDatabase() {
    abstract fun lenteraDao(): LenteraDao

    companion object {
        @Volatile
        private var INSTANCE: LenteraDatabase? = null

        fun getDatabase(context: Context): LenteraDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LenteraDatabase::class.java,
                    "lentera_character_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
