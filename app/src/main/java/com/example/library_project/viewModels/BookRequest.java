package com.example.library_project.viewModels;

import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.Serializable;

public class BookRequest implements Serializable {
    @SerializedName("name")
    private String title;
    private String description;
    @SerializedName("data")
    private File pdfFile;
    @SerializedName("category_id")
    private Integer category;

    public BookRequest() {
    }

    public BookRequest(String name, String description, File data, Integer category_id) {
        this.title = name;
        this.description = description;
        this.pdfFile = data;
        this.category = category_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public File getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(File pdfFile) {
        this.pdfFile = pdfFile;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }
}
