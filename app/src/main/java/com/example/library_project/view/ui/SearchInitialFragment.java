package com.example.library_project.view.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library_project.R;
import com.example.library_project.data.local.MySharedPreferences;
import com.example.library_project.view.adapter.SearchRecentKeyAdapter;

public class SearchInitialFragment extends Fragment {
    private RecyclerView recentKeyView;
    private SearchRecentKeyAdapter adapter = new SearchRecentKeyAdapter();
    private EditText searchBar;
    private TextView removeRecent;
    private ImageButton searchBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_initial, container, false);
        final MySharedPreferences mySharedPreferences = new MySharedPreferences(this.getActivity());
        adapter.setFragment(this);
        adapter.setKeyList(mySharedPreferences.getRecentSearchKey());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());

        recentKeyView = v.findViewById(R.id.recent_search_list);
        recentKeyView.setLayoutManager(linearLayoutManager);
        recentKeyView.setAdapter(adapter);

        searchBar = getActivity().findViewById(R.id.search_bar);
        searchBtn = getActivity().findViewById(R.id.btn_search);
        removeRecent = v.findViewById(R.id.remove_recent);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!searchBar.getText().toString().isEmpty()) {
                    mySharedPreferences.putRecentSearchKey(searchBar.getText().toString());

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    Fragment resultFragment = new SearchResultsFragment();
                    FragmentTransaction t = fm.beginTransaction();
                    t.replace(R.id.frame_main, resultFragment);
                    t.commit();
                }
            }
        });

        removeRecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mySharedPreferences.removeRecentKeys();
                adapter.setKeyList(mySharedPreferences.getRecentSearchKey());
                recentKeyView.setAdapter(adapter);
            }
        });
        return v;
    }
}
