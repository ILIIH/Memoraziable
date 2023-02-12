package com.example.about_theme_ui.di

import com.example.add_theme_ui.ThemeAddViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val addThemeModule = module {
    viewModel {
        ThemeAddViewModel(get(), get())
    }
}