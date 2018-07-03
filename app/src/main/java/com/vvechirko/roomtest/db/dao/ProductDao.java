package com.vvechirko.roomtest.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.vvechirko.roomtest.db.entity.CommentEntity;
import com.vvechirko.roomtest.db.entity.CommentModel;
import com.vvechirko.roomtest.db.entity.ProductEntity;
import com.vvechirko.roomtest.db.entity.ProductModel;
import com.vvechirko.roomtest.db.entity.ProductWithComments;
import com.vvechirko.roomtest.model.Comment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Dao
public abstract class ProductDao implements CommentDao {

    @Query("SELECT * FROM products")
    public abstract LiveData<List<ProductEntity>> loadAllProducts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void saveProducts(List<ProductEntity> products);

    @Query("select * from products where id = :productId")
    public abstract LiveData<ProductEntity> loadProduct(int productId);

    @Query("select * from products where id = :productId")
    public abstract ProductEntity loadProductSync(int productId);

    // Complex

    @Query("SELECT * FROM products")
    public abstract List<ProductWithComments> loadFullProducts();

    @Query("select * from products where id = :productId")
    public abstract ProductWithComments loadFullProduct(int productId);

    @Query("SELECT * FROM products")
    public abstract List<ProductWithComments> fullProducts();

    @Query("select * from products where id = :productId")
    public abstract ProductWithComments fullProduct(int productId);

    @Transaction
    public void saveFullSeparate(ProductModel product) {
        saveProducts(Arrays.asList(new ProductEntity(product)));

        for (CommentModel c : product.getComments()) {
            saveComments(Arrays.asList(new CommentEntity(c)));
        }
    }

    @Transaction
    public ProductModel getFullSeparate(int productId) {
        ProductWithComments pC = fullProduct(productId);
        ProductModel p = new ProductModel(pC.getProduct());

        List<CommentModel> cl = new ArrayList<>();
        for (Comment c : pC.getComments()) {
            cl.add(new CommentModel(c));
        }
        p.setComments(cl);
        return p;
    }

    @Transaction
    public void saveFull(ProductEntity product) {
        saveProducts(Arrays.asList(product));

        for (CommentEntity c : product.getComments()) {
            saveComments(Arrays.asList(c));
        }
    }

    @Transaction
    public ProductEntity getFull(int productId) {
        ProductWithComments pC = fullProduct(productId);
        pC.getProduct().setComments(pC.getComments());
        return pC.getProduct();
    }
}
