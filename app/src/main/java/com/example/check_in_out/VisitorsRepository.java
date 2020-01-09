package com.example.check_in_out;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class VisitorsRepository {

    private VisitorsDao visitorsDao;
    private LiveData<List<Visitors>> allVisitors;
    private LiveData<List<Visitors>> inVisitors;
    private LiveData<List<Visitors>> outVisitors;
    private LiveData<List<Visitors>> flagVisitors;
    private List<Visitors> inVisitorsJS;

    public  VisitorsRepository (Application application){
        VisitorsDb visitorsDb = VisitorsDb.getInstance(application);
        visitorsDao = visitorsDb.visitorsDao();
        allVisitors = visitorsDao.selectAll();
        inVisitors = visitorsDao.selectAllStatus(true);
        outVisitors = visitorsDao.selectAllStatus(false);
        flagVisitors = visitorsDao.selectAllFlag(true, true);
        inVisitorsJS = visitorsDao.selectAllStatus(true).getValue();
    }

   public void insert(Visitors visitors){
            new InsertAsyncTAsk(visitorsDao).execute(visitors);
    }
   public void update(Visitors visitors){
            new UpdateAsynctask(visitorsDao).execute(visitors);

    }
   public void delete(Visitors visitors){
            new DeleteAsyncTask(visitorsDao).execute(visitors);
    }
    public void deleteAll(){
            new DeleteAllAsyncTask(visitorsDao).execute();
    }
    public  LiveData<List<Visitors>> getAllVisitors(){
        return  allVisitors;
    }
    public  LiveData<List<Visitors>> getInVisitors(){
        return  inVisitors;
    }
    public  LiveData<List<Visitors>> getOutVisitors(){
        return  outVisitors;
    }

    public  LiveData<List<Visitors>> getFlagVisitors(){
        return  flagVisitors;
    }

    public  List<Visitors> getInVisitorsJS(){
        return  inVisitorsJS;
    }

    public static class InsertAsyncTAsk extends AsyncTask<Visitors,Void, Void>{
        VisitorsDao visitorsDao;

        public InsertAsyncTAsk(VisitorsDao visitorsDao) {
            this.visitorsDao = visitorsDao;
        }

        @Override
        protected Void doInBackground(Visitors... visitors) {
                visitorsDao.insert(visitors[0]);
                return null;
        }
    }

    public static class UpdateAsynctask extends  AsyncTask<Visitors, Void, Void>{
            VisitorsDao visitorsDao;

        public UpdateAsynctask(VisitorsDao visitorsDao) {
            this.visitorsDao = visitorsDao;
        }

        @Override
        protected Void doInBackground(Visitors... visitors) {
            visitorsDao.update(visitors[0]);
            return null;
        }
    }

    public static class DeleteAsyncTask extends AsyncTask<Visitors, Void, Void>{

        VisitorsDao visitorsDao;

        public DeleteAsyncTask(VisitorsDao visitorsDao) {
            this.visitorsDao = visitorsDao;
        }

        @Override
        protected Void doInBackground(Visitors... Visitors) {
            visitorsDao.delete(Visitors[0]);
            return null;
        }
    }
    public static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void>{

        VisitorsDao visitorsDao;

        public DeleteAllAsyncTask(VisitorsDao visitorsDao) {
            this.visitorsDao = visitorsDao;
        }

        @Override
        protected Void doInBackground(Void... Voids) {
            visitorsDao.deleteAll();
            return null;
        }
    }


}
