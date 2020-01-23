package com.peace.roomforwords.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {

    //You make database provides its DAOs by creating an abstract "getter" method for each @Dao.
    public abstract WordDao wordDao();

    private static volatile WordRoomDatabase INSTANCE;
    //We've created an ExecutorService with a fixed thread pool that you will use
    // to run database operations asynchronously on a background thread.
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //getDatabase returns the singleton.
    // It'll create the database the first time it's accessed, using Room's database builder to create a RoomDatabase object
    // in the application context from the WordRoomDatabase class and names it "word_list"
    static WordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WordRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class, "word_list")
                            //.addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                WordDao dao = INSTANCE.wordDao();
                dao.deleteAll();

                Word word = new Word("Hello");
                dao.insert(word);
                word = new Word("World");
                dao.insert(word);
            });
        }
    };
}