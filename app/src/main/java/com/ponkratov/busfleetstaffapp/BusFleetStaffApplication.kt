package com.ponkratov.busfleetstaffapp

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.ponkratov.busfleetstaffapp.database.AppDatabase

class BusFleetStaffApplication : Application() {
    private var _appDatabase: AppDatabase? = null
    val appDatabase get() = requireNotNull(_appDatabase)

    override fun onCreate() {
        super.onCreate()
        _appDatabase = Room
            .databaseBuilder(
                this,
                AppDatabase::class.java,
                "database-room"
            )
            .allowMainThreadQueries()
            .build()
    }
}

val Context.appDatabase: AppDatabase
    get() = when (this) {
        is BusFleetStaffApplication -> appDatabase
        else -> applicationContext.appDatabase
    }
