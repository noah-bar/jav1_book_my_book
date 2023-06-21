package com.example.book_my_book

import androidx.room.Room
import android.app.Application
import androidx.room.RoomDatabase
import com.example.book_my_book.Db

class BookMyBook : Application() {
    companion object {
        lateinit var db: Db
    }

    override fun onCreate() {
        super.onCreate()
        //applicationContext.deleteDatabase("my-database"); //delete database before loading (only on dev)
        db = Room.databaseBuilder(
            applicationContext,
            Db::class.java,
            "my-database"
        ).allowMainThreadQueries().build()
    }
}
