import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.book_my_book.BookMyBook
import com.example.book_my_book.R
import com.example.book_my_book.models.Book
import com.squareup.picasso.Picasso
import com.squareup.picasso.Callback

class RwBookAdapter(private val books: List<Book>, private val fragmentManager: FragmentManager) :
    RecyclerView.Adapter<RwBookAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = books[position]
        val currentLoan = BookMyBook.db.loanDao().getLastByBookId(book.id)

        holder.tvTitle.text = book.title
        holder.tvISBN.text = book.ISBN
        if (currentLoan != null) {
            holder.tvLoanTo.text = "Loué à: " + currentLoan.loanTo
            holder.tvLoanAt.text = "Loué le: " + currentLoan.loanAt
        }
        if (book?.imageUrl != null &&  book?.imageUrl != "") {
            Picasso.get()
                .load(book.imageUrl)
                .into(holder.ivBook, object : Callback {
                    override fun onSuccess() {
                        //Nothing to do
                    }

                    override fun onError(e: Exception?) {
                        holder.ivBook.setImageResource(R.drawable.ic_book)
                    }
                })
        } else {
            holder.ivBook.setImageResource(R.drawable.ic_book)
        }
        holder.itemView.setOnClickListener { itemHandleClick(book) };
    }

    private fun itemHandleClick(book: Book) {
        val navHostFragment = fragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)
        val navController = navHostFragment!!.findNavController()
        val bundle = Bundle()
        bundle.putInt("bookId", book.id)
        navController!!.navigate(R.id.action_navigation_home_to_bookDetailsFragment, bundle)
    }

    override fun getItemCount(): Int {
        return books.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvISBN: TextView = itemView.findViewById(R.id.tvISBN)
        val ivBook: ImageView = itemView.findViewById(R.id.ivBook)
        val tvLoanTo: TextView = itemView.findViewById(R.id.tvLoanTo)
        val tvLoanAt: TextView = itemView.findViewById(R.id.tvLoanAt)
    }
}
