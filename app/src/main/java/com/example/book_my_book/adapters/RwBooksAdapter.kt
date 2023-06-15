import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.book_my_book.R
import com.example.book_my_book.models.Book
import com.squareup.picasso.Picasso
import com.squareup.picasso.Callback

class RwBookAdapter(private val books: List<Book>) :
    RecyclerView.Adapter<RwBookAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = books[position]
        holder.tvTitle.text = book.title
        holder.tvISBN.text = book.ISBN
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

        //Picasso.get().load("https://static.fnac-static.com/multimedia/Images/FR/NR/07/5c/7c/8150023/1520-1/tsp20160909172707/Harry-Potter-et-l-Enfant-Maudit.jpg").into(holder.ivBook)
    }

    override fun getItemCount(): Int {
        return books.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvISBN: TextView = itemView.findViewById(R.id.tvISBN)
        val ivBook: ImageView = itemView.findViewById(R.id.ivBook)
    }
}
