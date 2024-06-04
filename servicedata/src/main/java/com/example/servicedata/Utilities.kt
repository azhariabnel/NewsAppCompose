package com.example.servicedata

import android.content.Context
import java.text.ParseException
import java.text.SimpleDateFormat

object Utilities {

    const val API_KEY = "d7a0e7681dac4d9dbf6b63138c63c1d6"
    private const val errorFormating = "Error Formating"

    fun getApiKey(context: Context): String? {
        val name = "${context.packageName}.$API_KEY"
        val preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        return preferences.getString(API_KEY, null)
    }

    fun setDateFormatDayddMMyyyy(date_resp: String): String {
        try {

            val sDate = date_resp.substring(0, 10)
            val output = SimpleDateFormat("dd-MM-yyyy")
            val formatter = SimpleDateFormat("yyyy-MM-dd")

            var newFormat = ""

            try {
                val d = formatter.parse(sDate)
                newFormat = output.format(d)

            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return newFormat
        }catch (e: java.lang.Exception){
            return errorFormating
        }
    }
}