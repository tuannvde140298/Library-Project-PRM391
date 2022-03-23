package com.example.library_project.data.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Book implements Serializable {
    private int id;
    private String name;
    private String cover_url;
    private String description;
    private int category_id;
    private String data_url;
    private String user_id;
    private Boolean isHeart = false;
    private String categoryName;

    private String author;
    @SerializedName("avg_rating")
    private float rating;

    public Book(int id, String name, String cover_url, String description, int category_id, String data_url, String user_id, Boolean isHeart) {
        this.id = id;
        this.name = name;
        this.cover_url = cover_url;
        this.description = description;
        this.category_id = category_id;
        this.data_url = data_url;
        this.user_id = user_id;
        this.isHeart = isHeart;
    }

    public Book() {
    }

    public Book(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover_url() {
        return cover_url;
    }

    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getData_url() {
        return data_url;
    }

    public void setData_url(String data_url) {
        this.data_url = data_url;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Boolean getHeart() {
        return isHeart;
    }

    public void setHeart(Boolean heart) {
        isHeart = heart;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cover_url='" + cover_url + '\'' +
                ", description='" + description + '\'' +
                ", category_id='" + category_id + '\'' +
                ", data_url='" + data_url + '\'' +
                ", user_id='" + user_id + '\'' +
                ", isHeart=" + isHeart +
                '}';
    }

}
