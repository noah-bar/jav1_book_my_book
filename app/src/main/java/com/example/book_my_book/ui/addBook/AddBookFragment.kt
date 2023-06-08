package com.example.book_my_book.ui.addBook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.book_my_book.databinding.FragmentAddBookBinding

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
        val addBookViewModel =
            ViewModelProvider(this).get(AddBookViewModel::class.java)

        _binding = FragmentAddBookBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textAddBook
        addBookViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}