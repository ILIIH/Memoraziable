package com.example.add_theme_domain

import com.example.add_theme_data.Theme
import com.example.add_theme_data.ThemeRepo
import com.example.core.DB.ThemeDatabace

class ThemeRepository(private val themeDB: ThemeDatabace) : ThemeRepo {
    override suspend fun saveThemes(theme: Theme) {
        themeDB.themeDao().insertTheme()
    }
}