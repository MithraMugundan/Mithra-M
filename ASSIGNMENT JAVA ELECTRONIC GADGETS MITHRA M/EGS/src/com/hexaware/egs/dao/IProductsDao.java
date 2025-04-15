package com.hexaware.egs.dao;

import com.hexaware.egs.entity.Products;
import com.hexaware.egs.exception.ProductNotFoundException;

public interface IProductsDao {

    Products getProductDetails(int productId) throws ProductNotFoundException;

    int updateProductInfo(Products product);

    boolean isProductInStock(int productId);
}
