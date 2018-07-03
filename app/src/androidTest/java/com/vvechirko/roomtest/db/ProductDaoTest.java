/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vvechirko.roomtest.db;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.vvechirko.roomtest.LiveDataTestUtil;
import com.vvechirko.roomtest.db.dao.ProductDao;
import com.vvechirko.roomtest.db.entity.ProductEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.vvechirko.roomtest.db.TestData.PRODUCTS;
import static com.vvechirko.roomtest.db.TestData.PRODUCT_ENTITY;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Test the implementation of {@link ProductDao}
 */
@RunWith(AndroidJUnit4.class)
public class ProductDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppDatabase mDatabase;

    private ProductDao mProductDao;

    @Before
    public void initDb() throws Exception {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase.class)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build();

        mProductDao = mDatabase.productDao();
    }

    @After
    public void closeDb() throws Exception {
        mDatabase.close();
    }

    @Test
    public void getProductsWhenNoProductInserted() throws InterruptedException {
        List<ProductEntity> products = LiveDataTestUtil.getValue(mProductDao.loadAllProducts());

        assertTrue(products.isEmpty());
    }

    @Test
    public void getProductsAfterInserted() throws InterruptedException {
        mProductDao.saveProducts(PRODUCTS);

        List<ProductEntity> products = LiveDataTestUtil.getValue(mProductDao.loadAllProducts());

        assertThat(products.size(), is(PRODUCTS.size()));
    }

    @Test
    public void getProductById() throws InterruptedException {
        mProductDao.saveProducts(PRODUCTS);

        ProductEntity product = LiveDataTestUtil.getValue(mProductDao.loadProduct
                (PRODUCT_ENTITY.getId()));

        assertThat(product.getId(), is(PRODUCT_ENTITY.getId()));
        assertThat(product.getName(), is(PRODUCT_ENTITY.getName()));
        assertThat(product.getDescription(), is(PRODUCT_ENTITY.getDescription()));
        assertThat(product.getPrice(), is(PRODUCT_ENTITY.getPrice()));
    }

}
