package com.example.my_ecomerge_app.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val imgFood: String,
    val nameFood: String,
    val price: Double,
    val describe: String,
    val date: String,
    val type: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readDouble(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    constructor(imgFood: String, nameFood: String, price: Double, describe: String, date: String, type: String): this(0, imgFood = imgFood, nameFood = nameFood, price = price, describe = describe, date = date, type = type)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(imgFood)
        parcel.writeString(nameFood)
        parcel.writeDouble(price)
        parcel.writeString(describe)
        parcel.writeString(date)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Order> {
        override fun createFromParcel(parcel: Parcel): Order {
            return Order(parcel)
        }

        override fun newArray(size: Int): Array<Order?> {
            return arrayOfNulls(size)
        }
    }
}
