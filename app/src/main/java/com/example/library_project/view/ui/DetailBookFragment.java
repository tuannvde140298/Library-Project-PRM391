package com.example.library_project.view.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.library_project.R;
import com.example.library_project.data.constants.Common;
import com.example.library_project.data.local.MySharedPreferences;
import com.example.library_project.data.models.Book;
import com.example.library_project.view.ui.interfaces.ShowBackButton;
import com.example.library_project.view.ui.interfaces.ToolbarTitle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailBookFragment extends Fragment {
    int book_id = 0;
    ToolbarTitle toolbarTitleCallback;
    ShowBackButton showBackButtonCallback;

    private TextView tvName, tvDescription;
    private ImageView imgBook;
    private Button btnReadingBook;
    private Book book;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        toolbarTitleCallback = (ToolbarTitle) context;
        showBackButtonCallback = (ShowBackButton) context;
    }

    public DetailBookFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book_details, container,false);

        Bundle args = getArguments();
        assert args != null;
        book_id = args.getInt(Common.BOOK_ID);

        if (book_id > 0) {
            // Show Back Button and Set Title
            showBackButtonCallback.showBackButton();
        }
        initUIDetail(v);
        final MySharedPreferences mySharedPreferences = new MySharedPreferences(this.getContext());

        Call<Book> listCall = MainActivity.apiInterface.getDetailBook(mySharedPreferences.getTokenValue(), book_id);
        listCall.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                book = response.body();
                tvName.setText(book.getName());
                tvDescription.setText(book.getDescription());
                if (book.getCover_url().equals("")){
                    imgBook.setImageResource(R.drawable.img_book);
                } else {
                    Glide.with(getContext()).load(book.getCover_url()).into(imgBook);
                }
                toolbarTitleCallback.setToolbarTitle(book.getName());
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) { }
        });

        btnReadingBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pdfLink = book.getData_url();
                if (!pdfLink.equals("")){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(pdfLink), "application/pdf");
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Book not yet updated ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }

    private void initUIDetail(View v) {
        tvName = v.findViewById(R.id.book_name_detail);
        tvDescription = v.findViewById(R.id.book_des_detail);
        imgBook = v.findViewById(R.id.img_book_detail);
        btnReadingBook = v.findViewById(R.id.reading_book);
    }
}
