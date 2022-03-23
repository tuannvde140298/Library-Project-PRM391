package com.example.library_project.data.remote;

import com.example.library_project.data.models.Book;
import com.example.library_project.data.models.Category;
import com.example.library_project.data.models.LoginModel;
import com.example.library_project.data.models.User;

import java.util.List;
import com.example.library_project.viewModels.BookRequest;
import com.example.library_project.viewModels.BookResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    //https://library-management-prm391.herokuapp.com/api/log_in?user[email]=hoangkimduclqd@gmail.com&user[password]=123456
    @POST("api/log_in")
    Call<LoginModel> doLogin(@Query("user[email]") String email, @Query("user[password]") String password);

    //https://library/management-prm391.herokuapp.com/api/sign_up?user[email]=hoangkimduclqd@gmail.com&user[name]=Duc&user[password]=123456&user[password_confirmation]=123456
    @POST("api/sign_up")
    Call<LoginModel> doRegister(@Query("user[email]") String email, @Query("user[password]") String password, @Query("user[password_confirmation]") String passwordConfirmation);
    
    //https://library-management-prm391.herokuapp.com/api/categories
    @GET("api/categories")
    Call<List<Category>> getCategories(@Header("Authorization") String authToken);

    //https://library-management-prm391.herokuapp.com/api/books
    @GET("api/books")
    Call<List<Book>> getListBooks(@Header("Authorization") String authToken, @Query("q[category_id_eq]") int categoryId);

    @GET("api/books")
    Call<List<Book>> getListBooks(@Header("Authorization") String authToken, @Query("q[name_or_description_cont]") String searchKey);

    //https://library-management-prm391.herokuapp.com/api/books
    @GET("api/books")
    Call<List<Book>> getListBookFavorite(@Header("Authorization") String authToken);

    //https://library-management-prm391.herokuapp.com/api/10
    @GET("api/books/{id}")
    Call<Book> getDetailBook(@Header("Authorization") String authToken, @Path("id") int id);

    //https://library-management-prm391.herokuapp.com/api/books
    @GET("api/books")
    Call<List<Book>> getListBooks(@Header("Authorization") String authToken);

    //https://library-management-prm391.herokuapp.com/api/books
    @Multipart
    @POST("api/books")
    Call<BookResponse> createBook(@Header("Authorization") String authToken, @Part("book[name]") RequestBody title, @Part("book[description]") RequestBody description, @Part("book[category_id]") RequestBody category_id, @Part("book[author]") RequestBody author, @Part MultipartBody.Part data, @Part MultipartBody.Part cover);

    //https://library-management-prm391.herokuapp.com/api/users/1
    @GET("api/users/{id}")
    Call<User> getUserInfo(@Header("Authorization") String authToken, @Path("id") int id);

    //https://library-management-prm391.herokuapp.com/api/books?q[user_id_eq]=1
    @GET("api/books")
    Call<List<Book>> getListBooksByUserId(@Header("Authorization") String authToken, @Query("q[user_id_eq]") int userId);

}
