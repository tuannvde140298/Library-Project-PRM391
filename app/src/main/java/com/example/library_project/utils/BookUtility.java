package com.example.library_project.utils;

import com.example.library_project.data.models.Book;

import java.util.ArrayList;
import java.util.List;

public class BookUtility {
    public static List<Book> getBooksByCate(int catId, List<Book> bookList){
        List<Book> booksByCate = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getCategory_id() == catId){
                booksByCate.add(book);
            }
        }
        return booksByCate;
    }
}
