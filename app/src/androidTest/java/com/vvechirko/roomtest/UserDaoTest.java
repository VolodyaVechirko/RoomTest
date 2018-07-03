package com.vvechirko.roomtest;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.vvechirko.roomtest.room.entity.AddressEntity;
import com.vvechirko.roomtest.room.AppDatabase;
import com.vvechirko.roomtest.room.entity.PetEntity;
import com.vvechirko.roomtest.room.UserDao;
import com.vvechirko.roomtest.room.entity.UserEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class UserDaoTest {

    private AppDatabase mDatabase;
    private UserDao userDao;

    @Before
    public void initDb() {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase.class)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build();

        userDao = mDatabase.userDao();
    }

    @After
    public void closeDb() {
        mDatabase.close();
    }

    @Test
    public void getUsersWhenNoInserted() {
        List<UserEntity> users = userDao.getUsers();

        assertTrue(users.isEmpty());
    }

    public UserEntity getUser() {
        UserEntity user = new UserEntity(15, "John");
        AddressEntity address = new AddressEntity(20, 15, "London");
        PetEntity pet1 = new PetEntity(2, 15, "Rex");
        PetEntity pet2 = new PetEntity(4, 15, "Bax");
        user.setAddress(address);
        user.setPets(Arrays.asList(pet1, pet2));
        return user;
    }

    @Test
    public void insertAndSelectUsers() {
        userDao.insertUsers(Arrays.asList(getUser()));

        List<UserEntity> users = userDao.getUsers();

        assertEquals(1, users.size());
    }

    @Test
    public void insertAndDeleteUsers() {
        UserEntity user = getUser();
        userDao.insertUsers(Arrays.asList(user));

        userDao.deleteUsers(Arrays.asList(user));

        List<UserEntity> users = userDao.getUsers();

        assertTrue(users.isEmpty());
        assertTrue(userDao.loadAddress().isEmpty());
        assertTrue(userDao.loadPets().isEmpty());
    }
}
