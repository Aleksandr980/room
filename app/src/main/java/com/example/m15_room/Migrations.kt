package com.example.m15_room

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_2_3: Migration = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
     database.execSQL("ALTER TABLE dictionary ADD COLUMN id")
    }
}