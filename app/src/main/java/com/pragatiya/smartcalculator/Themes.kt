package com.pragatiya.smartcalculator

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.google.android.material.color.DynamicColors
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.text.get

// app/src/main/java/com/pragatiya/smartcalculator/Themes.kt

class Themes(private val context: Context) {

    companion object {
        // Only keep Light and Amoled
        private const val DEFAULT_THEME_INDEX = 0
        private const val AMOLED_THEME_INDEX = 1

        private val themeMap = mapOf(
            DEFAULT_THEME_INDEX to R.style.AppTheme,
            AMOLED_THEME_INDEX to R.style.AmoledTheme
        )

        // Only two styles now
        private const val LIGHT_STYLE_INDEX = 0
        private const val AMOLED_STYLE_INDEX = 1

        fun openDialogThemeSelector(context: Context) {
            val preferences = MyPreferences(context)
            val builder = MaterialAlertDialogBuilder(context)
            builder.background = ContextCompat.getDrawable(context, R.drawable.rounded)

            val styles = hashMapOf(
                LIGHT_STYLE_INDEX to context.getString(R.string.theme_light),
                AMOLED_STYLE_INDEX to context.getString(R.string.theme_amoled)
            )

            val checkedItem = when (preferences.theme) {
                AMOLED_THEME_INDEX -> AMOLED_STYLE_INDEX
                else -> LIGHT_STYLE_INDEX
            }

            builder.setSingleChoiceItems(styles.values.toTypedArray(), checkedItem) { dialog, which ->
                when (which) {
                    LIGHT_STYLE_INDEX -> {
                        preferences.theme = DEFAULT_THEME_INDEX
                        preferences.forceDayNight = AppCompatDelegate.MODE_NIGHT_NO
                    }
                    AMOLED_STYLE_INDEX -> {
                        preferences.theme = AMOLED_THEME_INDEX
                        preferences.forceDayNight = AppCompatDelegate.MODE_NIGHT_YES
                    }
                }
                dialog.dismiss()
                reloadActivity(context)
            }
            val dialog = builder.create()
            dialog.show()
        }

        private fun reloadActivity(context: Context) {
            (context as Activity).finish()
            startActivity(context, context.intent, null)
        }
    }

