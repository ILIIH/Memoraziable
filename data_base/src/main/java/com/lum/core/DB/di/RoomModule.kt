package com.lum.core.DB.di

import androidx.room.Room
import com.lum.core.DB.GamesDatabase
import com.lum.core.DB.ThemeDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val RoomModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            ThemeDatabase::class.java,
            "theme-database",
        )
            .fallbackToDestructiveMigration()
            .build()
            .themeDao()
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            ThemeDatabase::class.java,
            "theme-database",
        )
            .fallbackToDestructiveMigration()
            .build()
            .cardsDAO()
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            ThemeDatabase::class.java,
            "theme-database",
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            GamesDatabase::class.java,
            "games-database-database",
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}
