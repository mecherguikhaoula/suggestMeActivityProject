package com.example.boredApplication.Data.Models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "activitiesTable", indices = [Index(value = ["activity"], unique = true)])
@Parcelize
data class ActivityEntity(
    @SerializedName("activity")
    @PrimaryKey
    @ColumnInfo(name = "activity")
    val activity: String = "",
    @SerializedName("type")
    @ColumnInfo(name = "type")
    val type: String?,
    @SerializedName("participants")
    @ColumnInfo(name = "participants")
    val participants: Int?,
    @SerializedName("price")
    @ColumnInfo(name = "price")
    val price: Double?,
    @SerializedName("link")
    @ColumnInfo(name = "link")
    val link: String?,
    @SerializedName("key")
    @ColumnInfo(name = "key")
    val key: String?,
    @SerializedName("accessibility")
    @ColumnInfo(name = "accessibility")
    val accessibility: Double?
): Parcelable
