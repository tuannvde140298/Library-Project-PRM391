package com.example.library_project.view.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library_project.R;
import com.example.library_project.data.constants.Common;
import com.example.library_project.data.local.MySharedPreferences;
import com.example.library_project.data.models.Book;
import com.example.library_project.data.models.Category;
import com.example.library_project.data.remote.ApiClient;
import com.example.library_project.data.remote.ApiInterface;
import com.example.library_project.view.adapter.HomeBookListAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultsFragment extends Fragment {
    private EditText searchBar;
    private List<Book> books = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private HomeBookListAdapter adapter = new HomeBookListAdapter(R.layout.view_search_result_item);
    private RecyclerView searchResultsView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_results, container, false);
        LinearLayout searchFrame = getActivity().findViewById(R.id.search_bar_frame);
        searchFrame.setVisibility(View.VISIBLE);
        final MySharedPreferences mySharedPreferences = new MySharedPreferences(this.getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());
        searchBar = getActivity().findViewById(R.id.search_bar);

        Bundle args = getArguments();
        if (args != null){
            searchBar.setText(args.getString(Common.SEARCH_KEY));
        }
        adapter.setFragment(this);

        searchResultsView = v.findViewById(R.id.result_search_list);
        searchResultsView.setLayoutManager(linearLayoutManager);
        ApiClient.getApiClient()
                .create(ApiInterface.class)
                .getListBooks(mySharedPreferences.getTokenValue(), searchBar.getText().toString())
                .enqueue(new Callback<List<Book>>() {
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
                            adapter.setBookList(books);

                            searchResultsView.setAdapter(adapter);
                        } else {
                            Log.e(Common.ERROR_GET_BOOK_LIST, "Error when get books!");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Book>> call, Throwable t) {
                        Log.e(Common.ERROR_GET_BOOK_LIST, t.getMessage());
                    }
                });

        return v;
    }
}
