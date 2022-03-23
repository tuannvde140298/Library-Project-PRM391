package com.example.library_project.data.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginModel implements Serializable {
    @SerializedName("data")
    private Data data;
    @SerializedName("messages")
    private String messages;
    @SerializedName("is_success")
    private boolean is_success;

    public LoginModel(Data data, String messages, boolean is_success) {
        this.data = data;
        this.messages = messages;
        this.is_success = is_success;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public boolean isIs_success() {
        return is_success;
    }

    public void setIs_success(boolean is_success) {
        this.is_success = is_success;
    }
}
