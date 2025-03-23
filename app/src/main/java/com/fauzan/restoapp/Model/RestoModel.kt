package com.fauzan.restoapp.Model

import android.os.Parcel
import android.os.Parcelable

data class RestoModel (
    val restaurant:String="",
    val picUrl:String="",
    val time:String="",
    val type:String="",
    val location:String="",
    val price:String="",
    val category:String="",
    val description:String="",
    val rating:String="",
    val menu:String=""

):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(restaurant)
        parcel.writeString(picUrl)
        parcel.writeString(time)
        parcel.writeString(type)
        parcel.writeString(location)
        parcel.writeString(price)
        parcel.writeString(category)
        parcel.writeString(description)
        parcel.writeString(rating)
        parcel.writeString(menu)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RestoModel> {
        override fun createFromParcel(parcel: Parcel): RestoModel {
            return RestoModel(parcel)
        }

        override fun newArray(size: Int): Array<RestoModel?> {
            return arrayOfNulls(size)
        }
    }
}