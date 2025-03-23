package com.fauzan.restoapp.Room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "person_table")
data class Person(
    @PrimaryKey(autoGenerate = true) val pId : Int,
    @ColumnInfo("person_name") val name : String,
    @ColumnInfo("person_review") val review : String
) : Parcelable
