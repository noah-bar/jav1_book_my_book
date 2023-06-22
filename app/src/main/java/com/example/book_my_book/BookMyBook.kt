package com.example.book_my_book

import androidx.room.Room
import android.app.Application
class BookMyBook : Application() {
    companion object {
        lateinit var db: Db
    }

    override fun onCreate() {
        super.onCreate()
        //applicationContext.deleteDatabase("book-my-book"); //delete database before loading (only on dev)
        db = Room.databaseBuilder(
            applicationContext,
            Db::class.java,
            "book-my-book"
        ).allowMainThreadQueries().build()
    }
}
