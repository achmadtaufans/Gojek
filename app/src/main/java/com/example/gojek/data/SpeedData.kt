package com.example.gojek.data


import android.os.Parcel
import android.os.Parcelable

/**
 * This class is used to contains Wallet data
 *
 * @author    Taufan S  <taufansetiawan@onoff.insure>
 */
class SpeedData(
    val status: String = "",
    val min: Int = 0,
    val max: Int = 0,
    val random: Int = 0

) : Parcelable {
    /**
     * Create WalletData model object with parcel parameter
     */
    constructor(parcel: Parcel) : this(
        parcel.readString()!!
        , parcel.readInt()
        , parcel.readInt()
        , parcel.readInt()
    ) {
    }

    /**
     *To write to parcel. This is from Parcelable interface
     */
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(status)
        parcel.writeValue(min)
        parcel.writeValue(max)
        parcel.writeValue(random)
    }

    /**
     * A bitmask indicating the set of special object types marshaled by this Parcelable object instance.
     * Value is either 0 or CONTENTS_FILE_DESCRIPTOR. This is from Parcelable interface
     */
    override fun describeContents(): Int {
        return 0
    }

    /**
     * To create parcelable object. This is from Parcelable interface
     */
    companion object CREATOR : Parcelable.Creator<SpeedData> {
        override fun createFromParcel(parcel: Parcel): SpeedData {
            return SpeedData(parcel)
        }

        override fun newArray(size: Int): Array<SpeedData?> {
            return arrayOfNulls(size)
        }
    }
}
