package com.example.library_project.viewModels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BookResponse implements Serializable {
    private Integer id;
    @SerializedName("name")
    private String title;
    @SerializedName("cover_url")
    private String coverImg;
    private String description;
    @SerializedName("category_id")
    private Integer category;
    @SerializedName("data_url")
    private String pdfFileUrl;
    @SerializedName("user_id")
    private String owner;

    public BookResponse(Integer id, String title, String coverImg, String description, Integer category, String pdfFileUrl, String owner) {
        this.id = id;
        this.title = title;
        this.coverImg = coverImg;
        this.description = description;
        this.category = category;
        this.pdfFileUrl = pdfFileUrl;
        this.owner = owner;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getPdfFileUrl() {
        return pdfFileUrl;
    }

    public void setPdfFileUrl(String pdfFileUrl) {
        this.pdfFileUrl = pdfFileUrl;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
