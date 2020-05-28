package com.example.rxjavaretrofit.models;

import android.app.Application;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.rxjavaretrofit.database.UserRepository;

import java.util.List;


public class UserViewModel extends AndroidViewModel {
    private UserRepository repository;
    private LiveData<List<User>> allUsers;

    public UserViewModel(@NonNull Application application){
        super(application);
        repository = new UserRepository(application);
        allUsers = repository.getAllUsers();
    }

    public void insertAll(List<User> users) {
        repository.insertAll(users);
    }

    public void update(User user){
        repository.update(user);
    }

    public void delete(User user){
        repository.delete(user);
    }

    public void delteAllUsers(){repository.deleteAllUsers();}

    public LiveData<List<User>> getAllUsers(){
        return allUsers;
    }



}
