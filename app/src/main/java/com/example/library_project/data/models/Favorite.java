package com.example.library_project.data.models;

import java.io.Serializable;

public class Favorite implements Serializable {
    private int id;
    private int bookId;

    public Favorite(int id, int bookId) {
        this.id = id;
        this.bookId = bookId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "id=" + id +
                ", bookId=" + bookId +
                '}';
    }
}
