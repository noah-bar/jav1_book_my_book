package com.example.book_my_book.ui.addBook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.book_my_book.BookMyBook
import com.example.book_my_book.R
import com.example.book_my_book.databinding.FragmentAddBookBinding
import com.example.book_my_book.models.Book
import com.example.book_my_book.models.BookDao

class AddBookFragment : Fragment() {

    private var _binding: FragmentAddBookBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBookBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val btnAddBook = root.findViewById<Button>(R.id.btnAddBook)
        btnAddBook.setOnClickListener { handleAddBookClick() }
        return root
    }

    private fun handleAddBookClick() {
        val book = Book(title = "Mon livre", ISBN = "123", imageUrl = "foo")
        BookMyBook.db.bookDao().insert(book)
        Toast.makeText(requireContext(), "Livre créé", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}