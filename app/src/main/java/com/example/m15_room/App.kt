package com.example.m15_room

import android.app.Application
import androidx.room.Room

class App : Application() {

    lateinit var db: AppDatabase
    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            name = "db"
        )
            .addMigrations(MIGRATION_2_3)
            .build()

    }
}