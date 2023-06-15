package com.example.book_my_book.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.book_my_book.BookMyBook
import com.example.book_my_book.R
import com.example.book_my_book.databinding.FragmentHomeBinding
import com.example.book_my_book.models.Book

enum class DisplayMode {
    All,
    Rented,
}

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private var _displayMode = DisplayMode.All
    private lateinit var _btnDisplayAll: Button
    private lateinit var _btnDisplayRented: Button

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
        //get view elements
        _btnDisplayAll = root.findViewById(R.id.btnDisplayAll)
        _btnDisplayRented = root.findViewById(R.id.btnDisplayRented)
        // add listener
        _btnDisplayAll.setOnClickListener { switchDisplayMode(DisplayMode.All) }
        _btnDisplayRented.setOnClickListener { switchDisplayMode(DisplayMode.Rented) }

        val books = getBooks()
        for (book in books) {
            println(book.title)
        }

        return root
    }

    private fun switchDisplayMode(displayMode: DisplayMode) {
        this._displayMode = displayMode
        when (this._displayMode) {
            DisplayMode.All -> {
                _btnDisplayAll.setBackgroundColor(Color.parseColor("#6200ee"))
                _btnDisplayAll.setTextColor(Color.WHITE)
                _btnDisplayRented.setBackgroundColor(Color.WHITE)
                _btnDisplayRented.setTextColor(Color.BLACK)
            }
            DisplayMode.Rented -> {
                _btnDisplayAll.setBackgroundColor(Color.WHITE)
                _btnDisplayAll.setTextColor(Color.BLACK)
                _btnDisplayRented.setBackgroundColor(Color.parseColor("#6200ee"))
                _btnDisplayRented.setTextColor(Color.WHITE)
            }
            else -> {}
        }
    }

    private fun getBooks(): List<Book> {
        return BookMyBook.db.bookDao().getAll()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}