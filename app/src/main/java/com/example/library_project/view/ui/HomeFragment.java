package com.example.library_project.view.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library_project.R;
import com.example.library_project.data.constants.Common;
import com.example.library_project.data.local.MySharedPreferences;
import com.example.library_project.data.models.Book;
import com.example.library_project.data.models.Category;
import com.example.library_project.utils.BookUtility;
import com.example.library_project.view.adapter.HomeBookListAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private EditText searchBar;
    private RecyclerView topPopularView, cateView1, cateView2, cateView3, cateView4, cateView5;
    private TextView allCat1, allCat2, allCat3, allCat4, allCat5;

    private HomeBookListAdapter topViewAdapter = new HomeBookListAdapter(R.layout.view_book_vertical);
    private HomeBookListAdapter cateViewAdapter1 = new HomeBookListAdapter(R.layout.view_book_horizontal);
    private HomeBookListAdapter cateViewAdapter2 = new HomeBookListAdapter(R.layout.view_book_horizontal);
    private HomeBookListAdapter cateViewAdapter3 = new HomeBookListAdapter(R.layout.view_book_horizontal);
    private HomeBookListAdapter cateViewAdapter4 = new HomeBookListAdapter(R.layout.view_book_horizontal);
    private HomeBookListAdapter cateViewAdapter5 = new HomeBookListAdapter(R.layout.view_book_horizontal);

    private List<Book> books = new ArrayList<>();

    public HomeFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        final MySharedPreferences mySharedPreferences = new MySharedPreferences(this.getActivity());
        topViewAdapter.setFragment(this);
        cateViewAdapter1.setFragment(this);
        cateViewAdapter2.setFragment(this);
        cateViewAdapter3.setFragment(this);
        cateViewAdapter4.setFragment(this);
        cateViewAdapter5.setFragment(this);

        MainActivity.apiInterface.getListBooks(mySharedPreferences.getTokenValue()).enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful()) {
                    books = response.body();
                    List<Category> categories = mySharedPreferences.getCategories();
                    for (Book book : books) {
                        for (Category category : categories) {
                            if (book.getCategory_id() == category.getId()) {
                                book.setCategoryName(category.getName());
                                break;
                            }
                        }
                    }

                    topViewAdapter.setBookList(books);

                    topPopularView = v.findViewById(R.id.top_popular_list);
                    cateView1 = v.findViewById(R.id.category_01);
                    cateView2 = v.findViewById(R.id.category_02);
                    cateView3 = v.findViewById(R.id.category_03);
                    cateView4 = v.findViewById(R.id.category_04);
                    cateView5 = v.findViewById(R.id.category_05);

                    topPopularView.setAdapter(topViewAdapter);
                    cateViewAdapter1.setBookList(BookUtility.getBooksByCate(1, books));
                    cateView1.setAdapter(cateViewAdapter1);
                    cateViewAdapter2.setBookList(BookUtility.getBooksByCate(2, books));
                    cateView2.setAdapter(cateViewAdapter2);
                    cateViewAdapter3.setBookList(BookUtility.getBooksByCate(3, books));
                    cateView3.setAdapter(cateViewAdapter3);
                    cateViewAdapter4.setBookList(BookUtility.getBooksByCate(4, books));
                    cateView4.setAdapter(cateViewAdapter4);
                    cateViewAdapter5.setBookList(BookUtility.getBooksByCate(5, books));
                    cateView5.setAdapter(cateViewAdapter5);
                } else {
                    Log.e(Common.ERROR_GET_BOOK_LIST, "Error when get books!");
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.e(Common.ERROR_GET_BOOK_LIST, t.getMessage());
            }
        });

        searchBar = v.findViewById(R.id.search_bar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            searchBar.setDefaultFocusHighlightEnabled(false);
        }
        Intent i = new Intent(this.getActivity(), SearchActivity.class);
        allCat1 = v.findViewById(R.id.cate_01);
        allCat2 = v.findViewById(R.id.cate_02);
        allCat3 = v.findViewById(R.id.cate_03);
        allCat4 = v.findViewById(R.id.cate_04);
        allCat5 = v.findViewById(R.id.cate_05);

        allCat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seeAll(1, "Detective and Mystery");
            }
        });
        allCat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seeAll(2, "Novel");
            }
        });
        allCat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seeAll(3, "Comic Book");
            }
        });
        allCat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seeAll(4, "Action");
            }
        });
        allCat5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seeAll(5, "Adventure");
            }
        });
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i);
                getActivity().finish();
            }
        });
        return v;
    }

    public void seeAll(int catId, String name){
        int id = catId;
        // initialize bundle and fragment manager
        Bundle bundle = new Bundle();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        // add bundle arguments
        bundle.putInt(Common.CAT_ID_KEY, id);
        bundle.putString(Common.CAT_NAME, name);

        BooksFragment bookFragment = new BooksFragment();
        bookFragment.setArguments(bundle);

        ft.replace(R.id.frame_main, bookFragment, Common.FRAG_BOOK);
        ft.addToBackStack(null);
        ft.commit();
    }
}
