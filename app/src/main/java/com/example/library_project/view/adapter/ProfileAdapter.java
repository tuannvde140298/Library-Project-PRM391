package com.example.library_project.view.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library_project.R;
import com.example.library_project.data.constants.Common;
import com.example.library_project.data.models.Book;
import com.example.library_project.view.ui.DetailBookFragment;
import com.example.library_project.view.ui.MainActivity;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {

    private Context context;
    private List<Book> mBookList;

    public ProfileAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Book> list){
        this.mBookList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_favorite_book_vertical, parent,false);
        return new ProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        Book book = mBookList.get(position);
        if (book == null){
            return;
        }
        holder.name.setText(book.getName());
        holder.des.setText(book.getDescription());
        holder.heart.setVisibility(View.INVISIBLE);
        holder.itemLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                FragmentManager fm = ((MainActivity) context).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                // add bundle arguments
                bundle.putInt(Common.BOOK_ID, book.getId());

                DetailBookFragment detailBookFragment = new DetailBookFragment();
                detailBookFragment.setArguments(bundle);

                ft.replace(R.id.frame_main, detailBookFragment, Common.FRAG_BDETAIL);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mBookList != null){
            return mBookList.size();
        }
        return 0;
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout itemLay;
        private TextView name, des;
        private ImageView heart;

        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_book);
            des = itemView.findViewById(R.id.des_book);
            heart = itemView.findViewById(R.id.heart);
            itemLay = itemView.findViewById(R.id.item_book);
        }
    }
}
