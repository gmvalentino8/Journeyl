package com.valentino.journeyl.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Valentino on 4/4/18.
 */

@Parcelize
data class Milestone(var mid: String? = null, var description: String = "", var time: Long = 0,
                     var completed: Boolean = false, var rating1: Int? = null,
                     var rating2: Int? = null, var rating3: Int? = null,
                     var rating4: Int? = null, var reflection: String? = null) : Parcelable
