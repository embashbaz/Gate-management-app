package com.example.check_in_out;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class VisitorsViewModel extends AndroidViewModel {

    private VisitorsRepository repository;
    private LiveData<List<Visitors>> allVisitors;
    public LiveData<List<Visitors>> inVisitors;
    private LiveData<List<Visitors>> outVisitors;
    private LiveData<List<Visitors>> flagVisitors;


    public VisitorsViewModel(@NonNull Application application) {
        super(application);

        repository = new VisitorsRepository(application);
        allVisitors = repository.getAllVisitors();
        inVisitors = repository.getInVisitors();
        outVisitors = repository.getOutVisitors();
        flagVisitors = repository.getFlagVisitors();
    }
    public void insert(Visitors visitors){
        repository.insert(visitors);
    }
    public void update(Visitors visitors){
       repository.update(visitors);

    }
    public void delete(Visitors visitors){
        repository.delete(visitors);
    }
    public void deleteAll(){
        repository.deleteAll();
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
    public  LiveData<List<Visitors>> getFlagVisitors(){return  flagVisitors;
    }

    public class data{


    }
}