package com.example.book_my_book.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Query

@Dao
interface LoanDao {
    @Query("SELECT * FROM loans")
    fun getAll(): List<Loan>

    @Query("SELECT * FROM loans WHERE bookId=:bookId AND status=\"lent\" LIMIT 1")
    fun getLastByBookId(bookId: Int): Loan

    @Query("UPDATE loans set status=:status WHERE id=:loanId")
    fun updateStatusById(status:String, loanId: Int)
    @Insert
    fun insert(vararg loans: Loan)

    @Delete
    fun delete(loan: Loan)

}
