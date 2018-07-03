package com.vvechirko.roomtest;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.vvechirko.roomtest.realm.AddressModel;
import com.vvechirko.roomtest.realm.PetModel;
import com.vvechirko.roomtest.realm.UserModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class RealmTest {

    private Realm testRealm;

    @Before
    public void initDb() {
        Realm.init(InstrumentationRegistry.getInstrumentation().getTargetContext());
        RealmConfiguration testConfig =
                new RealmConfiguration.Builder().
                        inMemory().
                        name("test-realm").build();

        testRealm = Realm.getInstance(testConfig);
    }

    @After
    public void closeDb() {
        testRealm.close();
    }

    @Test
    public void getUsersWhenNoInserted() {
        RealmResults<UserModel> users = testRealm.where(UserModel.class).findAll();

        assertTrue(users.isEmpty());
    }

    public UserModel getUser() {
        UserModel user = new UserModel(15, "John");
        AddressModel address = new AddressModel(20, 15, "London");
        PetModel pet1 = new PetModel(2, 15, "Rex");
        PetModel pet2 = new PetModel(4, 15, "Bax");
        user.setAddress(address);
        user.setPets(new RealmList<>(pet1, pet2));
        return user;
    }

    @Test
    public void insertAndSelectUsers() {
        testRealm.executeTransaction(realm -> realm.copyToRealmOrUpdate(getUser()));

        RealmResults<UserModel> users = testRealm.where(UserModel.class).findAll();

        UserModel escaped = testRealm.copyFromRealm(testRealm.where(UserModel.class).findFirst());

        assertEquals(1, users.size());
    }

    @Test
    public void insertAndDeleteUsers() {
        UserModel user = getUser();
        testRealm.executeTransaction(realm -> realm.copyToRealmOrUpdate(user));

        // Non Cascade
//        testRealm.executeTransaction(realm ->
//                realm.where(UserModel.class).equalTo("id", user.getId())
//                        .findAll().deleteAllFromRealm()
//        );

        // Cascade
        testRealm.executeTransaction(realm -> {
            int userId = user.getId();

            realm.where(UserModel.class).equalTo(UserModel.ID, userId)
                    .findAll().deleteAllFromRealm();

            realm.where(AddressModel.class).equalTo(AddressModel.USER_ID, userId)
                    .findAll().deleteAllFromRealm();

            realm.where(PetModel.class).equalTo(PetModel.USER_ID, userId)
                    .findAll().deleteAllFromRealm();
        });

        RealmResults<UserModel> list = testRealm.where(UserModel.class).findAll();
//        RealmResults<AddressModel> list = testRealm.where(AddressModel.class).findAll();
//        RealmResults<PetModel> list = testRealm.where(PetModel.class).findAll();

        assertTrue(testRealm.where(UserModel.class).findAll().isEmpty());
        assertTrue(testRealm.where(AddressModel.class).findAll().isEmpty());
        assertTrue(testRealm.where(PetModel.class).findAll().isEmpty());
    }
}
