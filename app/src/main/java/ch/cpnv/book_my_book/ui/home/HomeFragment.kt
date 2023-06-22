package ch.cpnv.book_my_book.ui.home

import RwBookAdapter
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.cpnv.book_my_book.BookMyBook
import ch.cpnv.book_my_book.R
import ch.cpnv.book_my_book.databinding.FragmentHomeBinding
enum class DisplayMode {
    All,
    Rented,
}

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private var _displayMode = DisplayMode.All
    private lateinit var _btnDisplayAll: Button
    private lateinit var _btnDisplayRented: Button
    private lateinit var _rwBooks: RecyclerView
    private lateinit var _swBooks: SearchView

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
        _rwBooks = root.findViewById(R.id.rwBooks)
        _rwBooks.layoutManager = LinearLayoutManager(requireContext())
        _swBooks = root.findViewById(R.id.svBooks)
        // add listener
        _btnDisplayAll.setOnClickListener {
            switchDisplayMode(DisplayMode.All)
            displayBooks(this._displayMode)
        }
        _btnDisplayRented.setOnClickListener {
            switchDisplayMode(DisplayMode.Rented)
            displayBooks(this._displayMode)
        }
        _swBooks.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchBook(newText)
                return true
            }
        })

        this.switchDisplayMode(this._displayMode)
        this.displayBooks()

        return root
    }

    private fun displayBooks(displayMode: DisplayMode = DisplayMode.All) {
        if (displayMode == DisplayMode.All) {
            val books = BookMyBook.db.bookDao().getAll()
            _rwBooks.adapter = RwBookAdapter(books, parentFragmentManager)
        } else if (displayMode == DisplayMode.Rented) {
            val books = BookMyBook.db.bookDao().getLoansBooks()
            _rwBooks.adapter = RwBookAdapter(books, parentFragmentManager)
        }

    }
    private fun searchBook(query: String) {
        if (query.isEmpty()) {
            this.displayBooks(this._displayMode)
        } else {
            val books = BookMyBook.db.bookDao().searchByTitle(query)
            _rwBooks.adapter = RwBookAdapter(books, parentFragmentManager)
        }
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
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}