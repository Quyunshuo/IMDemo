package com.quyunshuo.imdemomember

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    @SuppressLint("SimpleDateFormat")
    fun getTime(): String {
        return if (android.os.Build.VERSION.SDK_INT >= 24) {
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
        } else {
            val tms = Calendar.getInstance()
            tms.get(Calendar.YEAR).toString() + "-" + tms.get(Calendar.MONTH)
                .toString() + "-" + tms.get(Calendar.DAY_OF_MONTH).toString() + " " + tms.get(
                Calendar.HOUR_OF_DAY
            ).toString() + ":" + tms.get(Calendar.MINUTE)
                .toString() + ":" + tms.get(Calendar.SECOND)
                .toString() + "." + tms.get(Calendar.MILLISECOND).toString()
        }
    }
}