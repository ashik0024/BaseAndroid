package com.example.appdemo

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.runner.RunWith
import androidx.room.Room
import androidx.room.testing.MigrationTestHelper

import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.platform.app.InstrumentationRegistry
import com.example.appdemo.roomDb.LocalDb


import org.junit.Test


private const val DB_NAME = "local_database"

@RunWith(AndroidJUnit4::class)
class MigrationTest {

    @get:Rule
    val helper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        LocalDb::class.java,
        listOf(LocalDb.Migration2To3()),
        FrameworkSQLiteOpenHelperFactory()
    )


    @Test
    fun testAllMigrations() {
        helper.createDatabase(DB_NAME, 1).apply { close() }

        Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            LocalDb::class.java,
            DB_NAME
        ).addMigrations(LocalDb.migration3to4).build().apply {
            openHelper.writableDatabase.close()
        }
    }
}