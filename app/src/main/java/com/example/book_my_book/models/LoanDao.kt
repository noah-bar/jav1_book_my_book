package com.example.book_my_book.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Query

@Dao
interface LoanDao {
    @Query("SELECT * FROM loans")
    fun getAll(): List<Loan>

    @Insert
    fun insert(vararg loans: Loan)

    @Delete
    fun delete(loan: Loan)

}
