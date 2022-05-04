package ro.alexmamo.firebasesigninwithgoogle.core

import android.util.Log
import ro.alexmamo.firebasesigninwithgoogle.core.Constants.TAG

class Utils {
    companion object {
        fun print(e: Exception?) {
            Log.d(TAG, e?.message ?: e.toString())
        }
    }
}