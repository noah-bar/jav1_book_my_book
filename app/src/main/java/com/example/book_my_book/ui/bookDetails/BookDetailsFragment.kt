package com.example.book_my_book.ui.bookDetails

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.book_my_book.BookMyBook
import com.example.book_my_book.R
import com.example.book_my_book.databinding.FragmentBookDetailsBinding
import com.example.book_my_book.models.Book
import com.example.book_my_book.models.Loan
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class BookDetailsFragment : Fragment() {
    private var bookId: Int? = null
    private var _binding: FragmentBookDetailsBinding? = null
    private lateinit var _twTitle: TextView
    private lateinit var _etLoanTo: EditText
    private lateinit var _etLoanAt: EditText
    private lateinit var _btn: Button

    private lateinit var _book: Book
    private var _currentLoan: Loan? = null
    private val binding get() = _binding!!

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
        _binding = FragmentBookDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        _twTitle = root.findViewById(R.id.twBookDetailsTitle)
        _etLoanTo = root.findViewById(R.id.etBookDetailsLoanTo)
        _etLoanAt = root.findViewById(R.id.etBookDetailsLoanAt)
        _btn = root.findViewById(R.id.btnBookDetails)
        bookId?.let { getBook(it) }

        _btn.setOnClickListener { handleClick() }
        // Inflate the layout for this fragment
        return root
    }

    private fun getBook(bookId: Int) {
        _book= BookMyBook.db.bookDao().findById(bookId)
        _twTitle.text = _book.title

        getCurrentLoan()
    }

    private fun getCurrentLoan() {
        _currentLoan = BookMyBook.db.loanDao().getLastByBookId(_book.id)
        _currentLoan?.let {
            _etLoanAt?.setText(it.loanAt)
            _etLoanTo?.setText(it.loanTo)
            _btn.text = "Rendre"
        }

        if(_currentLoan == null) {

            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            val currentDate = LocalDate.now().format(formatter)
            _etLoanAt?.setText(currentDate.toString())
            _etLoanTo.text.clear()
            _btn.text = "Prêter"
        }
    }

    private fun handleClick() {
        if(_currentLoan == null) {
            val loanAt = _etLoanAt.text.toString()
            val loanTo = _etLoanTo.text.toString()
            createLoan(loanTo, loanAt)
            getCurrentLoan()
            return
        }
        BookMyBook.db.loanDao().updateStatusById("rendered", _currentLoan!!.id)
        getCurrentLoan()
    }

    private fun createLoan(loanTo: String, loanAt: String) {
        val newLoan = Loan(loanTo=loanTo, loanAt=loanAt, bookId=_book.id, status="lent")
        BookMyBook.db.loanDao().insert(newLoan);
    }
}