    fun applyDayNightOverride() {
        val preferences = MyPreferences(context)
        // Only allow explicit Light or Night mode
        when (preferences.theme) {
            AMOLED_THEME_INDEX -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
    fun getTheme(): Int {
        val preferences = MyPreferences(context)
        val theme = when (preferences.theme) {
            AMOLED_THEME_INDEX -> AMOLED_THEME_INDEX
            else -> DEFAULT_THEME_INDEX
        }
        return themeMap[theme] ?: R.style.AppTheme
    }

    fun getThemeNameFromId(themeID: Int) : String {
        return when (themeID) {
            DEFAULT_THEME_INDEX -> context.getString(R.string.theme_light)
            AMOLED_THEME_INDEX -> context.getString(R.string.theme_amoled)
            else -> "THEME"
        }
    }
}

//class Themes(private val context: Context) {
//
//    companion object {
//
//        // Themes
//        private const val DEFAULT_THEME_INDEX = 0
//        private const val AMOLED_THEME_INDEX = 1
//        private const val MATERIAL_YOU_THEME_INDEX = 2
//
//        // used to go from Preference int value to actual theme
//        private val themeMap = mapOf(
//            DEFAULT_THEME_INDEX to R.style.AppTheme,
//            AMOLED_THEME_INDEX to R.style.AmoledTheme,
//            MATERIAL_YOU_THEME_INDEX to R.style.MaterialYouTheme
//        )
//
//        // Styles - Combinations of theme + day/night mode
//        private const val SYSTEM_STYLE_INDEX = 0
//        private const val LIGHT_STYLE_INDEX = 1
//        private const val DARK_STYLE_INDEX = 2
//        private const val AMOLED_STYLE_INDEX = 3
//
//        fun openDialogThemeSelector(context: Context) {
//
//            val preferences = MyPreferences(context)
//
//            val builder = MaterialAlertDialogBuilder(context)
//            builder.background = ContextCompat.getDrawable(context, R.drawable.rounded)
//
//            val systemName =
//                if (DynamicColors.isDynamicColorAvailable())
//                    "${context.getString(R.string.theme_system)} (${context.getString(R.string.theme_material_you)})"
//                else
//                    context.getString(R.string.theme_system)
//
//            val styles = hashMapOf(
//                SYSTEM_STYLE_INDEX to systemName,
//                LIGHT_STYLE_INDEX to context.getString(R.string.theme_light),
//                DARK_STYLE_INDEX to context.getString(R.string.theme_dark),
//                AMOLED_STYLE_INDEX to context.getString(R.string.theme_amoled)
//            )
//
//            val checkedItem = when (preferences.theme) {
//                AMOLED_THEME_INDEX -> AMOLED_STYLE_INDEX
//                MATERIAL_YOU_THEME_INDEX -> SYSTEM_STYLE_INDEX
//                else -> {
//                    when (preferences.forceDayNight) {
//                        AppCompatDelegate.MODE_NIGHT_NO -> LIGHT_STYLE_INDEX
//                        AppCompatDelegate.MODE_NIGHT_YES -> DARK_STYLE_INDEX
//                        else -> SYSTEM_STYLE_INDEX
//                    }
//                }
//            }
//
//            builder.setSingleChoiceItems(styles.values.toTypedArray(), checkedItem) { dialog, which ->
//                when (which) {
//                    SYSTEM_STYLE_INDEX -> {
//                        // system style uses the Material You theme if supported
//                        preferences.theme = if (DynamicColors.isDynamicColorAvailable()) MATERIAL_YOU_THEME_INDEX else DEFAULT_THEME_INDEX
//                        preferences.forceDayNight = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
//                    }
//                    LIGHT_STYLE_INDEX -> {
//                        preferences.theme = DEFAULT_THEME_INDEX
//                        preferences.forceDayNight = AppCompatDelegate.MODE_NIGHT_NO
//                    }
//                    DARK_STYLE_INDEX -> {
//                        preferences.theme = DEFAULT_THEME_INDEX
//                        preferences.forceDayNight = AppCompatDelegate.MODE_NIGHT_YES
//                    }
//                    AMOLED_STYLE_INDEX -> {
//                        preferences.theme = AMOLED_THEME_INDEX
//                        preferences.forceDayNight = AppCompatDelegate.MODE_NIGHT_YES
//                    }
//                }
//                dialog.dismiss()
//                reloadActivity(context)
//            }
//            val dialog = builder.create()
//            dialog.show()
//        }
//
//        private fun reloadActivity(context: Context) {
//            (context as Activity).finish()
//            startActivity(context, context.intent, null)
//        }
//    }
//
//    fun applyDayNightOverride() {
//        val preferences = MyPreferences(context)
//        if (preferences.forceDayNight != AppCompatDelegate.MODE_NIGHT_UNSPECIFIED) {
//            AppCompatDelegate.setDefaultNightMode(preferences.forceDayNight)
//        }
//    }
//
//    fun getTheme(): Int {
//        var theme = MyPreferences(context).theme
//        if (theme == -1) {
//            theme = if (DynamicColors.isDynamicColorAvailable()) MATERIAL_YOU_THEME_INDEX else DEFAULT_THEME_INDEX
//            MyPreferences(context).theme = theme
//        }
//        return themeMap[theme] ?: DEFAULT_THEME_INDEX
//    }
//
//    fun getThemeNameFromId(themeID: Int) : String {
//        var theme = "THEME"
//        when (themeID) {
//            DEFAULT_THEME_INDEX -> {
//                theme = if (MyPreferences(this.context).forceDayNight == AppCompatDelegate.MODE_NIGHT_YES) {
//                    context.getString(R.string.theme_dark)
//                } else {
//                    context.getString(R.string.theme_light)
//                }
//            }
//            MATERIAL_YOU_THEME_INDEX -> {
//                theme = "${context.getString(R.string.theme_system)} (${context.getString(R.string.theme_material_you)})"
//            }
//            AMOLED_THEME_INDEX -> {
//                theme = context.getString(R.string.theme_amoled)
//            }
//        }
//        return theme
//    }
//}