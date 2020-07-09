package org.jtom.ader_mobile.database;

import androidx.room.*
import org.jtom.ader_mobile.datamodel.OfferDto

@Dao
interface OfferDao {

    @Query("SELECT * FROM offers LIMIT :limit OFFSET :offset")
    suspend fun get(offset: Int, limit: Int): List<OfferDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(offers: List<OfferDto>)

    @Delete
    fun delete(offer: OfferDto)

    @Query("DELETE FROM offers")
    fun deleteAll()
}
