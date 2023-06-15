package com.example.book_my_book.models

import  com.example.book_my_book.models.Book
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookDao {
    @Query("SELECT * FROM books")
    fun getAll(): List<Book>

    @Query("SELECT * FROM books WHERE title LIKE :title LIMIT 1")
    fun findByTitle(title: String): Book

    @Insert
    fun insert(vararg books: Book)

    @Delete
    fun delete(user: Book)

}
