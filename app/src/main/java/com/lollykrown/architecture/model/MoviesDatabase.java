package com.lollykrown.architecture.model;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Movies.class}, version = 1, exportSchema = false)
public abstract class MoviesDatabase extends RoomDatabase {

    public abstract MoviesDao moviesDao();

    public static final String DATABASE_NAME = "moviesDb";

    //SINGLETON
    private static volatile MoviesDatabase sINSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static MoviesDatabase getDatabase(final Context context) {
        if (sINSTANCE == null) {
            synchronized (MoviesDatabase.class) {
                if (sINSTANCE == null) {
                    sINSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MoviesDatabase.class, MoviesDatabase.DATABASE_NAME)
                            // .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return sINSTANCE;
    }

//    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
//        @Override
//        public void onOpen(@NonNull SupportSQLiteDatabase db) {
//            super.onOpen(db);
//
//            // If you want to keep data through app restarts,
//            // comment out the following block
//            databaseWriteExecutor.execute(new Runnable() {
//                @Override
//                public void run() {
//                    // Populate the database in the background.
//                    // If you want to start with more words, just add them.
//                    MoviesDao dao = sINSTANCE.moviesDao();
//                    dao.deleteAllMovies();
//
//                    Movies m = new Movies(21324, "Aladdin", "a nice movie", "12/12/2019", 3.7, "fvgsbnbdfg.jpg");
//                    dao.addMovies(m);
//                    m = new Movies(32344, "Joker", "a nice movie", "12/12/2019", 4.2, "ffffytyyrdtdg.jpg");
//                    dao.addMovies(m);
//                    m = new Movies(89535, "Joker", "a nice movie", "12/12/2019", 4.2, "ffffytyyrdtdg.jpg");
//                    dao.addMovies(m);
//                }
//            });
//        }
//    };
}