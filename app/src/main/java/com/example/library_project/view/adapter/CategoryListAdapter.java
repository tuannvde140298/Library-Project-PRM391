package com.example.library_project.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.library_project.R;
import com.example.library_project.data.constants.Common;
import com.example.library_project.data.models.Category;
import com.example.library_project.view.ui.BooksFragment;
import com.example.library_project.view.ui.MainActivity;

import java.util.List;

public class CategoryListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Category> categoryList;

    public CategoryListAdapter(Context context, List<Category> categoryList)
    {
        this.context = context;
        this.categoryList = categoryList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int i) {
        return categoryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.single_row_category, null);
        holder.tvCategory = rowView.findViewById(R.id.tv_category);
        holder.tvCategory.setText(categoryList.get(position).getName());

        holder.imgCategory = rowView.findViewById(R.id.img_category);
        if (categoryList.get(position).getImageUrl().equals("")){
            holder.imgCategory.setImageDrawable(context.getDrawable(R.drawable.img_login));
        } else {
            Glide.with(context).load(categoryList.get(position).getImageUrl()).into(holder.imgCategory);
        }

        holder.relativeLayout = rowView.findViewById(R.id.item_category);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = categoryList.get(position).getId();
                // initialize bundle and fragment manager
                Bundle bundle = new Bundle();
                FragmentManager fm = ((MainActivity) context).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                // add bundle arguments
                bundle.putInt(Common.CAT_ID_KEY, id);
                bundle.putString(Common.CAT_NAME, categoryList.get(position).getName());

                BooksFragment bookFragment = new BooksFragment();
                bookFragment.setArguments(bundle);

                ft.replace(R.id.frame_main, bookFragment, Common.FRAG_BOOK);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        return rowView;
    }

    public class Holder {
        TextView tvCategory;
        ImageView imgCategory;
        RelativeLayout relativeLayout;
    }
}
