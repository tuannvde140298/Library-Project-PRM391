package com.example.library_project.view.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.library_project.R
import com.example.library_project.data.constants.Common
import com.example.library_project.data.models.Book
import com.example.library_project.view.ui.DetailBookFragment
import com.example.library_project.view.ui.HomeFragment
import com.squareup.picasso.Picasso

class HomeBookListAdapter(val layout : Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var bookList: List<Book> = ArrayList()
    lateinit var homeFragment: Fragment

    fun setFragment(fragment: Fragment) {
        homeFragment = fragment
    }

    fun setBookList(books: List<Book>) {
        bookList = books
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BooksViewHolder(
            LayoutInflater.from(parent.context).inflate(layout, parent, false),
            homeFragment
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BooksViewHolder -> {
                holder.bind(bookList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    class BooksViewHolder(itemView: View, fragment: Fragment) :
        RecyclerView.ViewHolder(itemView) {
        private val bookName = itemView.findViewById<TextView>(R.id.name_book)
        private val bookCategory = itemView.findViewById<TextView>(R.id.category_book)
        private val rating = itemView.findViewById<RatingBar>(R.id.rating_book)
        private val image = itemView.findViewById<ImageView>(R.id.image_book)

        val fragment = fragment

        fun bind(book: Book) {
            if (book.cover_url.isNotEmpty()){
                Picasso.get().load(book.cover_url).into(image)
            }
            bookName.text = book.name
            bookCategory.text = book.categoryName
            rating.rating = book.rating

            itemView.setOnClickListener {
                val bookDetailFragment = DetailBookFragment()
                val ft = fragment.activity?.supportFragmentManager?.beginTransaction()
                val searchBar = fragment.activity?.findViewById<LinearLayout>(R.id.search_bar_frame)
                searchBar?.isInvisible = true

                val bundle = Bundle()
                bundle.putInt(Common.BOOK_ID, book.id)
                bookDetailFragment.arguments = bundle

                ft
                    ?.replace(R.id.frame_main, bookDetailFragment)
                    ?.addToBackStack(null)
                    ?.commit()
            }
        }
    }
}