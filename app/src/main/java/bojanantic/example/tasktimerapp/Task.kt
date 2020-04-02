package bojanantic.example.tasktimerapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Bojan Antić on 02/04/2020
 **/

@Parcelize
class Task(val name: String, val description: String, val sortOrder: Int) : Parcelable {

    var id: Long = 0
}