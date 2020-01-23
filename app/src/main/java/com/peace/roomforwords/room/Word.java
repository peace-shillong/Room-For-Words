package com.peace.roomforwords.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * This is the Word Entity
 * for word SQLite table
 * it has two columns
 * id and word
 */
@Entity(tableName = "word")
public class Word {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;

    public Word(@NonNull String word) {this.mWord = word;}

    public String getWord(){return this.mWord;}

    public int getId(){return this.id;}

    public void setId(int id) {
        this.id = id;
    }

    public void setmWord(@NonNull String mWord) {
        this.mWord = mWord;
    }

}
