package com.example.library_project.view.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.library_project.R;
import com.example.library_project.data.local.DB_Handler;
import com.example.library_project.data.local.MySharedPreferences;
import com.example.library_project.data.models.Book;
import com.example.library_project.data.models.Category;
import com.example.library_project.data.models.Favorite;
import com.example.library_project.view.adapter.BookListAdapter;
import com.example.library_project.view.adapter.FavorityListAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteFragment extends Fragment {

    GridView favoriteGrid;
    FavorityListAdapter favorityListAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    public FavoriteFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favorite, container,false);

        favoriteGrid = v.findViewById(R.id.favorites_grid);

        List<Book> bookList = new ArrayList<Book>();
        final MySharedPreferences mySharedPreferences = new MySharedPreferences(this.getContext());

        List<Book> list = new ArrayList<Book>();
        DB_Handler db_handler = new DB_Handler(getContext());
        List<Favorite> categoryList = db_handler.getListFavorite();
//        Log.e("categoryList","Size"+ categoryList.get(0).getBookId());

        Call<List<Book>> listCall = MainActivity.apiInterface.getListBookFavorite(mySharedPreferences.getTokenValue());
        listCall.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                    for (Book book : response.body()) {
                        for (Favorite favorite : categoryList){
                            if (book.getId() == favorite.getBookId()){
                                list.add(book);
                                Log.e("Book have ",""+ list.toString());
                            }
                        }
                    }
                favoriteGrid.setNumColumns(2);
                favorityListAdapter = new FavorityListAdapter(getActivity(), list);
                favorityListAdapter.notifyDataSetChanged();
                favoriteGrid.setAdapter(favorityListAdapter);
            }
            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.e("ErrorGetBook", t.getMessage());
            }
        });

        return v;
    }
}
