package com.example.check_in_out;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface VisitorsDao {

    @Insert
    void insert(Visitors visitors);

    @Update
    void update(Visitors visitors);

    @Delete
    void delete(Visitors visitors);

    @Query("DELETE FROM Visitors_table")
    void deleteAll();

    @Query("SELECT * FROM Visitors_table ORDER BY timeIn AND dateIn DESC")
    LiveData<List<Visitors>> selectAll();

    @Query("SELECT * FROM Visitors_table WHERE status LIKE :statusParams ORDER BY timeIn AND dateIn DESC")
    LiveData<List<Visitors>> selectAllStatus(boolean statusParams);

    @Query("SELECT * FROM Visitors_table WHERE flagIn LIKE :flagInParams OR flagOut LIKE :flagOutParams ORDER BY timeIn AND dateIn DESC")
    LiveData<List<Visitors>> selectAllFlag(boolean flagInParams, boolean flagOutParams );
}
