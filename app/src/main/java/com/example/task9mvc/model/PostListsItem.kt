package com.example.task9mvc.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class PostListsItem(
    val body: String? = null,
    val id: Int? = null,
    val title: String? = null,
    val userId: Int? = null
) : Parcelable
