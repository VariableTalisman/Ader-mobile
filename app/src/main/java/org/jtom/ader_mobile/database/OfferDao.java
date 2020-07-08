package org.jtom.ader_mobile.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import org.jtom.ader_mobile.datamodel.OfferDto;

import java.util.List;

@Dao
public interface OfferDao {

    @Query("SELECT * FROM offers LIMIT :limit OFFSET :offset")
    LiveData<List<OfferDto>> get(int offset, int limit);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<OfferDto> offers);

    @Delete
    void delete(OfferDto offer);

    @Query("DELETE FROM offers")
    void deleteAll();
}
