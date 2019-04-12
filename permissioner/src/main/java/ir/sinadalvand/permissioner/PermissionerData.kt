package ir.sinadalvand.permissioner

import android.os.Parcel
import android.os.Parcelable
import java.util.*


data class PermissionerData(val permission: String, val title: String = "", val description: String = "", val res_img: Int = 0) : Parcelable {
    val requestCode = Random().nextInt(1000) + 1000

    constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readInt()) {

        parcel.readInt()

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(permission)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeInt(res_img)
        parcel.writeInt(requestCode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PermissionerData> {
        override fun createFromParcel(parcel: Parcel): PermissionerData {
            return PermissionerData(parcel)
        }

        override fun newArray(size: Int): Array<PermissionerData?> {
            return arrayOfNulls(size)
        }
    }
}