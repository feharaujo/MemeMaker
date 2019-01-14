package com.fearaujo.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonIgnoreProperties(ignoreUnknown = true)
data class ResultModel(
        @field:JsonProperty("code")
        val code: Int?,
        @field:JsonProperty("data")
        val data: List<MemeModel>?,
        @field:JsonProperty("message")
        val message: String?,
        @field:JsonProperty("next")
        val next: String?,
        @field:Transient
        val error: Boolean = false,
        @field:Transient
        val loading: Boolean = false
) : Parcelable

@Parcelize
@JsonIgnoreProperties(ignoreUnknown = true)
data class MemeModel(
        @field:JsonProperty("ID")
        val ID: Int?,
        @field:JsonProperty("bottomText")
        val bottomText: String?,
        @field:JsonProperty("image")
        val image: String?,
        @field:JsonProperty("name")
        val name: String = "",
        @field:JsonProperty("rank")
        val rank: Int?,
        @field:JsonProperty("tags")
        val tags: String?,
        @field:JsonProperty("topText")
        val topText: String?
) : Parcelable
