package com.example.library_project.view.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.library_project.R;
import com.example.library_project.data.constants.Common;
import com.example.library_project.data.local.DB_Handler;
import com.example.library_project.data.local.MySharedPreferences;
import com.example.library_project.data.models.Book;
import com.example.library_project.data.models.Category;
import com.example.library_project.data.models.Favorite;
import com.example.library_project.view.adapter.BookListAdapter;
import com.example.library_project.view.ui.interfaces.ShowBackButton;
import com.example.library_project.view.ui.interfaces.ToolbarTitle;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BooksFragment extends Fragment {

    int cat_id = 0;
    ToolbarTitle toolbarTitleCallback;
    GridView booksGrid;

    BookListAdapter bookListAdapter;
    ShowBackButton showBackButtonCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        toolbarTitleCallback = (ToolbarTitle) context;
        showBackButtonCallback = (ShowBackButton) context;
    }

    public BooksFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_books, container,false);
        booksGrid = v.findViewById(R.id.books_grid);

        // get category id
        Bundle args = getArguments();
        assert args != null;
        cat_id = args.getInt(Common.CAT_ID_KEY);

        if (cat_id > 0) {
            // Show Back Button and Set Title
            showBackButtonCallback.showBackButton();
            toolbarTitleCallback.setToolbarTitle(args.getString(Common.CAT_NAME));
            toolbarTitleCallback.saveBooksTitle(cat_id, args.getString(Common.CAT_NAME));
        }
        DB_Handler db_handler = new DB_Handler(getContext());
        List<Favorite> categoryList = db_handler.getListFavorite();

        List<Book> list = new ArrayList<Book>();
        final MySharedPreferences mySharedPreferences = new MySharedPreferences(this.getContext());

        Call<List<Book>> listCall = MainActivity.apiInterface.getListBooks(mySharedPreferences.getTokenValue(), cat_id);
        listCall.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                for (Book book : response.body()) {
                    for (Favorite favorite : categoryList){
                        if (book.getId() == favorite.getBookId()){
                            book.setHeart(true);
                            Log.e("Book have ",""+ book.toString());
                        }
                    }
                    list.add(book);
                }
                    booksGrid.setNumColumns(2);
                    bookListAdapter = new BookListAdapter(getActivity(), list);
                    bookListAdapter.notifyDataSetChanged();
                    booksGrid.setAdapter(bookListAdapter);

                if (response.body().size() == 0){
                    Toast.makeText(getActivity(), "Not have book in this category", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.e("ErrorGetBook", t.getMessage());
            }
        });
        return v;
    }

}
