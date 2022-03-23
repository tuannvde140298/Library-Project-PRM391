package com.example.library_project.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.library_project.R;
import com.example.library_project.data.constants.Common;
import com.example.library_project.data.local.DB_Handler;
import com.example.library_project.data.models.Book;
import com.example.library_project.view.ui.DetailBookFragment;
import com.example.library_project.view.ui.MainActivity;

import java.util.List;

public class FavorityListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Book> mBookList;

    public FavorityListAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.mBookList = bookList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mBookList.size();
    }

    @Override
    public Object getItem(int i) {
        return mBookList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        // TODO Auto-generated method stub
        final Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.view_book_favorite, null);
        holder.name = rowView.findViewById(R.id.name_book_favo);
        holder.category = rowView.findViewById(R.id.cate_book_favo);
        holder.remove = rowView.findViewById(R.id.remove_favo);

        holder.name.setText(mBookList.get(position).getName());
        holder.category.setText("Category " + mBookList.get(position).getCategory_id());

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB_Handler db_handler = new DB_Handler(context);
                if (db_handler.removeFavorite(mBookList.get(position).getId())) {
                    mBookList.remove(position);
                    notifyDataSetChanged();
                }
            }
        });

        // Book Item Click
        holder.itemLay = rowView.findViewById(R.id.item_book_favo);
        holder.itemLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                FragmentManager fm = ((MainActivity) context).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                // add bundle arguments
                bundle.putInt(Common.BOOK_ID, mBookList.get(position).getId());

                DetailBookFragment detailBookFragment = new DetailBookFragment();
                detailBookFragment.setArguments(bundle);

                ft.replace(R.id.frame_main, detailBookFragment, Common.FRAG_BDETAIL);
                ft.addToBackStack(null);
                ft.commit();
            }
        });


        return rowView;
    }

    public class Holder {
        LinearLayout itemLay;
        TextView name, category;
        ImageView remove;
    }
}
