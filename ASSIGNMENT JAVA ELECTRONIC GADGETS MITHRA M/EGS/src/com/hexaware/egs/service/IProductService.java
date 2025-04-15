package com.hexaware.egs.service;

import com.hexaware.egs.entity.Products;
import com.hexaware.egs.exception.ProductNotFoundException;

public interface IProductService {

    Products getProductDetails(int productId) throws ProductNotFoundException;

    int updateProductInfo(Products product);

    boolean isProductInStock(int productId);
}
