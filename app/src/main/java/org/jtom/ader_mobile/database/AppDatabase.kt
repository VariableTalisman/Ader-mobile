package org.jtom.ader_mobile.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.jtom.ader_mobile.datamodel.OfferDto

@Database(entities = [OfferDto::class], version = 1)
@TypeConverters(StatusConverter::class, OfferStatusConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): OfferDao
}
