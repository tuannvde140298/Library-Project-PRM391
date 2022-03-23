package com.example.library_project.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.library_project.R;
import com.example.library_project.data.local.MySharedPreferences;
import com.example.library_project.data.models.Category;
import com.example.library_project.view.adapter.CategoryListAdapter;

import java.util.List;

public class CategoriesFragment extends Fragment {

    public CategoriesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_categories, container,false);

        final MySharedPreferences mySharedPreferences = new MySharedPreferences(this.getContext());
        List<Category> categoryList = mySharedPreferences.getCategories();
        ListView listView = v.findViewById(R.id.listview);
        listView.setAdapter(new CategoryListAdapter(getActivity(), categoryList));

        return v;
    }
}
