package com.example.rxjavaretrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.rxjavaretrofit.models.User;
import com.example.rxjavaretrofit.models.UserRecyclerAdapter;
import com.example.rxjavaretrofit.models.UserViewModel;
import com.example.rxjavaretrofit.networking.NetworkUtils;
import com.example.rxjavaretrofit.networking.UserAPI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jakewharton.rxbinding4.view.RxView;

import java.util.Arrays;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private UserViewModel userViewModel;
    private RecyclerView rv;
    final UserRecyclerAdapter adapter = new UserRecyclerAdapter();

    private FloatingActionButton fab;
    private FloatingActionButton fabInsert;

    private CompositeDisposable disposables;

    private Retrofit retrofit;
    private UserAPI userAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Disposables
        disposables = new CompositeDisposable();

        //Networking
        retrofit = NetworkUtils.getInstance();
        userAPI = retrofit.create(UserAPI.class);

        //Fab
        fab = findViewById(R.id.delete_fab);
        fabInsert = findViewById(R.id.insert_fab);

        //Recycler View
        rv = findViewById(R.id.recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        //ViewModel
        userViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).getInstance(this.getApplication()).create(UserViewModel.class);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userViewModel.delteAllUsers();
            }
        });

        //Button to insert 100 pieces of data
        fabInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callData();
            }
        });

        userViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                //Update RecyclerView
                adapter.setUsers(users);

            }
        });

    }

    // Make retrofit call and update recycler view
    private synchronized void callData() {
        Log.d("Change", "networking");

        Flowable<List<User>> userObservables = userAPI.getUsers()
                .toFlowable(BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
        disposables.add(userObservables.subscribe(new Consumer<List<User>>() {
            @Override
            public void accept(List<User> users) throws Exception {
                insertCalledData(users);
                }
            },
                throwable -> {
                    Log.e("Error!", throwable.getLocalizedMessage());
                })
        );


    }

    //Method to handle inserting data to viewModel
    private void insertCalledData(List<User> users){
        userViewModel.insertAll(users);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Clear observers
        disposables.clear();
    }
}
