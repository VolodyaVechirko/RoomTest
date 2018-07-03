package com.vvechirko.roomtest.db;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.database.sqlite.SQLiteConstraintException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.vvechirko.roomtest.LiveDataTestUtil;
import com.vvechirko.roomtest.db.dao.CommentDao;
import com.vvechirko.roomtest.db.dao.ProductDao;
import com.vvechirko.roomtest.db.entity.CommentEntity;
import com.vvechirko.roomtest.db.entity.CommentModel;
import com.vvechirko.roomtest.db.entity.ProductEntity;
import com.vvechirko.roomtest.db.entity.ProductModel;
import com.vvechirko.roomtest.db.entity.ProductWithComments;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.vvechirko.roomtest.db.TestData.COMMENTS;
import static com.vvechirko.roomtest.db.TestData.COMMENT_ENTITY;
import static com.vvechirko.roomtest.db.TestData.PRODUCTS;
import static com.vvechirko.roomtest.db.TestData.PRODUCT_ENTITY;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Test the implementation of {@link CommentDao}
 */
@RunWith(AndroidJUnit4.class)
public class CommentDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppDatabase mDatabase;

    private CommentDao mCommentDao;

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

        mCommentDao = mDatabase.commentDao();
        mProductDao = mDatabase.productDao();
    }

    @After
    public void closeDb() throws Exception {
        mDatabase.close();
    }

    @Test
    public void getCommentsWhenNoCommentInserted() throws InterruptedException {
        List<CommentEntity> comments = mCommentDao.loadComments(COMMENT_ENTITY.getProductId());

        assertTrue(comments.isEmpty());
    }

    @Test
    public void cantInsertCommentWithoutProduct() throws InterruptedException {
        try {
            mCommentDao.saveComments(COMMENTS);
            fail("SQLiteConstraintException expected");
        } catch (SQLiteConstraintException ignored) {

        }
    }

    @Test
    public void getCommentsAfterInserted() throws InterruptedException {
        mProductDao.saveProducts(PRODUCTS);
        mCommentDao.saveComments(COMMENTS);

        List<CommentEntity> comments = mCommentDao.loadComments(COMMENT_ENTITY.getProductId());

        assertThat(comments.size(), is(1));
    }

    @Test
    public void getCommentByProductId() throws InterruptedException {
        mProductDao.saveProducts(PRODUCTS);
        mCommentDao.saveComments(COMMENTS);

        List<CommentEntity> comments = mCommentDao.loadComments((COMMENT_ENTITY.getProductId()));

        assertThat(comments.size(), is(1));
    }

    @Test
    public void getProductAndComments() throws InterruptedException {
        mProductDao.saveProducts(PRODUCTS);
        mCommentDao.saveComments(COMMENTS);

        List<ProductWithComments> list = mProductDao.loadFullProducts();

        assertThat(list.size(), is(2));
    }

    @Test
    public void getProductAndComments1() throws InterruptedException {
        mProductDao.saveProducts(PRODUCTS);
        mCommentDao.saveComments(COMMENTS);

        ProductWithComments p = mProductDao.loadFullProduct(PRODUCT_ENTITY.getId());

        assertThat(p.getComments().size(), is(1));
    }

    @Test
    public void fullModelTest() throws InterruptedException {
        CommentModel c1 = new CommentModel(304, 203, "ololo", new Date());
        CommentModel c2 = new CommentModel(308, 203, "some text", new Date());
        CommentModel c3 = new CommentModel(401, 203, "ok", new Date());

        ProductModel p = new ProductModel(203, "Name", "Description", 3000);
        p.setComments(Arrays.asList(c1, c2, c3));

        mProductDao.saveFullSeparate(p);
        ProductModel newPC = mProductDao.getFullSeparate(203);

        assertThat(newPC.getComments().size(), is(3));
    }

    @Test
    public void fullModelTest2() throws InterruptedException {
        CommentEntity c1 = new CommentEntity(304, 203, "ololo", new Date());
        CommentEntity c2 = new CommentEntity(308, 203, "some text", new Date());
        CommentEntity c3 = new CommentEntity(401, 203, "ok", new Date());

        ProductEntity p = new ProductEntity(203, "Name", "Description", 3000);
        p.setComments(Arrays.asList(c1, c2, c3));

        mProductDao.saveFull(p);
        ProductEntity newPC = mProductDao.getFull(203);

        assertThat(newPC.getComments().size(), is(3));
    }
}
