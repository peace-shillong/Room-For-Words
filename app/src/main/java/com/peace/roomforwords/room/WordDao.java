package com.peace.roomforwords.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Our Data Access Object Interface to interact with the Database Table Word
 */
@Dao
public interface  WordDao {
    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    /**  Declares a method to insert one word:     *
     * @param word
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Word word);

    @Query("Select * from word where id=:arg0")
    LiveData<Word> selectOne(int arg0);

    @Update
    void update(Word word);

    @Query("DELETE FROM word")
    void deleteAll();

    @Query("SELECT * from word ORDER BY word ASC")
    LiveData<List<Word>> getAlphabetizedWords();
}
