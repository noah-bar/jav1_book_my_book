package com.example.book_my_book

import androidx.room.Room
import android.app.Application
import com.example.book_my_book.Db

class BookMyBook : Application() {
    companion object {
        lateinit var db: Db
    }

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            applicationContext,
            Db::class.java,
            "my-database"
        ).allowMainThreadQueries().build()
    }
}
