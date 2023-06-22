package ch.cpnv.book_my_book.models
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "loans")
data class Loan(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "loanTo")
    val loanTo: String,

    @ColumnInfo(name = "loanAt")
    val loanAt: String,

    @ColumnInfo(name = "status")
    val status: String,

    @ColumnInfo(name = "bookId")
    val bookId: Int,
)
