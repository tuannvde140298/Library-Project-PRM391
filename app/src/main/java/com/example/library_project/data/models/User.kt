package com.example.library_project.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    var id: Int,
    @SerializedName("email")
    var email: String,
    @SerializedName("name")
    var username: String,
    @SerializedName("created_at")
    var createdDate: String,
    @SerializedName("updated_at")
    var modifiedDate: String,
    @SerializedName("authentication_token")
    var authenticationToken: String,
    var imageUrl: String
) : Serializable

data class Data(val user: User)