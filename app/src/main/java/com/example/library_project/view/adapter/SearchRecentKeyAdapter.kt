package com.example.library_project.view.adapter

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.library_project.R
import com.example.library_project.data.constants.Common
import com.example.library_project.view.ui.DetailBookFragment
import com.example.library_project.view.ui.SearchInitialFragment
import com.example.library_project.view.ui.SearchResultsFragment

class SearchRecentKeyAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var keyList: List<String> = ArrayList()
    lateinit var searchFragment: SearchInitialFragment

    fun setFragment(fragment: SearchInitialFragment) {
        searchFragment = fragment
    }

    fun setKeyList(keys: List<String>) {
        keyList = keys
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BooksViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_search_recent_item, parent, false),
            searchFragment
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BooksViewHolder -> {
                holder.bind(keyList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return keyList.size
    }

    class BooksViewHolder(itemView: View, fragment: SearchInitialFragment) :
        RecyclerView.ViewHolder(itemView) {
        private val item = itemView.findViewById<TextView>(R.id.recent_search_item)
        val fragment = fragment

        fun bind(key: String) {
            item.text = key;
            item.setOnClickListener {
                val searchResultsFragment = SearchResultsFragment()
                val bundle = Bundle()
                bundle.putString(Common.SEARCH_KEY, key)
                searchResultsFragment.arguments = bundle
                val ft = fragment.activity?.supportFragmentManager?.beginTransaction()
                ft
                    ?.add(R.id.frame_main, searchResultsFragment)
                    ?.addToBackStack(null)
                    ?.commit()
            }
        }
    }
}