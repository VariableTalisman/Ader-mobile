package org.jtom.ader_mobile.database;

import androidx.room.TypeConverter;

import org.jtom.ader_mobile.datamodel.Status;

class StatusConverter {

    @TypeConverter
    fun fromStatusToString(status: Status): String {
        return status.status
    }

    @TypeConverter
    fun fromStringToStatus(status: String): Status {
        return Status.valueOf(status)
    }
}
