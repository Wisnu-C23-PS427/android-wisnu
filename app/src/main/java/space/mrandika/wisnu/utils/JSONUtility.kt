package space.mrandika.wisnu.utils

import android.content.Context

fun getJsonAsString(context: Context, resId: Int): String {
    val inputStream = context.resources.openRawResource(resId)
    return readTextFile(inputStream)
}