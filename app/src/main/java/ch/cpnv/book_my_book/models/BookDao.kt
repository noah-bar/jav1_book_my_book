package ch.cpnv.book_my_book.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Query

@Dao
interface BookDao {
    @Query("SELECT * FROM books")
    fun getAll(): List<Book>

    @Query("SELECT * FROM books WHERE title LIKE :title limit 1")
    fun findByTitle(title: String): Book

    @Query("SELECT * FROM books WHERE id=:id")
    fun findById(id: Int): Book

    @Query("SELECT * FROM books WHERE title LIKE '%' || :title || '%'")
    fun searchByTitle(title: String): List<Book>

    @Query("SELECT books.* FROM books INNER JOIN loans ON loans.bookId = books.id AND loans.status = \"lent\"")
    fun getLoansBooks(): List<Book>

    @Insert
    fun insert(vararg books: Book)

    @Delete
    fun delete(book: Book)

    @Query("DELETE FROM books where id=:bookId")
    fun deleteById(bookId: Int)

}
