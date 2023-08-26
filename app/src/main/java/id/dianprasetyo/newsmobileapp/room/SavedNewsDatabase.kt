package id.dianprasetyo.newsmobileapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [SavedNews::class], version = 1, exportSchema = false)
abstract class SavedNewsDatabase : RoomDatabase() {

    abstract fun savedNewsDao() : SavedNewsDao

    companion object {
        @Volatile
        private var INSTANCE: SavedNewsDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): SavedNewsDatabase {
            if (INSTANCE == null) {
                synchronized(SavedNewsDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        SavedNewsDatabase::class.java,
                        "savednews_database"
                    ).build()
                }
            }
            return INSTANCE as SavedNewsDatabase
        }
    }
}