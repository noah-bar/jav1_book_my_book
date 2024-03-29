package ch.cpnv.book_my_book.ui.addBook

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import ch.cpnv.book_my_book.BookMyBook
import ch.cpnv.book_my_book.R
import ch.cpnv.book_my_book.databinding.FragmentAddBookBinding
import ch.cpnv.book_my_book.models.Book
class AddBookFragment : Fragment() {

    private var _binding: FragmentAddBookBinding? = null
    private lateinit var _etBookTitle: EditText

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
        _etBookTitle = root.findViewById(R.id.etBookTitle)
        val etBookISBN = root.findViewById<EditText>(R.id.etBookISBN)
        val etBookImage = root.findViewById<EditText>(R.id.etBookImage)
        btnAddBook.setOnClickListener {
            createBook(
                _etBookTitle.text.toString(),
                etBookISBN.text.toString(),
                etBookImage.text.toString()
            )
            //Clear form
            _etBookTitle.text.clear()
            etBookISBN.text.clear()
            etBookImage.text.clear()
        }
        return root
    }

    private fun createBook(title: String, ISBN: String, imageUrl: String) {
        val book = Book(title = title, ISBN = ISBN, imageUrl = imageUrl)
        BookMyBook.db.bookDao().insert(book)
        Toast.makeText(requireContext(), "Livre créé", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}