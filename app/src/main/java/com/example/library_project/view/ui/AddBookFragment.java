package com.example.library_project.view.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.library_project.R;
import com.example.library_project.data.local.MySharedPreferences;
import com.example.library_project.data.models.Category;
import com.example.library_project.data.models.LoginModel;
import com.example.library_project.viewModels.BookRequest;
import com.example.library_project.viewModels.BookResponse;
import com.obsez.android.lib.filechooser.ChooserDialog;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBookFragment extends Fragment {

    private Button btnPublish;
    private TextView txtBookTitle;
    private TextView txtAuthor;
    private Button btnUploadPdfFile;
    private TextView txtDescription;
    private int categoryId = 0;
    private File pdfFile;
    private File cover;
    private ImageView imgBook;
    private PowerSpinnerView categoryList;
    private List<String> list;
    private TextView pdfFileName;

    public AddBookFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_book, container,false);
        initUi(v);

        final MySharedPreferences mySharedPreferences = new MySharedPreferences(this.getContext());
        list = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mySharedPreferences.getCategories().forEach(category -> list.add(category.getName()));
        }
        categoryList.setItems(list);

        categoryList.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(int i, @Nullable String s, int i1, String t1) {
                mySharedPreferences.getCategories().forEach(cate->{
                        if (cate.getName().matches(t1)){
                            categoryId = cate.getId();
                            return;
                        };
                    });
            }
        });

        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBook();
            }
        });
        btnUploadPdfFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPdfFile();
            }
        });
        imgBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPicture();
            }
        });
        return v;
    }
    private void selectPicture(){
        ChooserDialog dialog = new ChooserDialog(getActivity())
                .withStartFile("/")
                .withFilter(false,false,"png","jpg")
                .withChosenListener(new ChooserDialog.Result() {
                    @Override
                    public void onChoosePath(String path, File pathFile) {
                        cover = pathFile;
                        imgBook.setImageURI(Uri.parse(path));
                        Log.i("Picture:", "FILE: " + path);
                    }
                })
                // to handle the back key pressed or clicked outside the dialog:
                .withOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        Log.d("CANCEL", "CANCEL");
                        dialog.cancel(); // MUST have
                    }
                });
        dialog.build().show();
    }

    private void getPdfFile(){
        ChooserDialog dialog = new ChooserDialog(getActivity())
                .withStartFile("/")
                .withFilter(false,false,"pdf")
                .withChosenListener(new ChooserDialog.Result() {
                    @Override
                    public void onChoosePath(String path, File pathFile) {
                        pdfFile = pathFile;
                        pdfFileName.setText("PDF file: " + pathFile.getName());
                    }
                })
                // to handle the back key pressed or clicked outside the dialog:
                .withOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        Log.d("CANCEL", "CANCEL");
                        dialog.cancel(); // MUST have
                    }
                });
        dialog.build().show();
    }

    private void addBook(){
        final MySharedPreferences mySharedPreferences = new MySharedPreferences(this.getContext());
        try {
            String title = txtBookTitle.getText().toString();
            String description = txtDescription.getText().toString();
            String author = txtAuthor.getText().toString();

            if (title.isEmpty() || categoryId==0){Toast.makeText(getActivity(), "Invalid request", Toast.LENGTH_LONG).show(); return;}
            if (!pdfFile.exists() || !cover.exists()) {Toast.makeText(getActivity(), "Please Add Pdf file & Book Cover", Toast.LENGTH_LONG).show(); return;}

            RequestBody titlePart = RequestBody.create(MediaType.parse("multipart/form-data"), title);
            RequestBody descriptionPart = RequestBody.create(MediaType.parse("multipart/form-data"), description);
            RequestBody authorPart = RequestBody.create(MediaType.parse("multipart/form-data"), author);
            RequestBody categoryPart = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(categoryId));
            RequestBody fileReq = RequestBody.create(
                    MediaType.parse("multipart/form-data"),
                    pdfFile
            );
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("book[data]", pdfFile.getName(), fileReq);
            RequestBody coverReq = RequestBody.create(
                    MediaType.parse("multipart/form-data"),
                    cover
            );
            MultipartBody.Part coverPart = MultipartBody.Part.createFormData("book[cover]", pdfFile.getName(), coverReq);

            Call<BookResponse> createBookCall = LoginSignUpActivity.apiInterface.createBook(mySharedPreferences.getTokenValue(), titlePart, descriptionPart, categoryPart, authorPart, filePart, coverPart);
            createBookCall.enqueue(new Callback<BookResponse>() {
                @Override
                public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                    Log.e("Response", "Success");
                    if(response.isSuccessful()){
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        earseAllForm();
                    }
                }

                @Override
                public void onFailure(Call<BookResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), "Invalid request", Toast.LENGTH_SHORT).show();
                    Log.e("Response", t.getMessage());
                }
            });
        } catch (Exception ex){
            Log.e("AddBook", ex.getMessage());
            Toast.makeText(getActivity(), "Invalid request", Toast.LENGTH_SHORT).show();
        }

    }

    private void earseAllForm(){
        txtBookTitle.setText("");
        txtAuthor.setText("");
        categoryId = 0;
        categoryList.clearSelectedItem();
        txtDescription.setText("");
        pdfFile = null;
        cover = null;
        pdfFileName.setText("");
        imgBook.setImageDrawable(getResources().getDrawable(R.drawable.img_camera));
    }

    private void initUi(View v) {
        btnPublish = v.findViewById(R.id.btn_publish);
        btnUploadPdfFile = v.findViewById(R.id.btn_uploadPdfFile);
        txtBookTitle = v.findViewById(R.id.txtTitle);
        txtAuthor = v.findViewById(R.id.txtAuthor);
        txtDescription = v.findViewById(R.id.txtBookDescription);
        imgBook = v.findViewById(R.id.book_img);
        categoryList = v.findViewById(R.id.category);
        pdfFileName = v.findViewById(R.id.fileName);
    }
}
