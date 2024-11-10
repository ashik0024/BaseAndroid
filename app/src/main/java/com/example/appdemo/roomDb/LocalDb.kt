package com.example.appdemo.roomDb

import android.content.Context
import android.provider.ContactsContract.Contacts
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.firebase.firestore.auth.User


@Database(
    entities = [UserInfo::class, PlayerInfo::class],
    version = 4,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3, spec = LocalDb.Migration2To3::class)
    ]
)
abstract class LocalDb : RoomDatabase() {

    abstract val dao: UserInfoDao

    // Renaming the column from `timeStamp` to `createdTime`(AutoMigration)
    @RenameColumn(tableName = "UserInfo", fromColumnName = "timeStamp", toColumnName = "createdTime")
    class Migration2To3 : AutoMigrationSpec

    // Manual migration from version 3 to version 4
    companion object {
        private val migration3to4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Create the PlayerInfo table with nullable playerName column
                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS PlayerInfo (" +
                            "playerId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                            "playerName TEXT)"
                )
            }
        }

        @Volatile
        private var INSTANCE: LocalDb? = null

        fun getDatabase(context: Context): LocalDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDb::class.java,
                    "local_database"
                )
                    .addMigrations(migration3to4)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
