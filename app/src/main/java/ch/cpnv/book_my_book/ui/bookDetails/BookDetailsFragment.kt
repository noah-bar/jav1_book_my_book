package ch.cpnv.book_my_book.ui.bookDetails

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import ch.cpnv.book_my_book.BookMyBook
import ch.cpnv.book_my_book.R
import ch.cpnv.book_my_book.databinding.FragmentBookDetailsBinding
import ch.cpnv.book_my_book.models.Book
import ch.cpnv.book_my_book.models.Loan
import java.time.LocalDate
import java.time.format.DateTimeFormatter
class BookDetailsFragment : Fragment() {
    private var bookId: Int? = null
    private var _binding: FragmentBookDetailsBinding? = null
    private lateinit var _twTitle: TextView
    private lateinit var _etLoanTo: EditText
    private lateinit var _etLoanAt: EditText
    private lateinit var _btnLoanBook: Button

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
        val btnDeleteBook = root.findViewById<Button>(R.id.btnDeleteBook)
        val btnGetContacts = root.findViewById<Button>(R.id.btnGetContacts)
        _twTitle = root.findViewById(R.id.twBookDetailsTitle)
        _etLoanTo = root.findViewById(R.id.etBookDetailsLoanTo)
        _etLoanAt = root.findViewById(R.id.etBookDetailsLoanAt)
        _btnLoanBook = root.findViewById(R.id.btnLoanBook)
        bookId?.let { getBook(it) }


        //Listeners
        _btnLoanBook.setOnClickListener { handleClick() }

        btnGetContacts.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
            resultLauncher.launch(intent)
        }

        btnDeleteBook.setOnClickListener {
            bookId?.let {
                val builder = AlertDialog.Builder(this.requireContext())
                builder.setMessage("Êtes-vous sur de vouloir supprimer ce livre ?")
                    .setCancelable(false)
                    .setPositiveButton("Oui") { _, _ ->
                        deleteBook(it)
                    }
                    .setNegativeButton("Non") { dialog, _ ->
                        // Dismiss the dialog
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            }
        }

        // Inflate the layout for this fragment
        return root
    }

    private fun getBook(bookId: Int) {
        _book = BookMyBook.db.bookDao().findById(bookId)
        _twTitle.text = _book.title

        getCurrentLoan()
    }

    private fun deleteBook(bookId: Int) {
        BookMyBook.db.loanDao().deleteByBookId(bookId)
        BookMyBook.db.bookDao().deleteById(bookId)
        requireActivity().onBackPressedDispatcher.onBackPressed();
    }

    private fun getCurrentLoan() {
        _currentLoan = BookMyBook.db.loanDao().getLastByBookId(_book.id)
        _currentLoan?.let {
            _etLoanAt?.setText(it.loanAt)
            _etLoanTo?.setText(it.loanTo)
            _btnLoanBook.text = "Rendre"
        }

        if(_currentLoan == null) {
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            val currentDate = LocalDate.now().format(formatter)
            _etLoanAt?.setText(currentDate.toString())
            _etLoanTo.text.clear()
            _btnLoanBook.text = "Prêter"
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

    @SuppressLint("Range")
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val contactUri = data?.data
            val cursor = contactUri?.let {
                requireActivity().contentResolver.query(
                    it,
                    null,
                    null,
                    null,
                    null
                )
            }

            cursor?.use {
                if (it.moveToFirst()) {
                    _etLoanTo?.setText(
                        it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                        )
                    )
                }
            }
        }
    }
}