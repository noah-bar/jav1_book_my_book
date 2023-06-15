package com.example.book_my_book

import com.example.book_my_book.models.Book
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.book_my_book.models.BookDao

@Database(entities = [Book::class], version = 1)
abstract class Db : RoomDatabase() {
    abstract fun bookDao(): BookDao
}