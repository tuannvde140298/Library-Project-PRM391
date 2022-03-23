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

import com.bumptech.glide.Glide;
import com.example.library_project.R;
import com.example.library_project.data.constants.Common;
import com.example.library_project.data.local.DB_Handler;
import com.example.library_project.data.models.Book;
import com.example.library_project.view.ui.DetailBookFragment;
import com.example.library_project.view.ui.MainActivity;

import java.util.List;

public class BookListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Book> mBookList;

    public BookListAdapter(Context context, List<Book> bookList) {
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

        rowView = inflater.inflate(R.layout.view_favorite_book_vertical, null);
        holder.name = rowView.findViewById(R.id.name_book);
        holder.des = rowView.findViewById(R.id.des_book);
        holder.heart = rowView.findViewById(R.id.heart);
        holder.imgBook = rowView.findViewById(R.id.image_book);

        holder.name.setText(mBookList.get(position).getName());
        holder.des.setText(mBookList.get(position).getAuthor());
        if (mBookList.get(position).getCover_url().equals("")){
            holder.imgBook.setImageDrawable(context.getDrawable(R.drawable.img_book));
        } else  {
            Glide.with(context).load(mBookList.get(position).getCover_url()).into(holder.imgBook);
        }

        if (mBookList.get(position).getHeart()) {
            holder.heart.setImageResource(R.drawable.ic_heart_grey);
        }

        holder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB_Handler db_handler = new DB_Handler(context);
                if (!mBookList.get(position).getHeart()) {
                    holder.heart.setImageResource(R.drawable.ic_heart_grey);
                    if (db_handler.addFavorite(String.valueOf(mBookList.get(position).getId())) > 0) {
                        mBookList.get(position).setHeart(true);
                        Toast.makeText(context, "Added To Favorite", Toast.LENGTH_LONG).show();
                    }
                } else {
                    holder.heart.setImageResource(R.drawable.ic_heart_grey600_24dp);
                    if (db_handler.removeFavorite(mBookList.get(position).getId())) {
                        mBookList.get(position).setHeart(false);
                        Toast.makeText(context, "Removed To Favorite", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        // Book Item Click
        holder.itemLay = rowView.findViewById(R.id.item_book);
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
        TextView name, des;
        ImageView heart, imgBook;
    }
}
