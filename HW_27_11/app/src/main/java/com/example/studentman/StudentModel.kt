package com.example.studentman

import android.os.Parcel
import android.os.Parcelable

data class StudentModel(var studentName: String, var studentId: String) : Parcelable {

    // Constructor từ Parcel
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",  // Nếu là null, trả về giá trị mặc định ""
        parcel.readString() ?: ""   // Nếu là null, trả về giá trị mặc định ""
    )

    // Viết đối tượng vào Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(studentName)
        parcel.writeString(studentId)
    }

    // Trả về mã số đặc trưng của đối tượng Parcelable
    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<StudentModel> {
        // Tạo đối tượng từ Parcel
        override fun createFromParcel(parcel: Parcel): StudentModel {
            return StudentModel(parcel)
        }

        // Tạo mảng của StudentModel
        override fun newArray(size: Int): Array<StudentModel?> = arrayOfNulls(size)
    }
}
