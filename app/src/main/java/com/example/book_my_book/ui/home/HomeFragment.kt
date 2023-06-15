package com.example.book_my_book.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.book_my_book.BookMyBook
import com.example.book_my_book.databinding.FragmentAddBookBinding
import com.example.book_my_book.databinding.FragmentHomeBinding
import com.example.book_my_book.models.Book

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val books = getBooks()
        for (book in books) {
            println(book.title)
        }

        return root
    }

    private fun getBooks(): List<Book> {
        return BookMyBook.db.bookDao().getAll()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}