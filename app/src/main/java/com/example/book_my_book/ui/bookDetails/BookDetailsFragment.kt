package com.example.book_my_book.ui.bookDetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.book_my_book.BookMyBook
import com.example.book_my_book.R

class BookDetailsFragment : Fragment() {
    private var bookId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appBar = (requireActivity() as AppCompatActivity).supportActionBar
        appBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
        arguments?.let {
            bookId = it.getInt("bookId")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                NavHostFragment.findNavController(requireParentFragment()).popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bookId?.let { displayBook(it) }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_details, container, false)
    }

    private fun displayBook(bookId: Int)
    {
        val book = BookMyBook.db.bookDao().findById(bookId)
    }
}