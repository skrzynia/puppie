package org.wit.puppie.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlacemarkModel(var title: String = "",
                            var description: String = "") : Parcelable {
}
