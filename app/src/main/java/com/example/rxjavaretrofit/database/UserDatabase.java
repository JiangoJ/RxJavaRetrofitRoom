package com.example.rxjavaretrofit.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.rxjavaretrofit.models.User;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {
    private static UserDatabase instance;

    public abstract UserDao userDao();

    public static synchronized UserDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    UserDatabase.class, "user_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    //Callback to create database
    private static final Callback roomCallBack = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //new PopulateDbAsyncTask(instance).execute();
            final UserDao userDao = instance.userDao();

            //Some initial data that will populate the Database
            final User user1 = new User(1, "Example", "example.com");
            final User user2 = new User(2, "Example3", "example2.com");
            final List<User> userList = new ArrayList<User>();
            userList.add(user1);
            userList.add(user2);

            Observable<User> userObservable = Observable.fromIterable(userList)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io());

            userObservable.subscribe(new Observer<User>() {
                @Override
                public void onSubscribe(Disposable d) {}

                @Override
                public void onNext(User user) {
                    userDao.insertAll(userList);
                }

                @Override
                public void onError(Throwable e) {}

                @Override
                public void onComplete() {}
            });


        }


    };



}
