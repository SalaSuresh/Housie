package com.example.myapplication.utils

import android.content.Context
import com.example.myapplication.constants.PreferenceConstants

class SharedPreferenceUtils {
    companion object {

        fun getIsMuted(context: Context): Boolean {
            return SharedPreferenceManager(context).getBoolean(
                PreferenceConstants.KEY_IS_MUTED,
                false
            )
        }

        fun setIsMuted(context: Context, isMuted: Boolean) {
            SharedPreferenceManager(context).putBoolean(PreferenceConstants.KEY_IS_MUTED, isMuted)
        }
    }
}