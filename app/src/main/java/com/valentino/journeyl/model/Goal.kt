package com.valentino.journeyl.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Valentino on 4/4/18.
 */

@Parcelize
data class Goal(var gid: String? = null, val description: String = "", val time: Long = 0) : Parcelable