package com.example.check_in_out;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database( entities = Visitors.class, version = 1)
public abstract class VisitorsDb extends RoomDatabase {

    private static VisitorsDb instance;
    public abstract VisitorsDao visitorsDao();
    public static synchronized VisitorsDb getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    VisitorsDb.class, "Visitor_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();

        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

        }
    };
public static class PopulateDefaultDataAsyncTask extends AsyncTask<Void, Void, Void>{

    VisitorsDao visitorsDao;
    public PopulateDefaultDataAsyncTask(VisitorsDb db){
        this.visitorsDao = db.visitorsDao();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        visitorsDao.insert(new Visitors("default","visitor",
                true,"000000", "0-0-0000","0-00-0000",
                "00:00","00:00","",false, false,
                "","",0000000000,""));
        return null;
    }
}
}
