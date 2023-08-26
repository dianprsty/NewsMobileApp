package id.dianprasetyo.newsmobileapp.room


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SavedNewsDao {

    @Query("SELECT * FROM savednews ORDER BY id DESC")
    fun getAllSavedNews(): LiveData<List<SavedNews>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSavedNews(savedNews: SavedNews)

    @Delete
    fun deleteSavedNews(savedNews: SavedNews)
}