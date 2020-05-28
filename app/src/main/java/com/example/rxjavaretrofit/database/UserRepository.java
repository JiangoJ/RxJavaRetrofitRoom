package com.example.rxjavaretrofit.database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.rxjavaretrofit.models.User;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UserRepository {

    private UserDao userDao;
    private LiveData<List<User>> allUsers;


    public UserRepository(Application application) {
        UserDatabase database = UserDatabase.getInstance(application);
        userDao = database.userDao();
        allUsers = userDao.getAllUsers();
    }

    public void insertAll(List<User> users) {

        Observable.just(users)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) throws Exception {
                        userDao.insertAll(users);
                    }
                },  throwable -> {
                    Log.e("Error!", throwable.getLocalizedMessage());
                });
    }

    public void update(User user) {
        Observable.just(user)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        Log.d("ChangeObs", "UpdateObs");
                        userDao.update(user);
                    }
                },
                        throwable -> {
                            Log.e("Error!", throwable.getLocalizedMessage());
                        });

    }

    public void delete(User user) {

        Observable.just(user)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        userDao.delete(user);
                    }
                },
                        throwable -> {
                            Log.e("Error!", throwable.getLocalizedMessage());
                        });

    }

    public void deleteAllUsers() {

        Observable<Void> deleteAllObservable = Observable.create(new ObservableOnSubscribe<Void>() {
            @Override
            public void subscribe(ObservableEmitter<Void> emitter) throws Exception {
                userDao.deleteAll();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
        deleteAllObservable.subscribe(new Consumer<Void>() {
            @Override
            public void accept(Void aVoid) throws Exception {
                userDao.deleteAll();
            }
        });
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }







}
