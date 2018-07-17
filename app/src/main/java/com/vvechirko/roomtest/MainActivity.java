package com.vvechirko.roomtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.vvechirko.roomtest.repository.Repository;
import com.vvechirko.roomtest.room.entity.UserEntity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Repository().of(UserEntity.class)
                .query()
                .where("id", "23")
                .findAll()
                .subscribe();
    }
}
