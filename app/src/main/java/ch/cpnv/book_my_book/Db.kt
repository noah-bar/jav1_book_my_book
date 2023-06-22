package ch.cpnv.book_my_book

import ch.cpnv.book_my_book.models.Book
import ch.cpnv.book_my_book.models.Loan
import androidx.room.Database
import androidx.room.RoomDatabase
import ch.cpnv.book_my_book.models.BookDao
import ch.cpnv.book_my_book.models.LoanDao

@Database(entities = [Book::class, Loan::class], version = 1)
abstract class Db : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun loanDao(): LoanDao
}