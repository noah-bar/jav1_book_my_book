package ch.cpnv.book_my_book.models
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "isbn")
    val ISBN: String,

    @ColumnInfo(name = "imageUrl")
    val imageUrl: String,
)
