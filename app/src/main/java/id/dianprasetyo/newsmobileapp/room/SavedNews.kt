package id.dianprasetyo.newsmobileapp.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "savednews")
@Parcelize
data class SavedNews (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "headline")
    var headline: String? = null,

    @ColumnInfo(name = "url")
    var url: String? = null

)  : Parcelable